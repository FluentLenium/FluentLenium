package org.fluentlenium.core.performance;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toMap;
import static org.fluentlenium.core.performance.PerformanceTimingEvent.NAVIGATION_START;
import static org.fluentlenium.core.performance.PerformanceTimingEvent.SECURE_CONNECTION_START;

/**
 * Default implementation of {@link PerformanceTimingMetrics} storing the metrics value as a {@link Map}.
 * <p>
 * It also provides an option to convert all values in the current object to an arbitrary {@link TimeUnit}.
 * For that you can call {@link #in(TimeUnit)} on the current object.
 * <p>
 * The values returned from this object are not epoch values as querying the corresponding Javascript attribute
 * directly would return, but it rather handles {@code navigationStart} as zero and returns time values passed
 * since that point in time.
 * <p>
 * Take into account that when there is a time difference like {@code 1675ms}, and it is converted to e.g. seconds,
 * the conversion will return {@code 1sec} for that instead of rounding it up to {@code 2sec}.
 * <p>
 * If a query for a certain metric returns 0 it means it happened at the same moment (at least in epoch)
 * than {@code navigationStart}.
 * <p>
 * A query for a certain metrics returns a negative value if the event has not been registered on the page,
 * or it is not feasible/valid for the given page/page load/redirect.
 *
 * @see DefaultPerformanceTiming
 */
public class DefaultPerformanceTimingMetrics implements PerformanceTimingMetrics<DefaultPerformanceTimingMetrics> {

    /**
     * The values are stored as {@link Object}s because not all values have type {@code long}.
     */
    private final Map<String, Object> timingMetrics = new HashMap<>();
    private final TimeUnit sourceTimeUnit;

    /**
     * Creates a new {@link DefaultPerformanceTimingMetrics} object from the argument epoch values.
     * <p>
     * It calculates and converts the epoch values into the differences/times passed since {@code navigationStart}.
     *
     * @param timingMetrics the timing metrics in epoch
     */
    public DefaultPerformanceTimingMetrics(Map<String, Object> timingMetrics) {
        this.timingMetrics.putAll(calculateTimesSinceNavigationStart(requireNonNull(timingMetrics)));
        this.sourceTimeUnit = TimeUnit.MILLISECONDS;
    }

    /**
     * Creates a new {@link DefaultPerformanceTimingMetrics} object from the argument metrics (in the given time unit).
     *
     * @param timingMetrics the metrics
     * @param timeUnit      the time unit of the metrics
     */
    protected DefaultPerformanceTimingMetrics(Map<String, Object> timingMetrics, TimeUnit timeUnit) {
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
        Map<String, Object> metrics = convertEntriesBy(timingMetrics,
                entryValue -> targetTimeUnit.convert((Long) entryValue, sourceTimeUnit));
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
        return getEvent(NAVIGATION_START);
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

    private Map<String, Object> calculateTimesSinceNavigationStart(Map<String, Object> timingMetrics) {
        long navigationStartEpoch = (Long) timingMetrics.get(NAVIGATION_START.getEvent());
        Map<String, Object> metrics = convertEntriesBy(
                timingMetrics, entryValue -> ((Long) entryValue) - navigationStartEpoch);
        //Add metrics that can have values other than long
        metrics.putIfAbsent(SECURE_CONNECTION_START.getEvent(), timingMetrics.get(SECURE_CONNECTION_START.getEvent()));
        return metrics;
    }

    private Map<String, Object> convertEntriesBy(Map<String, Object> timingMetrics, Function<Object, Long> valueMapper) {
        return timingMetrics.entrySet()
                .stream()
                .filter(entry -> canBeCastToLong(entry.getValue()))
                .collect(toMap(
                        Map.Entry::getKey,
                        entry -> valueMapper.apply(entry.getValue())
                ));
    }

    private long getEvent(PerformanceTimingEvent event) {
        return (Long) timingMetrics.get(event.getEvent());
    }

    @SuppressWarnings("PMD.UnusedLocalVariable")
    private boolean canBeCastToLong(Object value) {
        try {
            long converted = (Long) value;
            return true;
        } catch (ClassCastException cce) {
            return false;
        }
    }
}
