package org.fluentlenium.core.performance;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.Map;

import static org.fluentlenium.utils.Preconditions.checkArgument;

/**
 * Default implementation of {@link PerformanceTiming}.
 * <p>
 * Via this implementation you can retrieve the W3C Performance Timing event values from the browser,
 * querying the {@code window.performance.timing.} property from the page.
 * <p>
 * This implementation executes the query with a simple {@link JavascriptExecutor} in a synchronous way.
 */
public class DefaultPerformanceTiming implements PerformanceTiming {

    private static final String PERFORMANCE_TIMING_EVENTS_SCRIPT = "return window.performance.timing.%s;";
    private static final String PERFORMANCE_TIMING_SCRIPT = "return window.performance.timing;";

    private final PerformanceTimingMetricsFactory metricsFactory = new PerformanceTimingMetricsFactory();
    private final WebDriver driver;

    public DefaultPerformanceTiming(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public long getEventValue(PerformanceTimingEvent event) {
        checkArgument(event, "The event should not be null.");
        return (Long) execute(String.format(PERFORMANCE_TIMING_EVENTS_SCRIPT, event));
    }

    @Override
    public PerformanceTimingMetrics getMetrics() {
        return metricsFactory.createFor(execute(PERFORMANCE_TIMING_SCRIPT));
    }

    private Object execute(String command) {
        return ((JavascriptExecutor) driver).executeScript(command);
    }
}
