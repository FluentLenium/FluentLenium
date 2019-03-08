package org.fluentlenium.core.inject;

import static org.fluentlenium.core.inject.InjectionAnnotationSupport.isAnnotationTypeHook;
import static org.fluentlenium.core.inject.InjectionAnnotationSupport.isAnnotationTypeHookOptions;
import static org.fluentlenium.core.inject.InjectionAnnotationSupport.isNoInject;
import static org.fluentlenium.core.inject.InjectionAnnotationSupport.isContainer;
import static org.fluentlenium.core.inject.InjectionAnnotationSupport.isParent;
import static org.fluentlenium.utils.CollectionUtils.isList;

import org.apache.commons.lang3.ArrayUtils;
import org.fluentlenium.core.FluentContainer;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.ComponentsManager;
import org.fluentlenium.core.components.LazyComponents;
import org.fluentlenium.core.components.LazyComponentsListener;
import org.fluentlenium.core.domain.ComponentList;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.events.ContainerAnnotationsEventsRegistry;
import org.fluentlenium.core.events.EventsRegistry;
import org.fluentlenium.core.hook.DefaultHookChainBuilder;
import org.fluentlenium.core.hook.FluentHook;
import org.fluentlenium.core.hook.Hook;
import org.fluentlenium.core.hook.HookControlImpl;
import org.fluentlenium.core.hook.HookDefinition;
import org.fluentlenium.core.hook.HookOptions;
import org.fluentlenium.core.hook.NoHook;
import org.fluentlenium.core.proxy.LocatorProxies;
import org.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

/**
 * Handle injection of element proxies, @Page objects and @FindBy.
 */
@SuppressWarnings("PMD.GodClass")
public class FluentInjector implements FluentInjectControl {

    private final Map<Class, Object> containerInstances = new IdentityHashMap<>();
    private final Map<Object, ContainerContext> containerContexts = new IdentityHashMap<>();
    private final Map<Object, ContainerAnnotationsEventsRegistry> eventsContainerSupport = new IdentityHashMap<>();

    private final FluentControl fluentControl;
    private final ComponentsManager componentsManager;
    private final ContainerInstantiator containerInstantiator;
    private final DefaultHookChainBuilder hookChainBuilder;
    private final EventsRegistry eventsRegistry;

    /**
     * Creates a new injector.
     *
     * @param control           control interface
     * @param eventsRegistry    events registry
     * @param componentsManager components manager
     * @param instantiator      container instantiator
     */
    public FluentInjector(FluentControl control, EventsRegistry eventsRegistry, ComponentsManager componentsManager,
            ContainerInstantiator instantiator) {
        fluentControl = control;
        this.eventsRegistry = eventsRegistry;
        this.componentsManager = componentsManager;
        containerInstantiator = instantiator;
        hookChainBuilder = new DefaultHookChainBuilder(control, componentsManager.getInstantiator());
    }

    /**
     * Release all loaded containers.
     */
    public void release() {
        containerInstances.clear();
        for (ContainerAnnotationsEventsRegistry support : eventsContainerSupport.values()) {
            support.close();
        }
        eventsContainerSupport.clear();
        containerContexts.clear();
        componentsManager.release();
    }

    @Override
    public <T> T newInstance(Class<T> cls) {
        T container = containerInstantiator.newInstance(cls, null);
        inject(container);
        return container;
    }

    @Override
    public ContainerContext inject(Object container) {
        initContainer(container, null, fluentControl.getDriver());
        inject(container, null, fluentControl.getDriver());
        return containerContexts.get(container);
    }

    @Override
    public ContainerContext injectComponent(Object componentContainer, Object parentContainer, SearchContext searchContext) {
        initContainerContext(componentContainer, parentContainer, searchContext);
        inject(componentContainer, parentContainer, searchContext);
        return containerContexts.get(componentContainer);
    }

    private void inject(Object container, Object parentContainer, SearchContext searchContext) {
        initParentContainer(container, parentContainer);
        initFluentElements(container, searchContext);
        initChildrenContainers(container, searchContext);
    }

    private void initParentContainer(Object container, Object parentContainer) {
        forAllDeclaredFieldsInHierarchyOf(container, field -> {
            if (isParent(field)) {
                try {
                    ReflectionUtils.set(field, container, parentContainer);
                } catch (IllegalAccessException | IllegalArgumentException e) {
                    throw new FluentInjectException("Can't set field " + field + " with value " + parentContainer, e);
                }
            }
        });
    }

