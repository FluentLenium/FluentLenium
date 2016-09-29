package org.fluentlenium.core.components;

import org.openqa.selenium.WebElement;

import java.util.Set;

/**
 * Accessor interface to retrieve a component from it's selenium element.
 */
public interface ComponentsAccessor {
    /**
     * Get the related components from the given element.
     *
     * @param element selenium element
     * @return components wrapping the given selenium element
     */
    Set<Object> getComponents(WebElement element);

}
