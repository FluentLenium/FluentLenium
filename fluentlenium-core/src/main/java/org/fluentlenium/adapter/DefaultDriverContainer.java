package org.fluentlenium.adapter;

import org.fluentlenium.core.FluentDriver;

/**
 * Default {@link DriverContainer} implementation, using a simple variable to store the {@link FluentDriver} instance.
 */
public class DefaultDriverContainer implements DriverContainer {
    private FluentDriver driver;

    @Override
    public FluentDriver getFluentDriver() {
        return driver;
    }

    @Override
    public void setFluentDriver(FluentDriver driver) {
        this.driver = driver;
    }
}
