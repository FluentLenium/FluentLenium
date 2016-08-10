package org.fluentlenium.adapter;

import org.fluentlenium.core.FluentDriver;
import org.fluentlenium.core.FluentDriverProvider;

/**
 * Container for FluentDriver of the actual test.
 */
public interface DriverContainer extends FluentDriverProvider {
    /**
     * Set the FluentDriver for actual test.
     *
     * @param driver FluentDriver intialized for actual test.
     */
    void setFluentDriver(FluentDriver driver);
}
