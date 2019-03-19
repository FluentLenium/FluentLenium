package org.fluentlenium.core.inject;

import static org.fluentlenium.core.inject.InjectionAnnotationSupport.isContainer;
import static org.fluentlenium.core.inject.InjectionAnnotationSupport.isParent;

import org.fluentlenium.core.FluentContainer;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.ComponentsManager;
import org.fluentlenium.core.components.LazyComponents;
import org.fluentlenium.core.components.LazyComponentsListener;
import org.fluentlenium.core.events.ContainerAnnotationsEventsRegistry;
import org.fluentlenium.core.events.EventsRegistry;
import org.fluentlenium.core.hook.DefaultHookChainBuilder;
import org.fluentlenium.core.hook.HookDefinition;
import org.fluentlenium.core.proxy.LocatorProxies;
import org.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

/**
 * Handle injection of element proxies, {@link org.fluentlenium.core.annotation.Page} objects, {@link Parent} objects and
 * {@link org.openqa.selenium.support.FindBy}.
 * <p>
 * Excludes fields from injection that are marked as {@link NoInject}.
 */
public class FluentInjector implements FluentInjectControl {

    private final Map<Class, Object> containerInstances = new IdentityHashMap<>();
    private final Map<Object, ContainerContext> containerContexts = new IdentityHashMap<>();
    private final Map<Object, ContainerAnnotationsEventsRegistry> eventsContainerSupport = new IdentityHashMap<>();

    private final FluentControl fluentControl;
    private final ComponentsManager componentsManager;
    private final ContainerInstantiator containerInstantiator;
    private final DefaultHookChainBuilder hookChainBuilder;
    private final EventsRegistry eventsRegistry;

    private final FluentElementInjectionSupportValidator injectionSupportValidator;
    private final FluentInjectFieldInitializer fieldInitializer;
    private final FluentInjectHookDefinitionAdder hookDefinitionAdder = new FluentInjectHookDefinitionAdder();

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
        injectionSupportValidator = new FluentElementInjectionSupportValidator(componentsManager);
        fieldInitializer = new FluentInjectFieldInitializer(componentsManager, injectionSupportValidator);
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

    private void initContainer(Object container, Object parentContainer, SearchContext searchContext) {
        initContainerContext(container, parentContainer, searchContext);
        if (container instanceof FluentContainer) {
            ((FluentContainer) container).initFluent(new ContainerFluentControl(fluentControl, containerContexts.get(container)));
        }
        initEventAnnotations(container);
    }

    //-------- initContainerContext --------

    private void initContainerContext(Object container, Object parentContainer, SearchContext searchContext) {
        ContainerContext parentContainerContext = parentContainer == null ? null : containerContexts.get(parentContainer);

        DefaultContainerContext containerContext = new DefaultContainerContext(container, parentContainerContext, searchContext);
        containerContexts.put(container, containerContext);

        if (parentContainerContext != null) {
            containerContext.getHookDefinitions().addAll(parentContainerContext.getHookDefinitions());
        }

        for (Class cls = container.getClass(); isClassSupported(cls); cls = cls.getSuperclass()) {
            hookDefinitionAdder.addHookDefinitions(cls.getDeclaredAnnotations(), containerContext.getHookDefinitions());
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

    //-------- initParentContainer --------

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

    //-------- initChildrenContainers --------

    private void initChildrenContainers(Object container, SearchContext searchContext) {
        forAllDeclaredFieldsInHierarchyOf(container, field -> {
            if (isContainer(field)) {
                Class fieldClass = field.getType();
                Object existingChildContainer = containerInstances.get(fieldClass);
                if (existingChildContainer == null) {
                    Object childContainer = containerInstantiator.newInstance(fieldClass, containerContexts.get(container));
                    initContainer(childContainer, container, searchContext);
                    setFieldInContainer(field, container, childContainer);
                    containerInstances.put(fieldClass, childContainer);
                    inject(childContainer, container, searchContext);
                } else {
                    setFieldInContainer(field, container, existingChildContainer);
                }
            }
        });
    }

    private void setFieldInContainer(Field field, Object container, Object value) {
        try {
            ReflectionUtils.set(field, container, value);
        } catch (IllegalAccessException e) {
            throw new FluentInjectException("Can't set field " + field + " with value " + value, e);
        }
    }

    //-------- initFluentElements --------

    private void initFluentElements(Object container, SearchContext searchContext) {
        ContainerContext containerContext = containerContexts.get(container);

        forAllDeclaredFieldsInHierarchyOf(container, field -> {
            if (injectionSupportValidator.isSupported(container, field)) {
                ArrayList<HookDefinition<?>> fieldHookDefinitions = new ArrayList<>(containerContext.getHookDefinitions());
                hookDefinitionAdder.addHookDefinitions(field.getAnnotations(), fieldHookDefinitions);
                InjectionElementLocatorFactory locatorFactory = new InjectionElementLocatorFactory(searchContext);
                InjectionElementLocator locator = locatorFactory.createLocator(field);
                if (locator != null) {
                    ComponentAndProxy fieldValue = fieldInitializer.initFieldElements(locator, field);
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

    private void forAllDeclaredFieldsInHierarchyOf(Object container, Consumer<Field> fieldConsumer) {
        for (Class cls = container.getClass(); isClassSupported(cls); cls = cls.getSuperclass()) {
            for (Field field : cls.getDeclaredFields()) {
                fieldConsumer.accept(field);
            }
        }
    }
}
