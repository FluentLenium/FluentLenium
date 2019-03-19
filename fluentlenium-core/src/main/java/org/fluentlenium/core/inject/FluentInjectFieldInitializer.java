package org.fluentlenium.core.inject;

import static java.util.Objects.requireNonNull;
import static org.fluentlenium.core.inject.FluentElementInjectionSupportValidator.isWebElement;
import static org.fluentlenium.core.inject.FluentElementInjectionSupportValidator.isListOfWebElement;
import static org.fluentlenium.core.inject.FluentElementInjectionSupportValidator.isListOfFluentWebElement;

import org.fluentlenium.core.components.ComponentsManager;
import org.fluentlenium.core.domain.ComponentList;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.proxy.LocatorProxies;
import org.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Initializes fields to inject based on its type.
 */
final class FluentInjectFieldInitializer {

    private final ComponentsManager componentsManager;
    private final FluentElementInjectionSupportValidator injectionSupportValidator;

    FluentInjectFieldInitializer(ComponentsManager componentsManager,
                                 FluentElementInjectionSupportValidator injectionSupportValidator) {
        this.componentsManager = requireNonNull(componentsManager);
        this.injectionSupportValidator = requireNonNull(injectionSupportValidator);
    }

    /**
     * Initializes the argument field based on its type using the argument element locator,
     *
     * @param locator the element locator
     * @param field the field to initialize
     * @return a {@link ComponentAndProxy} and proxy object storing initialized component and proxy object for that
     */
    ComponentAndProxy<?, ?> initFieldElements(ElementLocator locator, Field field) {
        ComponentAndProxy<?, ?> fieldValue = null;
        if (injectionSupportValidator.isComponent(field)) {
            fieldValue = initFieldAsComponent(locator, field);
        } else if (injectionSupportValidator.isComponentList(field)) {
            fieldValue = initFieldAsComponentList(locator, field);
        } else if (isListOfFluentWebElement(field)) {
            fieldValue = initFieldAsListOfFluentWebElement(locator, field);
        } else if (injectionSupportValidator.isListOfComponent(field)) {
            fieldValue = initFieldAsListOfComponent(locator, field);
        } else if (isWebElement(field)) {
            fieldValue = initFieldAsWebElement(locator);
        } else if (isListOfWebElement(field)) {
            fieldValue = initFieldAsListOfWebElement(locator);
        }
        return fieldValue;
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
        return new ComponentAndProxy<>(component, element);
    }

    private ComponentAndProxy<ComponentList<?>, List<WebElement>> initFieldAsListOfComponent(ElementLocator locator,
                                                                                             Field field) {
        List<WebElement> webElementList = LocatorProxies.createWebElementList(locator);
        ComponentList<?> componentList = componentsManager
                .asComponentList(ReflectionUtils.getFirstGenericType(field), webElementList);
        return new ComponentAndProxy<>(componentList, webElementList);
    }

    private ComponentAndProxy<FluentList<? extends FluentWebElement>, List<WebElement>> initFieldAsListOfFluentWebElement(
            ElementLocator locator, Field field) {
        List<WebElement> webElementList = LocatorProxies.createWebElementList(locator);
        FluentList<? extends FluentWebElement> fluentList = componentsManager
                .asFluentList((Class<? extends FluentWebElement>) ReflectionUtils.getFirstGenericType(field), webElementList);
        return new ComponentAndProxy<>(fluentList, webElementList);
    }

    private ComponentAndProxy<WebElement, WebElement> initFieldAsWebElement(ElementLocator locator) {
        WebElement element = LocatorProxies.createWebElement(locator);
        return new ComponentAndProxy<>(element, element);
    }

    private ComponentAndProxy<List<WebElement>, List<WebElement>> initFieldAsListOfWebElement(ElementLocator locator) {
        List<WebElement> elements = LocatorProxies.createWebElementList(locator);
        return new ComponentAndProxy<>(elements, elements);
    }
}
