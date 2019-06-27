package org.fluentlenium.core.performance;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * Wraps the performance timing metrics returned by the {@code window.performance.timing} Javascript attribute
 * from the browser, and provides convenience methods to access each metric.
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
        return getEvent(PerformanceTimingEvent.UNLOAD_EVENT_START);
    }

    public long getUnloadEventEnd() {
        return getEvent(PerformanceTimingEvent.UNLOAD_EVENT_END);
    }

    public long getRedirectStart() {
        return getEvent(PerformanceTimingEvent.REDIRECT_START);
    }

    public long getRedirectEnd() {
        return getEvent(PerformanceTimingEvent.REDIRECT_END);
    }

    public long getNavigationStart() {
        return getEvent(PerformanceTimingEvent.NAVIGATION_START);
    }

    public long getFetchStart() {
        return getEvent(PerformanceTimingEvent.FETCH_START);
    }

    public long getDomainLookupStart() {
        return getEvent(PerformanceTimingEvent.DOMAIN_LOOKUP_START);
    }

    public long getDomainLookupEnd() {
        return getEvent(PerformanceTimingEvent.DOMAIN_LOOKUP_END);
    }

    public long getConnectStart() {
        return getEvent(PerformanceTimingEvent.CONNECT_START);
    }

    public long getConnectEnd() {
        return getEvent(PerformanceTimingEvent.CONNECT_END);
    }

    public long getSecureConnectionStart() {
        return getEvent(PerformanceTimingEvent.SECURE_CONNECTION_START);
    }

    public long getRequestStart() {
        return getEvent(PerformanceTimingEvent.REQUEST_START);
    }

    public long getResponseStart() {
        return getEvent(PerformanceTimingEvent.RESPONSE_START);
    }

    public long getResponseEnd() {
        return getEvent(PerformanceTimingEvent.RESPONSE_END);
    }

    public long getDomLoading() {
        return getEvent(PerformanceTimingEvent.DOM_LOADING);
    }

    public long getDomInteractive() {
        return getEvent(PerformanceTimingEvent.DOM_INTERACTIVE);
    }

    public long getDomContentLoadedEventStart() {
        return getEvent(PerformanceTimingEvent.DOM_CONTENT_LOADED_EVENT_START);
    }

    public long getDomContentLoadedEventEnd() {
        return getEvent(PerformanceTimingEvent.DOM_CONTENT_LOADED_EVENT_END);
    }

    public long getDomComplete() {
        return getEvent(PerformanceTimingEvent.DOM_COMPLETE);
    }

    public long getLoadEventStart() {
        return getEvent(PerformanceTimingEvent.LOAD_EVENT_START);
    }

    public long getLoadEventEnd() {
        return getEvent(PerformanceTimingEvent.LOAD_EVENT_END);
    }
    
    private long getEvent(PerformanceTimingEvent event) {
        return (Long) timingMetrics.get(event.getEvent());
    }
}


