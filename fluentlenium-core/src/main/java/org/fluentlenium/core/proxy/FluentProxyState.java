package org.fluentlenium.core.proxy;

/**
 * State of the element locator proxy.
 *
 * @param <T>
 */
public interface FluentProxyState<T> {
    /**
     * Check if the element is present in the DOM.
     * <p>
     * return true if the element is present.
     */
    boolean isPresent();

    /**
     * Find the element now, loading the underlying element before returning.
     * <p>
     * It has no effect if the element is already loaded.
     *
     * @throws org.openqa.selenium.NoSuchElementException         if the element is not present, and has never been.
     * @throws org.openqa.selenium.StaleElementReferenceException if the element has been present, but is not present anymore.
     */
    T now();

    /**
     * Reset the element. Subsequent calls will perform the search again, instead of using the cached result.
     */
    T reset();

    /**
     * Check this element is loaded.
     */
    boolean isLoaded();
}
