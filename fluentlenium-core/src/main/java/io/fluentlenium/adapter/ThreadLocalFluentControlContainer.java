package io.fluentlenium.adapter;

import io.fluentlenium.core.FluentControl;
import io.fluentlenium.core.FluentDriver;

/**
 * Alternative {@link FluentControlContainer} implementation, using a ThreadLocal variable to store the
 * {@link FluentDriver} instance.
 */
public class ThreadLocalFluentControlContainer implements FluentControlContainer {
    private final ThreadLocal<FluentControl> fluentControls = new ThreadLocal<>();

    @Override
    public FluentControl getFluentControl() {
        return fluentControls.get();
    }

    @Override
    public void setFluentControl(FluentControl fluentControl) {
        if (fluentControl == null) {
            fluentControls.remove();
        } else {
            fluentControls.set(fluentControl);
        }
    }
}
