package org.fluentlenium.core.inject;

import org.fluentlenium.core.FluentContainer;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.components.ComponentsManager;
import org.fluentlenium.core.domain.ComponentList;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.events.ContainerAnnotationsEventsRegistry;
import org.fluentlenium.core.hook.DefaultHookChainBuilder;
import org.fluentlenium.core.hook.FluentHook;
import org.fluentlenium.core.hook.Hook;
import org.fluentlenium.core.hook.HookDefinition;
import org.fluentlenium.core.hook.HookOptions;
import org.fluentlenium.core.hook.NoHook;
import org.fluentlenium.core.proxy.LocatorProxies;
import org.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Handle injection of element proxies, @Page objects and @FindBy.
 */
public class FluentInjector implements FluentInjectControl {

    private final ConcurrentMap<Class, Object> containerInstances = new ConcurrentHashMap<>();
    private final ConcurrentMap<Object, ContainerContext> containerContexts = new ConcurrentHashMap<>();
    private final ConcurrentMap<Object, ContainerAnnotationsEventsRegistry> eventsContainerSupport = new ConcurrentHashMap<>();
    private final ConcurrentMap<Object, SearchContext> searchContexts = new ConcurrentHashMap<>();


    private final FluentControl fluentControl;
    private final ComponentsManager componentsManager;
    private final ContainerInstanciator containerInstanciator;
    private final DefaultHookChainBuilder hookChainBuilder;

    public FluentInjector(FluentControl fluentControl, ComponentsManager componentsManager, ContainerInstanciator instanciator) {
        this.fluentControl = fluentControl;
        this.componentsManager = componentsManager;
        this.containerInstanciator = instanciator;
        this.hookChainBuilder = new DefaultHookChainBuilder(fluentControl, componentsManager.getInstantiator());
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
        T container = containerInstanciator.newInstance(cls, null);
        inject(container);
        return container;
    }

    @Override
    public ContainerContext[] inject(Object... containers) {
        ContainerContext[] context = new ContainerContext[containers.length];
        for (int i = 0; i < containers.length; i++) {
            context[i] = inject(containers[i]);
        }
        return context;
    }

    @Override
    public ContainerContext inject(Object container) {
        inject(container, null, fluentControl.getDriver());
        return containerContexts.get(container);
    }

    private void inject(Object container, Object parentContainer, SearchContext searchContext) {
        initContainer(container, parentContainer, searchContext);
        initParentContainer(container, parentContainer);
        initFluentElements(container, searchContext);
        initChildrenContainers(container, searchContext);
    }

    private void injectComponent(Object componentContainer, Object parentContainer, SearchContext searchContext) {
        initContainerContext(componentContainer, parentContainer, searchContext);
        initParentContainer(componentContainer, parentContainer);
        initFluentElements(componentContainer, searchContext);
        initChildrenContainers(componentContainer, searchContext);
    }

    private void initParentContainer(Object container, Object parentContainer) {
        for (Class cls = container.getClass(); isClassSupported(cls); cls = cls.getSuperclass()) {
            for (Field field : cls.getDeclaredFields()) {
                if (isParent(field)) {
                    try {
                        ReflectionUtils.set(field, container, parentContainer);
                    } catch (IllegalAccessException | IllegalArgumentException e) {
                        throw new FluentInjectException("Can't set field " + field + " with value " + parentContainer, e);
                    }
                }
            }
        }
    }

    private boolean isParent(Field field) {
        return field.isAnnotationPresent(Parent.class);
    }

    private void initContainer(Object container, Object parentContainer, SearchContext searchContext) {
        initContainerContext(container, parentContainer, searchContext);
        if (container instanceof FluentContainer) {
            ((FluentContainer) container).initFluent(new ContainerFluentControl(fluentControl, containerContexts.get(container)));
        }
        initEventAnnotations(container);
    }