    private void forAllDeclaredFieldsInHierarchyOf(Object container, Consumer<Field> fieldConsumer) {
        for (Class cls = container.getClass(); isClassSupported(cls); cls = cls.getSuperclass()) {
            for (Field field : cls.getDeclaredFields()) {
                fieldConsumer.accept(field);
            }
        }
    }

    private void initContainer(Object container, Object parentContainer, SearchContext searchContext) {
        initContainerContext(container, parentContainer, searchContext);
        if (container instanceof FluentContainer) {
            ((FluentContainer) container).initFluent(new ContainerFluentControl(fluentControl, containerContexts.get(container)));
        }
        initEventAnnotations(container);
    }

    private void initContainerContext(Object container, Object parentContainer, SearchContext searchContext) {
        ContainerContext parentContainerContext = parentContainer == null ? null : containerContexts.get(parentContainer);

        DefaultContainerContext containerContext = new DefaultContainerContext(container, parentContainerContext, searchContext);
        containerContexts.put(container, containerContext);

        if (parentContainerContext != null) {
            containerContext.getHookDefinitions().addAll(parentContainerContext.getHookDefinitions());
        }

        for (Class cls = container.getClass(); isClassSupported(cls); cls = cls.getSuperclass()) {
            addHookDefinitions(cls.getDeclaredAnnotations(), containerContext.getHookDefinitions());
        }
    }

    private void initEventAnnotations(Object container) {
        if (eventsRegistry != null && !eventsContainerSupport.containsKey(container)) {
            eventsContainerSupport.put(container, new ContainerAnnotationsEventsRegistry(eventsRegistry, container));
        }
    }

    private static boolean isClassSupported(Class<?> cls) {
        return cls != Object.class && cls != null;
    }

    private void initChildrenContainers(Object container, SearchContext searchContext) {
        forAllDeclaredFieldsInHierarchyOf(container, field -> {
            if (isContainer(field)) {
                Class fieldClass = field.getType();
                Object existingChildContainer = containerInstances.get(fieldClass);
                if (existingChildContainer == null) {
                    Object childContainer = containerInstantiator.newInstance(fieldClass, containerContexts.get(container));
                    initContainer(childContainer, container, searchContext);
                    try {
                        ReflectionUtils.set(field, container, childContainer);
                    } catch (IllegalAccessException e) {
                        throw new FluentInjectException("Can't set field " + field + " with value " + childContainer, e);
                    }
                    containerInstances.put(fieldClass, childContainer);
                    inject(childContainer, container, searchContext);
                } else {
                    try {
                        ReflectionUtils.set(field, container, existingChildContainer);
                    } catch (IllegalAccessException e) {
                        throw new FluentInjectException("Can't set field " + field + " with value " + existingChildContainer, e);
                    }
                }
            }
        });
    }

    private void initFluentElements(Object container, SearchContext searchContext) {
        ContainerContext containerContext = containerContexts.get(container);

        forAllDeclaredFieldsInHierarchyOf(container, field -> {
            if (isSupported(container, field)) {
                ArrayList<HookDefinition<?>> fieldHookDefinitions = new ArrayList<>(containerContext.getHookDefinitions());
                addHookDefinitions(field.getAnnotations(), fieldHookDefinitions);
                InjectionElementLocatorFactory locatorFactory = new InjectionElementLocatorFactory(searchContext);
                InjectionElementLocator locator = locatorFactory.createLocator(field);
                if (locator != null) {
                    ComponentAndProxy fieldValue = initFieldElements(locator, field);
                    injectComponent(fieldValue, locator, container, field, fieldHookDefinitions);
                }
            }
        });
    }

    private void injectComponent(ComponentAndProxy fieldValue, ElementLocator locator, Object container, Field field,
            ArrayList<HookDefinition<?>> fieldHookDefinitions) {
        if (fieldValue != null) {
            LocatorProxies.setHooks(fieldValue.getProxy(), hookChainBuilder, fieldHookDefinitions);
            try {
                ReflectionUtils.set(field, container, fieldValue.getComponent());
            } catch (IllegalAccessException e) {
                throw new FluentInjectException(
                        "Unable to find an accessible constructor with an argument of type WebElement in " + field.getType(), e);
            }

            if (fieldValue.getComponent() instanceof Iterable && isLazyComponentsAndNotInitialized(fieldValue.getComponent())) {
                LazyComponents lazyComponents = (LazyComponents) fieldValue.getComponent();
                lazyComponents.addLazyComponentsListener((LazyComponentsListener<Object>) componentMap -> {
                    for (Entry<WebElement, Object> componentEntry : componentMap.entrySet()) {
                        injectComponent(componentEntry.getValue(), container, componentEntry.getKey());
                    }
                });
            } else {
                ElementLocatorSearchContext componentSearchContext = new ElementLocatorSearchContext(locator);
                injectComponent(fieldValue.getComponent(), container, componentSearchContext);
            }
        }
    }

