package org.fluentlenium.core;

import lombok.experimental.Delegate;

/**
 * Default minimal implementation for {@link FluentContainer}.
 */
public class DefaultFluentContainer implements FluentControl, FluentContainer {

    private FluentControl control;

    public DefaultFluentContainer() {
    }

    public DefaultFluentContainer(FluentControl control) {
        this.control = control;
    }

    @Delegate
    private FluentControl getFluentControl() {
        return control;
    }

    @Override
    public void initFluent(FluentControl control) {
        this.control = control;
    }
}
