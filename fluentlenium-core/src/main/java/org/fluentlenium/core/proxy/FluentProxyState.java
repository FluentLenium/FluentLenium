package org.fluentlenium.core.proxy;

/**
 * State of the element locator proxy.
 *
 * @param <T> type of the locator handler result
 */
public interface FluentProxyState<T> {

    /**
     * Check if the element is present in the DOM.
     *
     * @return true if the element is present, false otherwise
     */
    boolean present();

    /**
     * Search for the element now, actually performing the search on the {@link org.openqa.selenium.WebDriver}.
     * <p>
     * It has no effect if the element is already loaded.
     *
     * @return this object reference to chain calls.
     * @throws org.openqa.selenium.NoSuchElementException         if the element is not present, and has never been.
     * @throws org.openqa.selenium.StaleElementReferenceException if the element has been present, but is not present anymore.
     */
    T now();

    /**
     * Reset the element. Subsequent calls will perform the search again, instead of using the cached result.
     *
     * @return this object reference to chain calls.
     */
    T reset();

    /**
     * Check this element is loaded.
     *
     * @return true if the element is loaded, false otherwise
     */
    boolean isLoaded();
}
