package io.fluentlenium.core.components;

import org.openqa.selenium.WebElement;

/**
 * A listener interface for components registration.
 */
public interface ComponentsListener {
    /**
     * Invoked when a component has been registered.
     *
     * @param element   underlying element
     * @param component registered component
     */
    void componentRegistered(WebElement element, Object component);

    /**
     * Invoked when a component has been released.
     *
     * @param element   underlying element
     * @param component released component
     */
    void componentReleased(WebElement element, Object component);
}
