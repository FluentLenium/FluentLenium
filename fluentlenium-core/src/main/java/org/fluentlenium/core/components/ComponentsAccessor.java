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

    /**
     * Add a component listener to be notified when a component is registered or unregistered.
     *
     * @param listener components listener
     * @return true if listener is added
     */
    boolean addComponentsListener(ComponentsListener listener);

    /**
     * Remove a component listener.
     *
     * @param listener components listener to remove
     * @return true if listener is removed
     */
    boolean removeComponentsListener(ComponentsListener listener);

}
