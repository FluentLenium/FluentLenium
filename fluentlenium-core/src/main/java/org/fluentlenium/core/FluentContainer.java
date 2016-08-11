package org.fluentlenium.core;

/**
 * Container object that can be initialized with a {@link FluentControl}.
 */
public interface FluentContainer {
    /**
     * Init this container with a {@link FluentControl} instance.
     *
     * @param control
     */
    void initFluent(FluentControl control);
}
