package io.fluentlenium.core.components;

import org.openqa.selenium.WebElement;

import java.util.Map;

/**
 * Listen to lazy components events.
 *
 * @param <T> type of component
 */
public interface LazyComponentsListener<T> {
    /**
     * Invoked when components are initialized for web elements.
     *
     * @param componentMap map matching WebElement objects to their components couterparts.
     */
    void lazyComponentsInitialized(Map<WebElement, T> componentMap);
}