    private void initContainerContext(Object container, Object parentContainer, SearchContext searchContext) {
        ContainerContext parentContainerContext = parentContainer != null ? containerContexts.get(parentContainer) : null;

        DefaultContainerContext containerContext = new DefaultContainerContext(container, parentContainerContext, searchContext);
        containerContexts.put(container, containerContext);

        if (parentContainerContext != null) {
            containerContext.getHookDefinitions().addAll(parentContainerContext.getHookDefinitions());
        }

        for (Class cls = container.getClass(); isClassSupported(cls); cls = cls.getSuperclass()) {
            addHookDefinitions(cls.getDeclaredAnnotations(), containerContext.getHookDefinitions());
        }
    }

    private void initEventAnnotations(final Object container) {
        if (fluentControl.getDriver() instanceof EventFiringWebDriver) {
            eventsContainerSupport.put(container, new ContainerAnnotationsEventsRegistry(fluentControl, container));
        }
    }

    private static boolean isContainer(Field field) {
        return field.isAnnotationPresent(Page.class);
    }

    private static boolean isClassSupported(Class<?> cls) {
        return cls != Object.class && cls != null;
    }

    private void initChildrenContainers(Object container, SearchContext searchContext) {
        for (Class cls = container.getClass(); isClassSupported(cls); cls = cls.getSuperclass()) {
            for (Field field : cls.getDeclaredFields()) {
                if (isContainer(field)) {
                    Class fieldClass = field.getType();
                    Object existingChildContainer = containerInstances.get(fieldClass);
                    if (existingChildContainer != null) {
                        try {
                            ReflectionUtils.set(field, container, existingChildContainer);
                        } catch (IllegalAccessException e) {
                            throw new FluentInjectException("Can't set field " + field + " with value " + existingChildContainer, e);
                        }
                    } else {
                        Object childContainer = containerInstanciator.newInstance(fieldClass, containerContexts.get(container));
                        initContainer(childContainer, container, searchContext);
                        try {
                            ReflectionUtils.set(field, container, childContainer);
                        } catch (IllegalAccessException e) {
                            throw new FluentInjectException("Can't set field " + field + " with value " + childContainer, e);
                        }
                        containerInstances.putIfAbsent(fieldClass, childContainer);
                        inject(childContainer, container, searchContext);
                    }
                } else if (isComponent(field) && !isParent(field)) {
                    try {
                        Object o = ReflectionUtils.get(field, container);
                        SearchContext componentSearchContext = searchContexts.get(o);
                        if (componentSearchContext == null) {
                            componentSearchContext = fluentControl.getDriver();
                        }
                        injectComponent(o, container, componentSearchContext); // SearchContext from the component
                    } catch (IllegalAccessException e) {
                        throw new FluentInjectException("Can't get field " + field + " from container " + container, e);
                    }
                }
            }
        }
    }

    private <T extends FluentControl> void initFluentElements(Object container, SearchContext searchContext) {
        ContainerContext containerContext = containerContexts.get(container);

        for (Class cls = container.getClass(); isClassSupported(cls); cls = cls.getSuperclass()) {
            for (Field field : cls.getDeclaredFields()) {
                if (isSupported(container, field)) {
                    ArrayList<HookDefinition<?>> fieldHookDefinitions = new ArrayList<>(containerContext.getHookDefinitions());
                    addHookDefinitions(field.getAnnotations(), fieldHookDefinitions);
                    ElementLocatorFactory locatorFactory = new FieldAndReturnTypeElementLocatorFactory(searchContext);
                    ElementLocator locator = locatorFactory.createLocator(field);
                    if (locator == null) {
                        continue;
                    }
                    Object fieldValue = initFieldElements(locator, fieldHookDefinitions, container, field);
                    if (fieldValue != null) {
                        searchContexts.put(fieldValue, new ElementLocatorSearchContext(locator));
                    }
                }
            }
        }
    }

