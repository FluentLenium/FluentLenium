package org.fluentlenium.core;

/**
 * Provides the FluentDriver for actual test.
 */
public interface FluentControlProvider {
    /**
     *
     * @return FluentDriver for actual test.
     */
    FluentControl getFluentControl();
}
