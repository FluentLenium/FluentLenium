package org.fluentlenium.core.performance;

/**
 * Provides event types according to the
 * <a href="https://www.w3.org/TR/navigation-timing/#sec-navigation-timing-interface">W3C Performance Timing interface.</a>
 * documentation.
 */
public enum PerformanceTimingEvent {

    NAVIGATION_START("navigationStart"),
    UNLOAD_EVENT_START("unloadEventStart"),
    UNLOAD_EVENT_END("unloadEventEnd"),
    REDIRECT_START("redirectStart"),
    REDIRECT_END("redirectEnd"),
    FETCH_START("fetchStart"),
    DOMAIN_LOOKUP_START("domainLookupStart"),
    DOMAIN_LOOKUP_END("domainLookupEnd"),
    CONNECT_START("connectStart"),
    CONNECT_END("connectEnd"),
    SECURE_CONNECTION_START("secureConnectionStart"),
    REQUEST_START("requestStart"),
    RESPONSE_START("responseStart"),
    RESPONSE_END("responseEnd"),
    DOM_LOADING("domLoading"),
    DOM_INTERACTIVE("domInteractive"),
    DOM_CONTENT_LOADED_EVENT_START("domContentLoadedEventStart"),
    DOM_CONTENT_LOADED_EVENT_END("domContentLoadedEventEnd"),
    DOM_COMPLETE("domComplete"),
    LOAD_EVENT_START("loadEventStart"),
    LOAD_EVENT_END("loadEventEnd");

    private final String event;

    PerformanceTimingEvent(String event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return this.event;
    }
}
