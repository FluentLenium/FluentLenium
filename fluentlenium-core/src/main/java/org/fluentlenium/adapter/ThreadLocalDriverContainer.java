package org.fluentlenium.adapter;

import org.fluentlenium.core.FluentDriver;

/**
 * Use ThreadLocal to get and set FluentDriver.
 */
public class ThreadLocalDriverContainer implements DriverContainer {
    private ThreadLocal<FluentDriver> drivers = new ThreadLocal<>();

    @Override
    public FluentDriver getFluentDriver() {
        return drivers.get();
    }

    @Override
    public void setFluentDriver(FluentDriver driver) {
        if (driver == null) {
            this.drivers.remove();
        } else {
            this.drivers.set(driver);
        }
    }
}
