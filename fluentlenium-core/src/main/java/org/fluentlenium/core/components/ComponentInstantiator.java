package org.fluentlenium.core.components;

import org.openqa.selenium.WebElement;

public interface ComponentInstantiator {
    /**
     * Create and register a new component from the given element.
     *
     * @param componentClass type of the component
     * @param element wrapped element
     * @param <T> type of the component
     *
     * @return new instance of the component.
     */
    <T> T newComponent(Class<T> componentClass, WebElement element);

    /**
     * Check if this class is a component class.
     *
     * @param componentClass
     */
    boolean isComponentClass(Class<?> componentClass);
}
