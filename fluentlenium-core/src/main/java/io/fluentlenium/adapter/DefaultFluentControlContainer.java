package io.fluentlenium.adapter;

import io.fluentlenium.core.FluentControl;
import io.fluentlenium.core.FluentDriver;

/**
 * Default {@link FluentControlContainer} implementation, using a simple variable to store the {@link FluentDriver} instance.
 */
public class DefaultFluentControlContainer implements FluentControlContainer {
    private FluentControl fluentControl;

    @Override
    public FluentControl getFluentControl() {
        return fluentControl;
    }

    @Override
    public void setFluentControl(FluentControl fluentControl) {
        this.fluentControl = fluentControl;
    }
}
