package io.fluentlenium.core.performance;

import io.fluentlenium.utils.Preconditions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import static io.fluentlenium.core.performance.PerformanceTimingEvent.NAVIGATION_START;
import static io.fluentlenium.core.performance.PerformanceTimingEvent.SECURE_CONNECTION_START;
import static io.fluentlenium.utils.Preconditions.checkArgument;

/**
 * Default implementation of {@link PerformanceTiming}.
 * <p>
 * Via this implementation you can retrieve the W3C Performance Timing event values from the browser relative to
 * the value of the {@code navigationStart} attribute value, thus it won't return epoch values as querying the
 * corresponding Javascript attribute directly would, but rather handles {@code navigationStart} as zero and returns
 * time values passed since that point in time.
 * <p>
 * This implementation executes the query ({@code window.performance.timing.}) with a simple {@link JavascriptExecutor}
 * in a synchronous way.
 * <p>
 * If a query for a certain metric returns 0 it means it happened at the same moment (at least in epoch)
 * than {@code navigationStart}.
 * <p>
 * A query for a certain metrics returns a negative value if the event has not been registered on the page,
 * or it is not feasible/valid for the given page/page load/redirect.
 */
public class DefaultPerformanceTiming implements PerformanceTiming {

    private static final String PERFORMANCE_TIMING_SCRIPT = "return window.performance.timing;";
    private static final String PERFORMANCE_TIMING_EVENTS_SCRIPT = "return window.performance.timing.%s;";

    private final WebDriver driver;
    private final PerformanceTimingMetricsFactory metricsFactory = new PerformanceTimingMetricsFactory();

    public DefaultPerformanceTiming(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public long getEventValue(PerformanceTimingEvent event) {
        Preconditions.checkArgument(event, "The event should not be null.");
        return timePassedUntil(execute(scriptFor(event)));
    }

    @Override
    public Object secureConnectionStart() {
        Object secureConnectionStart = execute(scriptFor(PerformanceTimingEvent.SECURE_CONNECTION_START));
        if (secureConnectionStart instanceof Long) {
            secureConnectionStart = timePassedUntil(secureConnectionStart);
        }
        return secureConnectionStart;
    }

    @Override
    public PerformanceTimingMetrics getMetrics() {
        return metricsFactory.createFor(execute(PERFORMANCE_TIMING_SCRIPT));
    }

    private long timePassedUntil(Object eventTime) {
        return ((Long) eventTime) - getNavigationStart();
    }

    private Object execute(String command) {
        return ((JavascriptExecutor) driver).executeScript(command);
    }

    private String scriptFor(PerformanceTimingEvent event) {
        return String.format(PERFORMANCE_TIMING_EVENTS_SCRIPT, event);
    }

    /**
     * Returns the navigation start epoch value.
     * <p>
     * Using this additional method is necessary to avoid running into an infinite loop when calling
     * {@link #getEventValue(PerformanceTimingEvent)}
     */
    private long getNavigationStart() {
        return (Long) execute(scriptFor(PerformanceTimingEvent.NAVIGATION_START));
    }
}