    private boolean isLazyComponentsAndNotInitialized(Object component) {
        if (component instanceof LazyComponents) {
            LazyComponents lazyComponents = (LazyComponents) component;
            return lazyComponents.isLazy() && !lazyComponents.isLazyInitialized();
        }
        return false;
    }

    private Hook getHookAnnotation(Annotation annotation) {
        if (annotation instanceof Hook) {
            return (Hook) annotation;
        } else if (isAnnotationTypeHook(annotation)) {
            return annotation.annotationType().getAnnotation(Hook.class);
        }
        return null;
    }

    private HookOptions getHookOptionsAnnotation(Annotation annotation) {
        if (annotation instanceof HookOptions) {
            return (HookOptions) annotation;
        } else if (isAnnotationTypeHookOptions(annotation)) {
            return annotation.annotationType().getAnnotation(HookOptions.class);
        }
        return null;
    }

    private void addHookDefinitions(Annotation[] annotations, List<HookDefinition<?>> hookDefinitions) {
        Hook currentHookAnnotation = null;
        HookOptions currentHookOptionAnnotation = null;

        Annotation currentAnnotation = null;
        for (Annotation annotation : annotations) {
            applyNoHook(hookDefinitions, annotation);

            Hook hookAnnotation = getHookAnnotation(annotation);
            if (hookAnnotation != null) {
                currentAnnotation = annotation;
                if (currentHookAnnotation != null) {
                    hookDefinitions.add(buildHookDefinition(currentHookAnnotation, currentHookOptionAnnotation, currentAnnotation));
                    currentHookOptionAnnotation = null;
                }
                currentHookAnnotation = hookAnnotation;
            }
            HookOptions hookOptionsAnnotation = getHookOptionsAnnotation(annotation);
            if (hookOptionsAnnotation != null) {
                if (currentHookOptionAnnotation != null) {
                    throw new FluentInjectException("Unexpected @HookOptions annotation. @Hook is missing.");
                }
                currentHookOptionAnnotation = hookOptionsAnnotation;
            }
        }

        if (currentHookAnnotation != null) {
            hookDefinitions.add(buildHookDefinition(currentHookAnnotation, currentHookOptionAnnotation, currentAnnotation));
        }
    }

    private void applyNoHook(List<HookDefinition<?>> hookDefinitions, Annotation annotation) {
        if (annotation instanceof NoHook) {
            Hook[] value = ((NoHook) annotation).value();
            if (ArrayUtils.isEmpty(value)) {
                hookDefinitions.clear();
            } else {
                HookControlImpl
                        .removeHooksFromDefinitions(hookDefinitions, Arrays.stream(value).map(Hook::value).toArray(Class[]::new));
            }
        }
    }

    private <T> HookDefinition<T> buildHookDefinition(Hook hookAnnotation, HookOptions hookOptionsAnnotation,
            Annotation currentAnnotation) {
        Class<? extends T> hookOptionsClass =
                hookOptionsAnnotation == null ? null : (Class<? extends T>) hookOptionsAnnotation.value();
        T fluentHookOptions = null;
        if (hookOptionsClass != null) {
            try {
                fluentHookOptions = ReflectionUtils.newInstanceOptionalArgs(hookOptionsClass, currentAnnotation);
            } catch (NoSuchMethodException e) {
                throw new FluentInjectException("@HookOption class has no valid constructor", e);
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                throw new FluentInjectException("Can't create @HookOption class instance", e);
            }
        }
        Class<? extends FluentHook<T>> hookClass = (Class<? extends FluentHook<T>>) hookAnnotation.value();
        return fluentHookOptions == null ? new HookDefinition<>(hookClass) : new HookDefinition<>(hookClass, fluentHookOptions);
    }

    private boolean isSupported(Object container, Field field) {
        return isValueNull(container, field) && !isNoInject(field) && !Modifier.isFinal(field.getModifiers()) && (
                isListOfFluentWebElement(field) || isListOfComponent(field) || isComponent(field) || isComponentList(field)
                        || isElement(field) || isListOfElement(field));
    }

    private static boolean isValueNull(Object container, Field field) {
        try {
            return ReflectionUtils.get(field, container) == null;
        } catch (IllegalAccessException e) {
            throw new FluentInjectException("Can't retrieve default value of field", e);
        }
    }

