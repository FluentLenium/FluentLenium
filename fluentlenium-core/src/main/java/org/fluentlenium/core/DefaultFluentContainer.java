package org.fluentlenium.core;

import lombok.experimental.Delegate;

/**
 * Default minimal implementation for {@link FluentContainer}.
 */
public class DefaultFluentContainer implements FluentControl, FluentContainer {

    protected FluentControl control;

    /**
     * Creates a new container.
     */
    public DefaultFluentContainer() {
        // Default constructor
    }

    /**
     * Creates a new container, using given fluent control.
     *
     * @param control fluent control
     */
    public DefaultFluentContainer(FluentControl control) {
        this.control = control;
    }

    @Delegate
    private FluentControl getFluentControl() { // NOPMD UnusedPrivateMethod
        return control;
    }

    @Override
    public void initFluent(FluentControl control) {
        this.control = control;
    }
}
