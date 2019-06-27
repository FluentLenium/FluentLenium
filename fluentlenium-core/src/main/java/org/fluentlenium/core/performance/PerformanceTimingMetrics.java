package org.fluentlenium.core.performance;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * Wraps the performance timing metrics returned by {@code window.performance.timing} from the browser,
 * and provides convenience methods to access each metric.
 *
 * @see DefaultPerformanceTiming
 */
public class PerformanceTimingMetrics {

    /**
     * The values are stored as {@link Object} because not all values have type {@code long}.
     */
    private final Map<String, Object> timingMetrics;

    public PerformanceTimingMetrics(Map<String, Object> timingMetrics) {
        this.timingMetrics = ImmutableMap.copyOf(requireNonNull(timingMetrics));
    }

    public long getUnloadEventStart() {
        return (Long) timingMetrics.get(PerformanceTimingEvent.UNLOAD_EVENT_START.getEvent());
    }

    public long getUnloadEventEnd() {
        return (Long) timingMetrics.get(PerformanceTimingEvent.UNLOAD_EVENT_END.getEvent());
    }

    public long getRedirectStart() {
        return (Long) timingMetrics.get(PerformanceTimingEvent.REDIRECT_START.getEvent());
    }

    public long getRedirectEnd() {
        return (Long) timingMetrics.get(PerformanceTimingEvent.REDIRECT_END.getEvent());
    }

    public long getNavigationStart() {
        return (Long) timingMetrics.get(PerformanceTimingEvent.NAVIGATION_START.getEvent());
    }

    public long getFetchStart() {
        return (Long) timingMetrics.get(PerformanceTimingEvent.FETCH_START.getEvent());
    }

    public long getDomainLookupStart() {
        return (Long) timingMetrics.get(PerformanceTimingEvent.DOMAIN_LOOKUP_START.getEvent());
    }

    public long getDomainLookupEnd() {
        return (Long) timingMetrics.get(PerformanceTimingEvent.DOMAIN_LOOKUP_END.getEvent());
    }

    public long getConnectStart() {
        return (Long) timingMetrics.get(PerformanceTimingEvent.CONNECT_START.getEvent());
    }

    public long getConnectEnd() {
        return (Long) timingMetrics.get(PerformanceTimingEvent.CONNECT_END.getEvent());
    }

    public long getSecureConnectionStart() {
        return (Long) timingMetrics.get(PerformanceTimingEvent.SECURE_CONNECTION_START.getEvent());
    }

    public long getRequestStart() {
        return (Long) timingMetrics.get(PerformanceTimingEvent.REQUEST_START.getEvent());
    }

    public long getResponseStart() {
        return (Long) timingMetrics.get(PerformanceTimingEvent.RESPONSE_START.getEvent());
    }

    public long getResponseEnd() {
        return (Long) timingMetrics.get(PerformanceTimingEvent.RESPONSE_END.getEvent());
    }

    public long getDomLoading() {
        return (Long) timingMetrics.get(PerformanceTimingEvent.DOM_LOADING.getEvent());
    }

    public long getDomInteractive() {
        return (Long) timingMetrics.get(PerformanceTimingEvent.DOM_INTERACTIVE.getEvent());
    }

    public long getDomContentLoadedEventStart() {
        return (Long) timingMetrics.get(PerformanceTimingEvent.DOM_CONTENT_LOADED_EVENT_START.getEvent());
    }

    public long getDomContentLoadedEventEnd() {
        return (Long) timingMetrics.get(PerformanceTimingEvent.DOM_CONTENT_LOADED_EVENT_END.getEvent());
    }

    public long getDomComplete() {
        return (Long) timingMetrics.get(PerformanceTimingEvent.DOM_COMPLETE.getEvent());
    }

    public long getLoadEventStart() {
        return (Long) timingMetrics.get(PerformanceTimingEvent.LOAD_EVENT_START.getEvent());
    }

    public long getLoadEventEnd() {
        return (Long) timingMetrics.get(PerformanceTimingEvent.LOAD_EVENT_END.getEvent());
    }
}