    private boolean isComponent(Field field) {
        return componentsManager.isComponentClass(field.getType());
    }

    private boolean isComponentList(Field field) {
        if (isList(field)) {
            boolean componentListClass = componentsManager.isComponentListClass((Class<? extends List<?>>) field.getType());
            if (componentListClass) {
                Class<?> genericType = ReflectionUtils.getFirstGenericType(field);
                return componentsManager.isComponentClass(genericType);
            }
        }
        return false;
    }

    private static boolean isListOfFluentWebElement(Field field) {
        if (isList(field)) {
            Class<?> genericType = ReflectionUtils.getFirstGenericType(field);
            return FluentWebElement.class.isAssignableFrom(genericType);
        }
        return false;
    }

    private boolean isListOfComponent(Field field) {
        if (isList(field)) {
            Class<?> genericType = ReflectionUtils.getFirstGenericType(field);
            return componentsManager.isComponentClass(genericType);
        }
        return false;
    }

    private static boolean isElement(Field field) {
        return WebElement.class.isAssignableFrom(field.getType());
    }

    private static boolean isListOfElement(Field field) {
        if (isList(field)) {
            Class<?> genericType = ReflectionUtils.getFirstGenericType(field);
            return WebElement.class.isAssignableFrom(genericType);
        }
        return false;
    }

    private static class ComponentAndProxy<T, P> {
        private final T component;
        private final P proxy;

        ComponentAndProxy(T component, P proxy) {
            this.component = component;
            this.proxy = proxy;
        }

        public T getComponent() {
            return component;
        }

        public P getProxy() {
            return proxy;
        }
    }

    private ComponentAndProxy<?, ?> initFieldElements(ElementLocator locator, Field field) {
        if (isComponent(field)) {
            return initFieldAsComponent(locator, field);
        } else if (isComponentList(field)) {
            return initFieldAsComponentList(locator, field);
        } else if (isListOfFluentWebElement(field)) {
            return initFieldAsListOfFluentWebElement(locator, field);
        } else if (isListOfComponent(field)) {
            return initFieldAsListOfComponent(locator, field);
        } else if (isElement(field)) {
            return initFieldAsElement(locator);
        } else if (isListOfElement(field)) {
            return initFieldAsListOfElement(locator);
        }
        return null;
    }

    private <L extends List<T>, T> ComponentAndProxy<L, List<WebElement>> initFieldAsComponentList(ElementLocator locator,
            Field field) {
        List<WebElement> webElementList = LocatorProxies.createWebElementList(locator);
        L componentList = componentsManager
                .asComponentList((Class<L>) field.getType(), (Class<T>) ReflectionUtils.getFirstGenericType(field),
                        webElementList);
        return new ComponentAndProxy<>(componentList, webElementList);
    }

    private ComponentAndProxy<Object, WebElement> initFieldAsComponent(ElementLocator locator, Field field) {
        WebElement element = LocatorProxies.createWebElement(locator);
        Object component = componentsManager.newComponent(field.getType(), element);
        return new ComponentAndProxy(component, element);
    }

    private ComponentAndProxy<ComponentList<?>, List<WebElement>> initFieldAsListOfComponent(ElementLocator locator,
            Field field) {
        List<WebElement> webElementList = LocatorProxies.createWebElementList(locator);
        ComponentList<?> componentList = componentsManager
                .asComponentList(ReflectionUtils.getFirstGenericType(field), webElementList);
        return new ComponentAndProxy(componentList, webElementList);
    }

    private ComponentAndProxy<FluentList<? extends FluentWebElement>, List<WebElement>> initFieldAsListOfFluentWebElement(
            ElementLocator locator, Field field) {
        List<WebElement> webElementList = LocatorProxies.createWebElementList(locator);
        FluentList<? extends FluentWebElement> fluentList = componentsManager
                .asFluentList((Class<? extends FluentWebElement>) ReflectionUtils.getFirstGenericType(field), webElementList);
        return new ComponentAndProxy(fluentList, webElementList);
    }

    private ComponentAndProxy<WebElement, WebElement> initFieldAsElement(ElementLocator locator) {
        WebElement element = LocatorProxies.createWebElement(locator);
        return new ComponentAndProxy<>(element, element);
    }

    private ComponentAndProxy<List<WebElement>, List<WebElement>> initFieldAsListOfElement(ElementLocator locator) {
        List<WebElement> elements = LocatorProxies.createWebElementList(locator);
        return new ComponentAndProxy(elements, elements);
    }
}
