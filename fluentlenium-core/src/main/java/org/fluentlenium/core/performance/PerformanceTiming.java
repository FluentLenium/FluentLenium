package org.fluentlenium.core.performance;

/**
 * Interface representing the PerformanceTiming interface defined by W3C under the
 * <a href="https://www.w3.org/TR/navigation-timing/#sec-navigation-timing-interface">Performance Timing interface.</a>
 * <p>
 * From the browser they can be retrieved by querying the value of the following JavaScript attribute:
 * {@code window.performance.timing.}.
 */
public interface PerformanceTiming {

    /**
     * Returns the value that corresponds to the {@code navigationStart} event.
     * <p>
     * For details see the official documentation of the
     * <a href="https://www.w3.org/TR/navigation-timing/#dom-performancetiming-navigationstart">navigationStart</a>
     * attribute.
     */
    default long navigationStart() {
        return getEventValue(PerformanceTimingEvent.NAVIGATION_START);
    }

    /**
     * Returns the value that corresponds to the {@code unloadEventStart} event.
     * <p>
     * For details see the official documentation of the
     * <a href="https://www.w3.org/TR/navigation-timing/#dom-performancetiming-unloadeventstart">unloadEventStart</a>
     * attribute.
     *
     * @return the epoch time for the {@code unloadEventStart} event
     */
    default long unloadEventStart() {
        return getEventValue(PerformanceTimingEvent.UNLOAD_EVENT_START);
    }

    /**
     * Returns the value that corresponds to the {@code unloadEventEnd} event.
     * <p>
     * For details see the official documentation of the
     * <a href="https://www.w3.org/TR/navigation-timing/#dom-performancetiming-unloadeventend">unloadEventEnd</a>
     * attribute.
     *
     * @return the epoch time for the {@code unloadEventEnd} event
     */
    default long unloadEventEnd() {
        return getEventValue(PerformanceTimingEvent.UNLOAD_EVENT_END);
    }

    /**
     * Returns the value that corresponds to the {@code redirectStart} event.
     * <p>
     * For details see the official documentation of the
     * <a href="https://www.w3.org/TR/navigation-timing/#dom-performancetiming-redirectstart">redirectStart</a>
     * attribute.
     *
     * @return the epoch time for the {@code redirectStart} event
     */
    default long redirectStart() {
        return getEventValue(PerformanceTimingEvent.REDIRECT_START);
    }

    /**
     * Returns the value that corresponds to the {@code redirectEnd} event.
     * <p>
     * For details see the official documentation of the
     * <a href="https://www.w3.org/TR/navigation-timing/#dom-performancetiming-redirectend">redirectEnd</a>
     * attribute.
     *
     * @return the epoch time for the {@code redirectEnd} event
     */
    default long redirectEnd() {
        return getEventValue(PerformanceTimingEvent.REDIRECT_END);
    }

    /**
     * Returns the value that corresponds to the {@code fetchStart} event.
     * <p>
     * For details see the official documentation of the
     * <a href="https://www.w3.org/TR/navigation-timing/#dom-performancetiming-fetchstart">fetchStart</a>
     * attribute.
     *
     * @return the epoch time for the {@code fetchStart} event
     */
    default long fetchStart() {
        return getEventValue(PerformanceTimingEvent.FETCH_START);
    }

    /**
     * Returns the value that corresponds to the {@code domainLookupStart} event.
     * <p>
     * For details see the official documentation of the
     * <a href="https://www.w3.org/TR/navigation-timing/#dom-performancetiming-domainlookupstart">domainLookupStart</a>
     * attribute.
     *
     * @return the epoch time for the {@code domainLookupStart} event
     */
    default long domainLookupStart() {
        return getEventValue(PerformanceTimingEvent.DOMAIN_LOOKUP_START);
    }

    /**
     * Returns the value that corresponds to the {@code domainLookupEnd} event.
     * <p>
     * For details see the official documentation of the
     * <a href="https://www.w3.org/TR/navigation-timing/#dom-performancetiming-domainlookupend">domainLookupEnd</a>
     * attribute.
     *
     * @return the epoch time for the {@code domainLookupEnd} event
     */
    default long domainLookupEnd() {
        return getEventValue(PerformanceTimingEvent.DOMAIN_LOOKUP_END);
    }

    /**
     * Returns the value that corresponds to the {@code connectStart} event.
     * <p>
     * For details see the official documentation of the
     * <a href="https://www.w3.org/TR/navigation-timing/#dom-performancetiming-connectstart">connectStart</a>
     * attribute.
     *
     * @return the epoch time for the {@code connectStart} event
     */
    default long connectStart() {
        return getEventValue(PerformanceTimingEvent.CONNECT_START);
    }

    /**
     * Returns the value that corresponds to the {@code connectEnd} event.
     * <p>
     * For details see the official documentation of the
     * <a href="https://www.w3.org/TR/navigation-timing/#dom-performancetiming-connectend">connectEnd</a>
     * attribute.
     *
     * @return the epoch time for the {@code connectEnd} event
     */
    default long connectEnd() {
        return getEventValue(PerformanceTimingEvent.CONNECT_END);
    }

