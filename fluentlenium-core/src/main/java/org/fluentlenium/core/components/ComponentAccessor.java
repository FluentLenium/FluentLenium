package org.fluentlenium.core.components;

import org.openqa.selenium.WebElement;

/**
 * Accessor interface to retrieve a component from it's selenium element.
 */
public interface ComponentAccessor {
    /**
     * Get the related component from the given element.
     *
     * @param element selenium element
     * @return component wrapping the given selenium element
     */
    Object getComponent(WebElement element);

}
