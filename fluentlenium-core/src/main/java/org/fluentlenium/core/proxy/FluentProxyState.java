package org.fluentlenium.core.proxy;

import java.util.Optional;

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
     * Search for the element now, actually performing the search on the {@link org.openqa.selenium.WebDriver}.
     * <p>
     * It has no effect if the element is already loaded.
     *
     * @param force force the search even if element is already loaded
     * @return this object reference to chain calls.
     * @throws org.openqa.selenium.NoSuchElementException         if the element is not present, and has never been.
     * @throws org.openqa.selenium.StaleElementReferenceException if the element has been present, but is not present anymore.
     */
    T now(boolean force);

    /**
     * Reset the element. Subsequent calls will perform the search again, instead of using the cached result.
     *
     * @return this object reference to chain calls.
     */
    T reset();

    /**
     * Check if the element is loaded.
     *
     * @return true if the element is loaded, false otherwise
     */
    boolean loaded();

    /**
     * Builds an optional. If underlying element is lazy, search will be perfomed when invoking this method.
     *
     * @return An optional wrapping this.
     * @see #present()
     * @see #now()
     */
    Optional<T> optional();
}
