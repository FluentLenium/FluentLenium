package org.fluentlenium.core.performance;

import java.util.concurrent.TimeUnit;

/**
 * Wraps the performance timing metrics returned by the {@code window.performance.timing} Javascript attribute
 * from the browser, and provides methods to access each value in it.
 * <p>
 * Though the default implementation in FluentLenium returns relative values, this interface is flexible enough
 * so that custom implementations may be created.
 *
 * @param <T> an actual implementation of this interface
 */
public interface PerformanceTimingMetrics<T extends PerformanceTimingMetrics> {

    long getUnloadEventStart();

    long getUnloadEventEnd();

    long getRedirectStart();

    long getRedirectEnd();

    long getNavigationStart();

    long getFetchStart();

    long getDomainLookupStart();

    long getDomainLookupEnd();

    long getConnectStart();

    long getConnectEnd();

    /**
     * According to the official documentation of the
     * <a href="https://www.w3.org/TR/navigation-timing/#dom-performancetiming-secureconnectstart">
     * secureConnectionStart attribute</a> this attribute is optional and may be set as {@code undefined}.
     * <p>
     * It is up to the user of this method to check whether it is present and convert it accordingly.
     *
     * @return the metrics value
     */
    Object getSecureConnectionStart();

    long getRequestStart();

    long getResponseStart();

    long getResponseEnd();

    long getDomLoading();

    long getDomInteractive();

    long getDomContentLoadedEventStart();

    long getDomContentLoadedEventEnd();

    long getDomComplete();

    long getLoadEventStart();

    long getLoadEventEnd();

    /**
     * Creates a new metrics object instance that returns the metrics values in the given {@link TimeUnit}.
     * <p>
     * When implementing this method take into account that there may be metric values that don't always return
     * a {@code long} value but may be undefined, or something else, like the {@code secureConnectionStart} attribute.
     *
     * @param targetTimeUnit the time unit to return the metrics in
     * @return the metrics in the given time unit
     */
    T in(TimeUnit targetTimeUnit);
}
