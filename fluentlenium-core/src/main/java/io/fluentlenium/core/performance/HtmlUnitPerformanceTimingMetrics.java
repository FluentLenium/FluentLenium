package io.fluentlenium.core.performance;

import com.gargoylesoftware.htmlunit.javascript.host.performance.PerformanceTiming;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * HtmlUnit specific implementation of {@link PerformanceTimingMetrics}.
 * <p>
 * The values returned from this object are not epoch values as querying the corresponding Javascript attribute
 * directly would return, but it rather handles {@code navigationStart} as zero and returns time values passed
 * since that point in time.
 * <p>
 * If a query for a certain metric returns 0 it means it happened at the same moment (at least in epoch)
 * than {@code navigationStart}.
 * <p>
 * A query for a certain metrics returns a negative value if the event has not been registered on the page,
 * or it is not feasible/valid for the given page/page load/redirect.
 * <p>
 * Though the HtmlUnit {@link PerformanceTiming} class is a mock implementation of the PerformanceTiming API,
 * the lack of this adapter might cause issues.
 * <p>
 * Before retrieving performance timing metrics from HtmlUnit, please check the aforementioned class whether it
 * is still a mock object or meanwhile it has been implemented properly to return real values.
 */
public class HtmlUnitPerformanceTimingMetrics implements PerformanceTimingMetrics<HtmlUnitPerformanceTimingMetrics> {

    private final PerformanceTiming timing;
    private final TimeUnit targetTimeUnit;
    private final long navigationStart;

    /**
     * Creates a new {@link HtmlUnitPerformanceTimingMetrics} object delegating calls to the argument
     * {@link PerformanceTiming}.
     * <p>
     * Sets the time unit of these metrics to {@link TimeUnit#MILLISECONDS}.
     *
     * @param timing the HtmlUnit performance timing object
     */
    public HtmlUnitPerformanceTimingMetrics(PerformanceTiming timing) {
        this(timing, MILLISECONDS);
    }

    /**
     * Creates a new {@link HtmlUnitPerformanceTimingMetrics} object delegating calls to the argument
     * {@link PerformanceTiming}.
     * <p>
     * Sets the time unit of these metrics to the given time unit.
     *
     * @param timing   the HtmlUnit performance timing object
     * @param timeUnit the time unit to convert the metrics to
     */
    public HtmlUnitPerformanceTimingMetrics(PerformanceTiming timing, TimeUnit timeUnit) {
        this.timing = timing;
        this.targetTimeUnit = timeUnit;
        this.navigationStart = targetTimeUnit.convert(timing.getNavigationStart(), MILLISECONDS);
    }

    @Override
    public HtmlUnitPerformanceTimingMetrics in(TimeUnit targetTimeUnit) {
        return new HtmlUnitPerformanceTimingMetrics(this.timing, targetTimeUnit);
    }

    @Override
    public long getUnloadEventStart() {
        return getEventValue(timing::getUnloadEventStart);
    }

    @Override
    public long getUnloadEventEnd() {
        return getEventValue(timing::getUnloadEventEnd);
    }

    @Override
    public long getRedirectStart() {
        return getEventValue(timing::getRedirectStart);
    }

    @Override
    public long getRedirectEnd() {
        return getEventValue(timing::getRedirectEnd);
    }

    @Override
    public long getNavigationStart() {
        return getEventValue(timing::getNavigationStart);
    }

    @Override
    public long getFetchStart() {
        return getEventValue(timing::getFetchStart);
    }

    @Override
    public long getDomainLookupStart() {
        return getEventValue(timing::getDomainLookupStart);
    }

    @Override
    public long getDomainLookupEnd() {
        return getEventValue(timing::getDomainLookupEnd);
    }

    @Override
    public long getConnectStart() {
        return getEventValue(timing::getConnectStart);
    }

    @Override
    public long getConnectEnd() {
        return getEventValue(timing::getConnectEnd);
    }

    @Override
    public Object getSecureConnectionStart() {
        return getEventValue(timing::getSecureConnectionStart);
    }

    @Override
    public long getRequestStart() {
        throw new UnsupportedOperationException("This performance timing attribute is not implemented in HtmlUnit.");
    }

    @Override
    public long getResponseStart() {
        return getEventValue(timing::getResponseStart);
    }

    @Override
    public long getResponseEnd() {
        return getEventValue(timing::getResponseEnd);
    }

    @Override
    public long getDomLoading() {
        return getEventValue(timing::getDomLoading);
    }

    @Override
    public long getDomInteractive() {
        return getEventValue(timing::getDomInteractive);
    }

    @Override
    public long getDomContentLoadedEventStart() {
        return getEventValue(timing::getDomContentLoadedEventStart);
    }

    @Override
    public long getDomContentLoadedEventEnd() {
        return getEventValue(timing::getDomContentLoadedEventEnd);
    }

    @Override
    public long getDomComplete() {
        return getEventValue(timing::getDomComplete);
    }

    @Override
    public long getLoadEventStart() {
        return getEventValue(timing::getLoadEventStart);
    }

    @Override
    public long getLoadEventEnd() {
        return getEventValue(timing::getLoadEventEnd);
    }

    private long getEventValue(Supplier<Long> eventValueSupplier) {
        return targetTimeUnit.convert(eventValueSupplier.get(), MILLISECONDS) - navigationStart;
    }
}
