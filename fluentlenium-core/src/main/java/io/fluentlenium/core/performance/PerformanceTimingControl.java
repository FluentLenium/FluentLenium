package io.fluentlenium.core.performance;

/**
 * Control interface for accessing an API for querying W3C Performance Timing event values.
 */
public interface PerformanceTimingControl {

    /**
     * Returns an object for accessing Performance Timing API queries.
     *
     * @return a {@link PerformanceTiming} object
     */
    PerformanceTiming performanceTiming();
}
