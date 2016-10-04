package org.fluentlenium.adapter;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.FluentDriver;

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
    public void setFluentControl(final FluentControl fluentControl) {
        this.fluentControl = fluentControl;
    }
}