    private Hook getHookAnnotation(Annotation annotation) {
        if (annotation instanceof Hook) {
            return (Hook) annotation;
        } else if (annotation.annotationType().isAnnotationPresent(Hook.class)) {
            return annotation.annotationType().getAnnotation(Hook.class);
        }
        return null;
    }

    private HookOptions getHookOptionsAnnotation(Annotation annotation) {
        if (annotation instanceof HookOptions) {
            return (HookOptions) annotation;
        } else if (annotation.annotationType().isAnnotationPresent(HookOptions.class)) {
            return annotation.annotationType().getAnnotation(HookOptions.class);
        }
        return null;
    }

    private void addHookDefinitions(Annotation[] annotations, List<HookDefinition<?>> hookDefinitions) {
        Hook currentHookAnnotation = null;
        HookOptions currentHookOptionAnnotation = null;

        for (Annotation annotation : annotations) {
            if (annotation instanceof NoHook) {
                hookDefinitions.clear();
                break;
            }
        }

        Annotation currentAnnotation = null;
        for (Annotation annotation : annotations) {
            Hook hookAnnotation = getHookAnnotation(annotation);
            if (hookAnnotation != null) {
                currentAnnotation = annotation;
            }
            if (hookAnnotation != null && currentHookAnnotation != null) {
                hookDefinitions.add(buildHookDefinition(currentHookAnnotation, currentHookOptionAnnotation, currentAnnotation));
                currentHookAnnotation = null;
                currentHookOptionAnnotation = null;

            }
            if (hookAnnotation != null) {
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

    private <T> HookDefinition<T> buildHookDefinition(Hook hookAnnotation, HookOptions hookOptionsAnnotation, Annotation currentAnnotation) {
        Class<? extends FluentHook<T>> hookClass = (Class<? extends FluentHook<T>>) hookAnnotation.value();
        Class<? extends T> hookOptionsClass = hookOptionsAnnotation == null ? null : (Class<? extends T>) hookOptionsAnnotation.value();
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
        if (fluentHookOptions != null) {
            return new HookDefinition<>(hookClass, fluentHookOptions);
        } else {
            return new HookDefinition<>(hookClass);
        }
    }


    private boolean isSupported(Object container, Field field) {
        return isValueNull(container, field) && !field.isAnnotationPresent(NoInject.class) && !Modifier.isFinal(field.getModifiers()) &&
                (isListOfFluentWebElement(field) ||
                        isListOfComponent(field) ||
                        isComponent(field) ||
                        isComponentList(field) || isElement(field) || isListOfElement(field));
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
                Class<?> genericType = getFirstGenericType(field);
                boolean componentClass = componentsManager.isComponentClass(genericType);

                if (componentClass) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isListOfFluentWebElement(Field field) {
        if (isList(field)) {
            Class<?> genericType = getFirstGenericType(field);
            return FluentWebElement.class.isAssignableFrom(genericType);
        }
        return false;
    }

    private boolean isListOfComponent(Field field) {
        if (isList(field)) {
            Class<?> genericType = getFirstGenericType(field);
            return componentsManager.isComponentClass(genericType);
        }
        return false;
    }

    private static Class<?> getFirstGenericType(Field field) {
        Type[] actualTypeArguments = ((ParameterizedType) field.getGenericType())
                .getActualTypeArguments();

        if (actualTypeArguments.length > 0) {
            return (Class<?>) actualTypeArguments[0];
        }

        return null;
    }

    private static boolean isList(Field field) {
        return List.class.isAssignableFrom(field.getType());
    }

    private static boolean isElement(Field field) {
        return WebElement.class.isAssignableFrom(field.getType());
    }

    private static boolean isListOfElement(Field field) {
        if (isList(field)) {
            Class<?> genericType = getFirstGenericType(field);
            return WebElement.class.isAssignableFrom(genericType);
        }
        return false;
    }

    private Object initFieldElements(ElementLocator locator, List<HookDefinition<?>> hookDefinitions, Object container, Field field) {
        try {
            if (isComponent(field)) {
                return initFieldAsComponent(locator, container, field, hookDefinitions);
            } else if (isComponentList(field)) {
                return initFieldAsComponentList(locator, container, field, hookDefinitions);
            } else if (isListOfFluentWebElement(field)) {
                return initFieldAsListOfFluentWebElement(locator, container, field, hookDefinitions);
            } else if (isListOfComponent(field)) {
                return initFieldAsListOfComponent(locator, container, field, hookDefinitions);
            } else if (isElement(field)) {
                return initFieldAsElement(locator, container, field, hookDefinitions);
            } else if (isListOfElement(field)) {
                return initFieldAsListOfElement(locator, container, field, hookDefinitions);
            }
        } catch (IllegalAccessException e) {
            throw new FluentInjectException("Unable to find an accessible constructor with an argument of type WebElement in " + field.getType(), e);
        }
        return null;
    }

    private <L extends List<T>, T> L initFieldAsComponentList(ElementLocator locator, Object container, Field field, List<HookDefinition<?>> hookDefinitions) throws IllegalAccessException {
        List<WebElement> webElementList = LocatorProxies.createWebElementList(locator);
        L componentList = componentsManager.asComponentList((Class<L>) field.getType(), (Class<T>) getFirstGenericType(field), webElementList);
        LocatorProxies.setHooks(webElementList, hookChainBuilder, hookDefinitions);
        ReflectionUtils.set(field, container, componentList);
        return componentList;
    }

    private Object initFieldAsComponent(ElementLocator locator, Object container, Field field, List<HookDefinition<?>> hookDefinitions) throws IllegalAccessException {
        WebElement element = LocatorProxies.createWebElement(locator);
        Object component = componentsManager.newComponent(field.getType(), element);

        LocatorProxies.setHooks(element, hookChainBuilder, hookDefinitions);
        ReflectionUtils.set(field, container, component);
        return component;
    }

    private ComponentList<?> initFieldAsListOfComponent(ElementLocator locator, Object container, Field field, List<HookDefinition<?>> hookDefinitions) throws IllegalAccessException {
        List<WebElement> webElementList = LocatorProxies.createWebElementList(locator);
        ComponentList<?> componentList = componentsManager.asComponentList(getFirstGenericType(field), webElementList);
        LocatorProxies.setHooks(webElementList, hookChainBuilder, hookDefinitions);
        ReflectionUtils.set(field, container, componentList);
        return componentList;
    }

    private FluentList<? extends FluentWebElement> initFieldAsListOfFluentWebElement(ElementLocator locator, Object container, Field field, List<HookDefinition<?>> hookDefinitions) throws IllegalAccessException {
        List<WebElement> webElementList = LocatorProxies.createWebElementList(locator);
        FluentList<? extends FluentWebElement> fluentList = componentsManager.asFluentList((Class<? extends FluentWebElement>) getFirstGenericType(field), webElementList);
        LocatorProxies.setHooks(webElementList, hookChainBuilder, hookDefinitions);
        ReflectionUtils.set(field, container, fluentList);
        return fluentList;
    }

    private WebElement initFieldAsElement(ElementLocator locator, Object container, Field field, List<HookDefinition<?>> hookDefinitions) throws IllegalAccessException {
        WebElement element = LocatorProxies.createWebElement(locator);
        LocatorProxies.setHooks(element, hookChainBuilder, hookDefinitions);
        ReflectionUtils.set(field, container, element);
        return element;
    }

    private List<WebElement> initFieldAsListOfElement(ElementLocator locator, Object container, Field field, List<HookDefinition<?>> hookDefinitions) throws IllegalAccessException {
        List<WebElement> elements = LocatorProxies.createWebElementList(locator);
        LocatorProxies.setHooks(elements, hookChainBuilder, hookDefinitions);
        ReflectionUtils.set(field, container, elements);
        return elements;
    }
}
