package io.fluentlenium.core.performance;

import com.gargoylesoftware.htmlunit.javascript.host.performance.PerformanceTiming;

import java.util.Map;

/**
 * Factory for creating {@link PerformanceTimingMetrics} objects.
 */
public class PerformanceTimingMetricsFactory {

    /**
     * Creates a {@link PerformanceTimingMetrics} object based on the type of the argument timing object.
     * <p>
     * Throws an exception if there is no adapter for the type of the argument object.
     * <p>
     * If you would like to get the performance timing object from the browser returned as an {@link Object}
     * just execute the appropriate Javascript code "manually" from your code.
     *
     * @param performanceTiming the source performance timing object retrieved from the browser
     * @return a new performance timing metrics object
     */
    @SuppressWarnings("unchecked")
    public PerformanceTimingMetrics createFor(Object performanceTiming) {
        PerformanceTimingMetrics metrics;

        if (performanceTiming instanceof Map) {
            metrics = new DefaultPerformanceTimingMetrics((Map<String, Object>) performanceTiming);
        } else if (performanceTiming instanceof PerformanceTiming) {
            metrics = new HtmlUnitPerformanceTimingMetrics((PerformanceTiming) performanceTiming);
        } else {
            throw new UnknownPerformanceTimingImplementationException("There is no suitable adapter implementation"
                    + " for the argument performance timing object."
                    + "\n"
                    + "Please create a GitHub issue for FluentLenium"
                    + " if you think that browser/implementation should be supported."
                    + "\n"
                    + "The object was of type: " + performanceTiming.getClass()
                    + " with value: " + performanceTiming);
        }

        return metrics;
    }
}
