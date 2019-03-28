package org.fluentlenium.core.proxy;

/**
 * Interface to control and retrieve the status of {@link LocatorHandler}s.
 */
public interface LocatorStatusHandler {

    /**
     * Check if this handler has loaded it's result.
     *
     * @return true if the result is loaded, false otherwise
     */
    boolean loaded();

    /**
     * Reset the loaded data.
     */
    void reset();

    /**
     * If result is not loaded, load result immediately. If it's already loaded, it has no effect.
     */
    void now();

    /**
     * Check if the result is present.
     *
     * @return true if result is present, false otherwise
     */
    boolean present();
}
