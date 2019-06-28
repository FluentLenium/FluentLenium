package org.fluentlenium.core.performance;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toMap;
import static org.fluentlenium.core.performance.PerformanceTimingEvent.SECURE_CONNECTION_START;

/**
 * Wraps the performance timing metrics returned by the {@code window.performance.timing} Javascript attribute
 * from the browser, and provides convenience methods to access each metric.
 * <p>
 * There is also option to get a whole new {@link PerformanceTimingMetrics} object having all value converted
 * to an arbitrary {@link TimeUnit}. For that you can call {@link #in(TimeUnit)} on the current object.
 *
 * @see DefaultPerformanceTiming
 */
public class PerformanceTimingMetrics {

    /**
     * The values are stored as {@link Object} because not all values have type {@code long}.
     */
    private final Map<String, Object> timingMetrics = new HashMap<>();
    private TimeUnit sourceTimeUnit = TimeUnit.MILLISECONDS;

    public PerformanceTimingMetrics(Map<String, Object> timingMetrics) {
        this.timingMetrics.putAll(requireNonNull(timingMetrics));
    }

    /**
     * Returns a new metrics object having all metric values converted to the target {@link TimeUnit}.
     * <p>
     * Values that can have other than long values are converted if they are {@code long} values,
     * otherwise added to the new metrics object unchanged.
     *
     * @param targetTimeUnit the time unit to convert the metrics to
     * @return the converted metrics object
     */
    public PerformanceTimingMetrics in(TimeUnit targetTimeUnit) {
        Map<String, Object> metrics = timingMetrics.entrySet()
                .stream()
                .filter(entry -> canBeCastToLong(entry.getValue()))
                .collect(toMap(
                        Map.Entry::getKey,
                        entry -> targetTimeUnit.convert((Long) entry.getValue(), sourceTimeUnit)
                ));
        //Add metrics that can have values other than long
        metrics.putIfAbsent(SECURE_CONNECTION_START.getEvent(), timingMetrics.get(SECURE_CONNECTION_START.getEvent()));

        timingMetrics.clear();
        timingMetrics.putAll(metrics);
        sourceTimeUnit = targetTimeUnit;
        return this;
    }

    public Map<String, Object> getAllMetrics() {
        return ImmutableMap.copyOf(timingMetrics);
    }

    public TimeUnit getSourceTimeUnit() {
        return sourceTimeUnit;
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

    /**
     * According to the official documentation of the
     * <a href="https://www.w3.org/TR/navigation-timing/#dom-performancetiming-secureconnectstart">
     * secureConnectionStart attribute</a> this attribute is optional and may be set as {@code undefined}.
     * <p>
     * It is up to the user of this method to check whether it is present and convert it accordingly.
     *
     * @return the metric value
     */
    public Object getSecureConnectionStart() {
        return timingMetrics.get(SECURE_CONNECTION_START.getEvent());
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

    private boolean canBeCastToLong(Object value) {
        try {
            long converted = (Long) value;
            return true;
        } catch (ClassCastException cce) {
            return false;
        }
    }
}