    /**
     * Returns the value that corresponds to the {@code secureConnectionStart} event.
     * <p>
     * For details see the official documentation of the
     * <a href="https://www.w3.org/TR/navigation-timing/#dom-performancetiming-secureconnectstart">secureConnectionStart</a>
     * attribute.
     *
     * @return the epoch time for the {@code secureConnectionStart} event
     */
    default long secureConnectionStart() {
        return getEventValue(PerformanceTimingEvent.SECURE_CONNECTION_START);
    }

    /**
     * Returns the value that corresponds to the {@code requestStart} event.
     * <p>
     * For details see the official documentation of the
     * <a href="https://www.w3.org/TR/navigation-timing/#dom-performancetiming-requeststart">requestStart</a>
     * attribute.
     *
     * @return the epoch time for the {@code requestStart} event
     */
    default long requestStart() {
        return getEventValue(PerformanceTimingEvent.REQUEST_START);
    }

    /**
     * Returns the value that corresponds to the {@code responseStart} event.
     * <p>
     * For details see the official documentation of the
     * <a href="https://www.w3.org/TR/navigation-timing/#dom-performancetiming-responsestart">responseStart</a>
     * attribute.
     *
     * @return the epoch time for the {@code responseStart} event
     */
    default long responseStart() {
        return getEventValue(PerformanceTimingEvent.RESPONSE_START);
    }

    /**
     * Returns the value that corresponds to the {@code responseEnd} event.
     * <p>
     * For details see the official documentation of the
     * <a href="https://www.w3.org/TR/navigation-timing/#dom-performancetiming-responseend">responseEnd</a>
     * attribute.
     *
     * @return the epoch time for the {@code responseEnd} event
     */
    default long responseEnd() {
        return getEventValue(PerformanceTimingEvent.RESPONSE_END);
    }

    /**
     * Returns the value that corresponds to the {@code domLoading} event.
     * <p>
     * For details see the official documentation of the
     * <a href="https://www.w3.org/TR/navigation-timing/#dom-performancetiming-domloading">domLoading</a>
     * attribute.
     *
     * @return the epoch time for the {@code domLoading} event
     */
    default long domLoading() {
        return getEventValue(PerformanceTimingEvent.DOM_LOADING);
    }

    /**
     * Returns the value that corresponds to the {@code domInteractive} event.
     * <p>
     * For details see the official documentation of the
     * <a href="https://www.w3.org/TR/navigation-timing/#dom-performancetiming-dominteractive">domInteractive</a>
     * attribute.
     *
     * @return the epoch time for the {@code domInteractive} event
     */
    default long domInteractive() {
        return getEventValue(PerformanceTimingEvent.DOM_INTERACTIVE);
    }

    /**
     * Returns the value that corresponds to the {@code domContentLoadedEventStart} event.
     * <p>
     * For details see the official documentation of the
     * <a href="https://www.w3.org/TR/navigation-timing/#dom-performancetiming-domcontenteventstart">domContentLoadedEventStart</a>
     * attribute.
     *
     * @return the epoch time for the {@code domContentLoadedEventStart} event
     */
    default long domContentLoadedEventStart() {
        return getEventValue(PerformanceTimingEvent.DOM_CONTENT_LOADED_EVENT_START);
    }

    /**
     * Returns the value that corresponds to the {@code domContentLoadedEventEnd} event.
     * <p>
     * For details see the official documentation of the
     * <a href="https://www.w3.org/TR/navigation-timing/#dom-performancetiming-domcontenteventend">domContentLoadedEventEnd</a>
     * attribute.
     *
     * @return the epoch time for the {@code domContentLoadedEventEnd} event
     */
    default long domContentLoadedEventEnd() {
        return getEventValue(PerformanceTimingEvent.DOM_CONTENT_LOADED_EVENT_END);
    }

    /**
     * Returns the value that corresponds to the {@code domComplete} event.
     * <p>
     * For details see the official documentation of the
     * <a href="https://www.w3.org/TR/navigation-timing/#dom-performancetiming-domcomplete">domComplete</a>
     * attribute.
     *
     * @return the epoch time for the {@code domComplete} event
     */
    default long domComplete() {
        return getEventValue(PerformanceTimingEvent.DOM_COMPLETE);
    }

    /**
     * Returns the value that corresponds to the {@code loadEventStart} event.
     * <p>
     * For details see the official documentation of the
     * <a href="https://www.w3.org/TR/navigation-timing/#dom-performancetiming-loadstart">loadEventStart</a>
     * attribute.
     *
     * @return the epoch time for the {@code loadEventStart} event
     */
    default long loadEventStart() {
        return getEventValue(PerformanceTimingEvent.LOAD_EVENT_START);
    }

    /**
     * Returns the value that corresponds to the {@code loadEventEnd} event.
     * <p>
     * For details see the official documentation of the
     * <a href="https://www.w3.org/TR/navigation-timing/#dom-performancetiming-loadend">loadEventEnd</a>
     * attribute.
     *
     * @return the epoch time for the {@code loadEventEnd} event
     */
    default long loadEventEnd() {
        return getEventValue(PerformanceTimingEvent.LOAD_EVENT_END);
    }

    long getEventValue(PerformanceTimingEvent event);
}
