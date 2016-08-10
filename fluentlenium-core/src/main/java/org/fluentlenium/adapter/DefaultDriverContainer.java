package org.fluentlenium.adapter;

import org.fluentlenium.core.FluentDriver;

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
