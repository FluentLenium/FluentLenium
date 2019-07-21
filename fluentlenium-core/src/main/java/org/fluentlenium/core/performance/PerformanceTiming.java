package org.fluentlenium.core.performance;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Interface representing the PerformanceTiming API defined by W3C under
 * <a href="https://www.w3.org/TR/navigation-timing/#sec-navigation-timing-interface">Performance Timing interface</a>.
 * <p>
 * From the browser they can be retrieved by querying the value of the following JavaScript attribute:
 * {@code window.performance.timing.<attribute>}.
 * <p>
 * To query the entire timing object in one Javascript query, you can call {@link #getMetrics()} which returns
 * a {@link PerformanceTimingMetrics} object wrapping the timing metrics.
 * <p>
 * Though the default implementation in FluentLenium returns relative values, this interface is flexible enough
 * so that custom implementations may be created.
 */
public interface PerformanceTiming {

    /**
     * Returns the value that corresponds to the {@code navigationStart} event.
     *
     * @return the epoch time for the {@code navigationStart} event
     */
    default long navigationStart() {
        return navigationStart(MILLISECONDS);
    }

    /**
     * Returns the value that corresponds to the {@code navigationStart} event, converted to the given time unit.
     *
     * @param timeUnit the time unit to convert the metrics values to
     * @return the converted time for the {@code navigationStart} event
     */
    default long navigationStart(TimeUnit timeUnit) {
        return getEventValue(PerformanceTimingEvent.NAVIGATION_START, timeUnit);
    }

    /**
     * Returns the value that corresponds to the {@code unloadEventStart} event.
     *
     * @return the epoch time for the {@code unloadEventStart} event
     */
    default long unloadEventStart() {
        return unloadEventStart(MILLISECONDS);
    }

    /**
     * Returns the value that corresponds to the {@code unloadEventStart} event, converted to the given time unit.
     *
     * @param timeUnit the time unit to convert the metrics values to
     * @return the converted time for the {@code unloadEventStart} event
     */
    default long unloadEventStart(TimeUnit timeUnit) {
        return getEventValue(PerformanceTimingEvent.UNLOAD_EVENT_START, timeUnit);
    }

    /**
     * Returns the value that corresponds to the {@code unloadEventEnd} event.
     *
     * @return the epoch time for the {@code unloadEventEnd} event
     */
    default long unloadEventEnd() {
        return unloadEventEnd(MILLISECONDS);
    }

    /**
     * Returns the value that corresponds to the {@code unloadEventEnd} event, converted to the given time unit.
     *
     * @param timeUnit the time unit to convert the metrics values to
     * @return the converted time for the {@code unloadEventEnd} event
     */
    default long unloadEventEnd(TimeUnit timeUnit) {
        return getEventValue(PerformanceTimingEvent.UNLOAD_EVENT_END, timeUnit);
    }

    /**
     * Returns the value that corresponds to the {@code redirectStart} event.
     *
     * @return the epoch time for the {@code redirectStart} event
     */
    default long redirectStart() {
        return redirectStart(MILLISECONDS);
    }

    /**
     * Returns the value that corresponds to the {@code redirectStart} event, converted to the given time unit.
     *
     * @param timeUnit the time unit to convert the metrics values to
     * @return the converted time for the {@code redirectStart} event
     */
    default long redirectStart(TimeUnit timeUnit) {
        return getEventValue(PerformanceTimingEvent.REDIRECT_START, timeUnit);
    }

    /**
     * Returns the value that corresponds to the {@code redirectEnd} event.
     *
     * @return the epoch time for the {@code redirectEnd} event
     */
    default long redirectEnd() {
        return redirectEnd(MILLISECONDS);
    }

    /**
     * Returns the value that corresponds to the {@code redirectEnd} event, converted to the given time unit.
     *
     * @param timeUnit the time unit to convert the metrics values to
     * @return the converted time for the {@code redirectEnd} event
     */
    default long redirectEnd(TimeUnit timeUnit) {
        return getEventValue(PerformanceTimingEvent.REDIRECT_END, timeUnit);
    }

    /**
     * Returns the value that corresponds to the {@code fetchStart} event.
     *
     * @return the epoch time for the {@code fetchStart} event
     */
    default long fetchStart() {
        return fetchStart(MILLISECONDS);
    }

    /**
     * Returns the value that corresponds to the {@code fetchStart} event, converted to the given time unit.
     *
     * @param timeUnit the time unit to convert the metrics values to
     * @return the converted time for the {@code fetchStart} event
     */
    default long fetchStart(TimeUnit timeUnit) {
        return getEventValue(PerformanceTimingEvent.FETCH_START, timeUnit);
    }

    /**
     * Returns the value that corresponds to the {@code domainLookupStart} event.
     *
     * @return the epoch time for the {@code domainLookupStart} event
     */
    default long domainLookupStart() {
        return domainLookupStart(MILLISECONDS);
    }

    /**
     * Returns the value that corresponds to the {@code domainLookupStart} event, converted to the given time unit.
     *
     * @param timeUnit the time unit to convert the metrics values to
     * @return the converted time for the {@code domainLookupStart} event
     */
    default long domainLookupStart(TimeUnit timeUnit) {
        return getEventValue(PerformanceTimingEvent.DOMAIN_LOOKUP_START, timeUnit);
    }

    /**
     * Returns the value that corresponds to the {@code domainLookupEnd} event.
     *
     * @return the epoch time for the {@code domainLookupEnd} event
     */
    default long domainLookupEnd() {
        return domainLookupEnd(MILLISECONDS);
    }

    /**
     * Returns the value that corresponds to the {@code domainLookupEnd} event, converted to the given time unit.
     *
     * @param timeUnit the time unit to convert the metrics values to
     * @return the converted time for the {@code domainLookupEnd} event
     */
    default long domainLookupEnd(TimeUnit timeUnit) {
        return getEventValue(PerformanceTimingEvent.DOMAIN_LOOKUP_END, timeUnit);
    }

    /**
     * Returns the value that corresponds to the {@code connectStart} event.
     *
     * @return the epoch time for the {@code connectStart} event
     */
    default long connectStart() {
        return connectStart(MILLISECONDS);
    }

    /**
     * Returns the value that corresponds to the {@code connectStart} event, converted to the given time unit.
     *
     * @param timeUnit the time unit to convert the metrics values to
     * @return the converted time for the {@code connectStart} event
     */
    default long connectStart(TimeUnit timeUnit) {
        return getEventValue(PerformanceTimingEvent.CONNECT_START, timeUnit);
    }

    /**
     * Returns the value that corresponds to the {@code connectEnd} event.
     *
     * @return the epoch time for the {@code connectEnd} event
     */
    default long connectEnd() {
        return connectEnd(MILLISECONDS);
    }

    /**
     * Returns the value that corresponds to the {@code connectEnd} event, converted to the given time unit.
     *
     * @param timeUnit the time unit to convert the metrics values to
     * @return the converted time for the {@code connectEnd} event
     */
    default long connectEnd(TimeUnit timeUnit) {
        return getEventValue(PerformanceTimingEvent.CONNECT_END, timeUnit);
    }

    /**
     * Returns the value that corresponds to the {@code secureConnectionStart} event.
     *
     * @return the epoch time for the {@code secureConnectionStart} event
     */
    default Object secureConnectionStart() {
        return secureConnectionStart(MILLISECONDS);
    }

    /**
     * Returns the value that corresponds to the {@code secureConnectionStart} event, converted to the given time unit.
     *
     * @param timeUnit the time unit to convert the metrics values to
     * @return the converted time for the {@code secureConnectionStart} event
     */
    default Object secureConnectionStart(TimeUnit timeUnit) {
        return getEventValue(PerformanceTimingEvent.SECURE_CONNECTION_START, timeUnit);
    }

    /**
     * Returns the value that corresponds to the {@code requestStart} event.
     *
     * @return the epoch time for the {@code requestStart} event
     */
    default long requestStart() {
        return requestStart(MILLISECONDS);
    }

    /**
     * Returns the value that corresponds to the {@code requestStart} event, converted to the given time unit.
     *
     * @param timeUnit the time unit to convert the metrics values to
     * @return the converted time for the {@code requestStart} event
     */
    default long requestStart(TimeUnit timeUnit) {
        return getEventValue(PerformanceTimingEvent.REQUEST_START, timeUnit);
    }

    /**
     * Returns the value that corresponds to the {@code responseStart} event.
     *
     * @return the epoch time for the {@code responseStart} event
     */
    default long responseStart() {
        return responseStart(MILLISECONDS);
    }

    /**
     * Returns the value that corresponds to the {@code responseStart} event, converted to the given time unit.
     *
     * @param timeUnit the time unit to convert the metrics values to
     * @return the converted time for the {@code responseStart} event
     */
    default long responseStart(TimeUnit timeUnit) {
        return getEventValue(PerformanceTimingEvent.RESPONSE_START, timeUnit);
    }

    /**
     * Returns the value that corresponds to the {@code responseEnd} event.
     *
     * @return the epoch time for the {@code responseEnd} event
     */
    default long responseEnd() {
        return responseEnd(MILLISECONDS);
    }

    /**
     * Returns the value that corresponds to the {@code responseEnd} event, converted to the given time unit.
     *
     * @param timeUnit the time unit to convert the metrics values to
     * @return the converted time for the {@code responseEnd} event
     */
    default long responseEnd(TimeUnit timeUnit) {
        return getEventValue(PerformanceTimingEvent.RESPONSE_END, timeUnit);
    }

    /**
     * Returns the value that corresponds to the {@code domLoading} event.
     *
     * @return the epoch time for the {@code domLoading} event
     */
    default long domLoading() {
        return domLoading(MILLISECONDS);
    }

    /**
     * Returns the value that corresponds to the {@code domLoading} event, converted to the given time unit.
     *
     * @param timeUnit the time unit to convert the metrics values to
     * @return the converted time for the {@code domLoading} event
     */
    default long domLoading(TimeUnit timeUnit) {
        return getEventValue(PerformanceTimingEvent.DOM_LOADING, timeUnit);
    }

    /**
     * Returns the value that corresponds to the {@code domInteractive} event.
     *
     * @return the epoch time for the {@code domInteractive} event
     */
    default long domInteractive() {
        return domInteractive(MILLISECONDS);
    }

    /**
     * Returns the value that corresponds to the {@code domInteractive} event, converted to the given time unit.
     *
     * @param timeUnit the time unit to convert the metrics values to
     * @return the converted time for the {@code domInteractive} event
     */
    default long domInteractive(TimeUnit timeUnit) {
        return getEventValue(PerformanceTimingEvent.DOM_INTERACTIVE, timeUnit);
    }

    /**
     * Returns the value that corresponds to the {@code domContentLoadedEventStart} event.
     *
     * @return the epoch time for the {@code domContentLoadedEventStart} event
     */
    default long domContentLoadedEventStart() {
        return domContentLoadedEventStart(MILLISECONDS);
    }

    /**
     * Returns the value that corresponds to the {@code domContentLoadedEventStart} event,
     * converted to the given time unit.
     *
     * @param timeUnit the time unit to convert the metrics values to
     * @return the converted time for the {@code domContentLoadedEventStart} event
     */
    default long domContentLoadedEventStart(TimeUnit timeUnit) {
        return getEventValue(PerformanceTimingEvent.DOM_CONTENT_LOADED_EVENT_START, timeUnit);
    }

    /**
     * Returns the value that corresponds to the {@code domContentLoadedEventEnd} event.
     *
     * @return the epoch time for the {@code domContentLoadedEventEnd} event
     */
    default long domContentLoadedEventEnd() {
        return domContentLoadedEventEnd(MILLISECONDS);
    }

    /**
     * Returns the value that corresponds to the {@code domContentLoadedEventEnd} event,
     * converted to the given time unit.
     *
     * @param timeUnit the time unit to convert the metrics values to
     * @return the converted time for the {@code domContentLoadedEventEnd} event
     */
    default long domContentLoadedEventEnd(TimeUnit timeUnit) {
        return getEventValue(PerformanceTimingEvent.DOM_CONTENT_LOADED_EVENT_END, timeUnit);
    }

    /**
     * Returns the value that corresponds to the {@code domComplete} event.
     *
     * @return the epoch time for the {@code domComplete} event
     */
    default long domComplete() {
        return domComplete(MILLISECONDS);
    }

    /**
     * Returns the value that corresponds to the {@code domComplete} event, converted to the given time unit.
     *
     * @param timeUnit the time unit to convert the metrics values to
     * @return the converted time for the {@code domComplete} event
     */
    default long domComplete(TimeUnit timeUnit) {
        return getEventValue(PerformanceTimingEvent.DOM_COMPLETE, timeUnit);
    }

    /**
     * Returns the value that corresponds to the {@code loadEventStart} event.
     *
     * @return the epoch time for the {@code loadEventStart} event
     */
    default long loadEventStart() {
        return loadEventStart(MILLISECONDS);
    }

    /**
     * Returns the value that corresponds to the {@code loadEventStart} event, converted to the given time unit.
     *
     * @param timeUnit the time unit to convert the metrics values to
     * @return the converted time for the {@code loadEventStart} event
     */
    default long loadEventStart(TimeUnit timeUnit) {
        return getEventValue(PerformanceTimingEvent.LOAD_EVENT_START, timeUnit);
    }

    /**
     * Returns the value that corresponds to the {@code loadEventEnd} event.
     *
     * @return the epoch time for the {@code loadEventEnd} event
     */
    default long loadEventEnd() {
        return loadEventEnd(MILLISECONDS);
    }

    /**
     * Returns the value that corresponds to the {@code loadEventEnd} event, converted to the given time unit.
     *
     * @param timeUnit the time unit to convert the metrics values to
     * @return the converted time for the {@code loadEventEnd} event
     */
    default long loadEventEnd(TimeUnit timeUnit) {
        return getEventValue(PerformanceTimingEvent.LOAD_EVENT_END, timeUnit);
    }

    /**
     * Returns the value for the argument event type.
     *
     * @param event the event, never null
     * @return the event value
     */
    long getEventValue(PerformanceTimingEvent event);

    /**
     * Returns the value for the argument event type converted to the given time unit.
     *
     * @param event    the event, never null
     * @param timeUnit the time unit to convert the value to
     * @return the event value in the given time unit
     */
    default long getEventValue(PerformanceTimingEvent event, TimeUnit timeUnit) {
        return timeUnit.convert(getEventValue(event), MILLISECONDS);
    }

    /**
     * Return all timing metrics, in particular the {@code window.performance.timing} object wrapped in a
     * {@link PerformanceTimingMetrics} object.
     *
     * @return the performance timing metrics
     */
    PerformanceTimingMetrics getMetrics();
}
