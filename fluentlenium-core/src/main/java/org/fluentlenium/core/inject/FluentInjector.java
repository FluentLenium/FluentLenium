package org.fluentlenium.core.inject;

import org.fluentlenium.core.FluentContainer;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.annotation.AjaxElement;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.components.ComponentsManager;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.events.ContainerAnnotationsEventsRegistry;
import org.fluentlenium.core.proxy.Proxies;
import org.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Handle injection of @AjaxElement proxies, @Page objects and @FindBy.
 */
public class FluentInjector implements FluentInjectControl {

    private final ConcurrentMap<Class, Object> pageInstances = new ConcurrentHashMap<>();
    private final ConcurrentMap<Object, ContainerAnnotationsEventsRegistry> eventsContainerSupport = new ConcurrentHashMap<>();

    private final FluentControl fluentControl;
    private final ComponentsManager componentsManager;
    private final ContainerInstanciator containerInstanciator;

    public FluentInjector(FluentControl fluentControl, ComponentsManager componentsManager, ContainerInstanciator instanciator) {
        this.fluentControl = fluentControl;
        this.componentsManager = componentsManager;
        this.containerInstanciator = instanciator;
    }

    /**
     * Release all loaded containers.
     */
    public void release() {
        pageInstances.clear();
        for (ContainerAnnotationsEventsRegistry support : eventsContainerSupport.values()) {
            support.close();
        }
        eventsContainerSupport.clear();
    }

    @Override
    public <T> T newInstance(Class<T> cls) {
        T container = containerInstanciator.newInstance(cls);
        inject(container);
        return container;
    }

    @Override
    public void inject(Object... containers) {
        for (Object container : containers) {
            inject(container);
        }
    }

    @Override
    public void inject(Object container) {
        initContainer(container);
        initChildrenContainers(container);
        initFluentElements(container);

        // Default Selenium WebElement injection.
        PageFactory.initElements(fluentControl.getDriver(), container);
    }

    private void initContainer(Object container) {
        if (container instanceof FluentContainer) {
            ((FluentContainer) container).initFluent(fluentControl);
        }
        initEventAnnotations(container);
    }

    private void initEventAnnotations(final Object container) {
        if (fluentControl.getDriver() instanceof EventFiringWebDriver) {
            eventsContainerSupport.put(container, new ContainerAnnotationsEventsRegistry((EventFiringWebDriver) fluentControl.getDriver(), container));
        }
    }

    private static boolean isContainer(Field field) {
        return field.isAnnotationPresent(Page.class);
    }

    private static boolean isClassSupported(Class<?> cls) {
        return cls != Object.class && cls != null;
    }

    private void initChildrenContainers(Object container) {
        for (Class cls = container.getClass(); isClassSupported(cls); cls = cls.getSuperclass()) {
            for (Field field : cls.getDeclaredFields()) {
                if (isContainer(field)) {
                    Class fieldClass = field.getType();
                    Object existingChildContainer = pageInstances.get(fieldClass);
                    if (existingChildContainer != null) {
                        try {
                            ReflectionUtils.set(field, container, existingChildContainer);
                        } catch (IllegalAccessException e) {
                            throw new FluentInjectException("Can't set field " + field + " with value " + existingChildContainer, e);
                        }
                    } else {
                        Object childContainer = containerInstanciator.newInstance(fieldClass);
                        initContainer(childContainer);
                        try {
                            ReflectionUtils.set(field, container, childContainer);
                        } catch (IllegalAccessException e) {
                            throw new FluentInjectException("Can't set field " + field + " with value " + childContainer, e);
                        }
                        pageInstances.putIfAbsent(fieldClass, childContainer);
                        inject(childContainer);
                    }
                }
            }
        }
    }

    private <T extends FluentControl> void initFluentElements(Object container) {
        for (Class cls = container.getClass(); isClassSupported(cls); cls = cls.getSuperclass()) {
            for (Field fieldFromPage : cls.getDeclaredFields()) {
                if (isSupported(container, fieldFromPage)) {
                    AjaxElement annotation = fieldFromPage.getAnnotation(AjaxElement.class);
                    ElementLocatorFactory locatorFactory;
                    if (annotation == null) {
                        locatorFactory = new DefaultElementLocatorFactory(this.fluentControl.getDriver());
                    } else {
                        locatorFactory = new ConfigurableAjaxElementLocatorFactory(this.fluentControl.getDriver(), annotation);
                    }
                    initFieldElements(locatorFactory, container, fieldFromPage);
                }
            }
        }
    }

    private boolean isSupported(Object container, Field field) {
        return isValueNull(container, field) && !field.isAnnotationPresent(NoInject.class) && !Modifier.isFinal(field.getModifiers()) && (isListOfFluentWebElement(field) || isList(field) || isComponent(field));
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

    private static boolean isListOfFluentWebElement(Field field) {
        if (isList(field)) {
            Class<?> genericType = getFirstGenericType(field);
            if (FluentWebElement.class.isAssignableFrom(genericType)) {
                return true;
            }
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

    private Object initFieldElements(ElementLocatorFactory factory, Object container, Field field) {
        ElementLocator locator = factory.createLocator(field);
        if (locator == null) {
            return null;
        }

        try {
            if (isListOfFluentWebElement(field)) {
                return initFieldAsListOfFluentWebElement(locator, container, field);
            } else if (isList(field)) {
                return initFieldAsList(locator, container, field);
            } else if (isComponent(field)) {
                return initFieldAsElement(locator, container, field);
            }
        } catch (IllegalAccessException e) {
            throw new FluentInjectException("Unable to find an accessible constructor with an argument of type WebElement in " + field.getType(), e);
        }
        return null;
    }

    private Object initFieldAsElement(ElementLocator locator, Object container, Field field) throws IllegalAccessException {
        Object proxy = Proxies.createComponent(locator, field.getType(), componentsManager);
        ReflectionUtils.set(field, container, proxy);
        return proxy;
    }

    private List<?> initFieldAsList(ElementLocator locator, Object container, Field field) throws IllegalAccessException {
        List<?> proxy = Proxies.createComponentList(locator, getFirstGenericType(field), componentsManager);
        ReflectionUtils.set(field, container, proxy);
        return proxy;
    }

    private FluentList<? extends FluentWebElement> initFieldAsListOfFluentWebElement(ElementLocator locator, Object container, Field field) throws IllegalAccessException {
        FluentList<? extends FluentWebElement> proxy = Proxies.createFluentList(locator, (Class<? extends FluentWebElement>) getFirstGenericType(field), componentsManager);
        ReflectionUtils.set(field, container, proxy);
        return proxy;
    }
}
