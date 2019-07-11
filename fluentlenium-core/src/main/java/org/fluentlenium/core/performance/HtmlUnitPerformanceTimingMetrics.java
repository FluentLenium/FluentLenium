package org.fluentlenium.core.performance;

import com.gargoylesoftware.htmlunit.javascript.host.performance.PerformanceTiming;

import java.util.concurrent.TimeUnit;

/**
 * HtmlUnit specific implementation for {@link PerformanceTimingMetrics}.
 * <p>
 * Though the {@link PerformanceTiming} is a mock
 * implementation for the PerformanceTiming API, the lack of this adapter might cause issues.
 * <p>
 * And before retrieving performance timing metrics from HtmlUnit, please check the aforementioned class whether it
 * is still a mock object or it returns real values.
 */
public class HtmlUnitPerformanceTimingMetrics implements PerformanceTimingMetrics<HtmlUnitPerformanceTimingMetrics> {

    private final PerformanceTiming timing;
    private final TimeUnit targetTimeUnit;

    public HtmlUnitPerformanceTimingMetrics(PerformanceTiming timing) {
        this(timing, TimeUnit.MILLISECONDS);
    }

    public HtmlUnitPerformanceTimingMetrics(PerformanceTiming timing, TimeUnit timeUnit) {
        this.timing = timing;
        this.targetTimeUnit = timeUnit;
    }

    @Override
    public HtmlUnitPerformanceTimingMetrics in(TimeUnit targetTimeUnit) {
        return new HtmlUnitPerformanceTimingMetrics(this.timing, targetTimeUnit);
    }

    @Override
    public long getUnloadEventStart() {
        return targetTimeUnit.convert(timing.getUnloadEventStart(), TimeUnit.MILLISECONDS);
    }

    @Override
    public long getUnloadEventEnd() {
        return targetTimeUnit.convert(timing.getUnloadEventEnd(), TimeUnit.MILLISECONDS);
    }

    @Override
    public long getRedirectStart() {
        return targetTimeUnit.convert(timing.getRedirectStart(), TimeUnit.MILLISECONDS);
    }

    @Override
    public long getRedirectEnd() {
        return targetTimeUnit.convert(timing.getRedirectEnd(), TimeUnit.MILLISECONDS);
    }

    @Override
    public long getNavigationStart() {
        return targetTimeUnit.convert(timing.getNavigationStart(), TimeUnit.MILLISECONDS);
    }

    @Override
    public long getFetchStart() {
        return targetTimeUnit.convert(timing.getFetchStart(), TimeUnit.MILLISECONDS);
    }

    @Override
    public long getDomainLookupStart() {
        return targetTimeUnit.convert(timing.getDomainLookupStart(), TimeUnit.MILLISECONDS);
    }

    @Override
    public long getDomainLookupEnd() {
        return targetTimeUnit.convert(timing.getDomainLookupEnd(), TimeUnit.MILLISECONDS);
    }

    @Override
    public long getConnectStart() {
        return targetTimeUnit.convert(timing.getConnectStart(), TimeUnit.MILLISECONDS);
    }

    @Override
    public long getConnectEnd() {
        return targetTimeUnit.convert(timing.getConnectEnd(), TimeUnit.MILLISECONDS);
    }

    @Override
    public Object getSecureConnectionStart() {
        return targetTimeUnit.convert(timing.getSecureConnectionStart(), TimeUnit.MILLISECONDS);
    }

    @Override
    public long getRequestStart() {
        throw new UnsupportedOperationException("This performance timing attribute is not implemented in HtmlUnit.");
    }

    @Override
    public long getResponseStart() {
        return targetTimeUnit.convert(timing.getResponseStart(), TimeUnit.MILLISECONDS);
    }

    @Override
    public long getResponseEnd() {
        return targetTimeUnit.convert(timing.getResponseEnd(), TimeUnit.MILLISECONDS);
    }

    @Override
    public long getDomLoading() {
        return targetTimeUnit.convert(timing.getDomLoading(), TimeUnit.MILLISECONDS);
    }

    @Override
    public long getDomInteractive() {
        return targetTimeUnit.convert(timing.getDomInteractive(), TimeUnit.MILLISECONDS);
    }

    @Override
    public long getDomContentLoadedEventStart() {
        return targetTimeUnit.convert(timing.getDomContentLoadedEventStart(), TimeUnit.MILLISECONDS);
    }

    @Override
    public long getDomContentLoadedEventEnd() {
        return targetTimeUnit.convert(timing.getDomContentLoadedEventEnd(), TimeUnit.MILLISECONDS);
    }

    @Override
    public long getDomComplete() {
        return targetTimeUnit.convert(timing.getDomComplete(), TimeUnit.MILLISECONDS);
    }

    @Override
    public long getLoadEventStart() {
        return targetTimeUnit.convert(timing.getLoadEventStart(), TimeUnit.MILLISECONDS);
    }

    @Override
    public long getLoadEventEnd() {
        return targetTimeUnit.convert(timing.getLoadEventEnd(), TimeUnit.MILLISECONDS);
    }
}
