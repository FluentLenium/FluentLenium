package org.fluentlenium.core.performance;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toMap;
import static org.fluentlenium.core.performance.PerformanceTimingEvent.SECURE_CONNECTION_START;

/**
 * Default implementation of {@link PerformanceTimingMetrics} storing the metrics value as a {@link Map}.
 * <p>
 * It also provides an option to convert all values in the current object to an arbitrary {@link TimeUnit}.
 * For that you can call {@link #in(TimeUnit)} on the current object.
 *
 * @see DefaultPerformanceTiming
 */
public class DefaultPerformanceTimingMetrics implements PerformanceTimingMetrics<DefaultPerformanceTimingMetrics> {

    /**
     * The values are stored as {@link Object} because not all values have type {@code long}.
     */
    private final Map<String, Object> timingMetrics = new HashMap<>();
    private final TimeUnit sourceTimeUnit;

    public DefaultPerformanceTimingMetrics(Map<String, Object> timingMetrics) {
        this(timingMetrics, TimeUnit.MILLISECONDS);
    }

    public DefaultPerformanceTimingMetrics(Map<String, Object> timingMetrics, TimeUnit timeUnit) {
        this.timingMetrics.putAll(requireNonNull(timingMetrics));
        this.sourceTimeUnit = timeUnit;
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
    @Override
    public DefaultPerformanceTimingMetrics in(TimeUnit targetTimeUnit) {
        Map<String, Object> metrics = timingMetrics.entrySet()
                .stream()
                .filter(entry -> canBeCastToLong(entry.getValue()))
                .collect(toMap(
                        Map.Entry::getKey,
                        entry -> targetTimeUnit.convert((Long) entry.getValue(), sourceTimeUnit)
                ));
        //Add metrics that can have values other than long
        metrics.putIfAbsent(SECURE_CONNECTION_START.getEvent(), timingMetrics.get(SECURE_CONNECTION_START.getEvent()));

        return new DefaultPerformanceTimingMetrics(metrics, targetTimeUnit);
    }

    public Map<String, Object> getAllMetrics() {
        return ImmutableMap.copyOf(timingMetrics);
    }

    public TimeUnit getSourceTimeUnit() {
        return sourceTimeUnit;
    }

    @Override
    public long getUnloadEventStart() {
        return getEvent(PerformanceTimingEvent.UNLOAD_EVENT_START);
    }

    @Override
    public long getUnloadEventEnd() {
        return getEvent(PerformanceTimingEvent.UNLOAD_EVENT_END);
    }

    @Override
    public long getRedirectStart() {
        return getEvent(PerformanceTimingEvent.REDIRECT_START);
    }

    @Override
    public long getRedirectEnd() {
        return getEvent(PerformanceTimingEvent.REDIRECT_END);
    }

    @Override
    public long getNavigationStart() {
        return getEvent(PerformanceTimingEvent.NAVIGATION_START);
    }

    @Override
    public long getFetchStart() {
        return getEvent(PerformanceTimingEvent.FETCH_START);
    }

    @Override
    public long getDomainLookupStart() {
        return getEvent(PerformanceTimingEvent.DOMAIN_LOOKUP_START);
    }

    @Override
    public long getDomainLookupEnd() {
        return getEvent(PerformanceTimingEvent.DOMAIN_LOOKUP_END);
    }

    @Override
    public long getConnectStart() {
        return getEvent(PerformanceTimingEvent.CONNECT_START);
    }

    @Override
    public long getConnectEnd() {
        return getEvent(PerformanceTimingEvent.CONNECT_END);
    }

    @Override
    public Object getSecureConnectionStart() {
        return timingMetrics.get(SECURE_CONNECTION_START.getEvent());
    }

    @Override
    public long getRequestStart() {
        return getEvent(PerformanceTimingEvent.REQUEST_START);
    }

    @Override
    public long getResponseStart() {
        return getEvent(PerformanceTimingEvent.RESPONSE_START);
    }

    @Override
    public long getResponseEnd() {
        return getEvent(PerformanceTimingEvent.RESPONSE_END);
    }

    @Override
    public long getDomLoading() {
        return getEvent(PerformanceTimingEvent.DOM_LOADING);
    }

    @Override
    public long getDomInteractive() {
        return getEvent(PerformanceTimingEvent.DOM_INTERACTIVE);
    }

    @Override
    public long getDomContentLoadedEventStart() {
        return getEvent(PerformanceTimingEvent.DOM_CONTENT_LOADED_EVENT_START);
    }

    @Override
    public long getDomContentLoadedEventEnd() {
        return getEvent(PerformanceTimingEvent.DOM_CONTENT_LOADED_EVENT_END);
    }

    @Override
    public long getDomComplete() {
        return getEvent(PerformanceTimingEvent.DOM_COMPLETE);
    }

    @Override
    public long getLoadEventStart() {
        return getEvent(PerformanceTimingEvent.LOAD_EVENT_START);
    }

    @Override
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
