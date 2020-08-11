package org.fluentlenium.core.performance;

/**
 * Exception for when the browser specific PerformanceTiming API implementation is not (yet) supported by FluentLenium.
 *
 * @see PerformanceTimingMetricsFactory
 */
class UnknownPerformanceTimingImplementationException extends RuntimeException {

    UnknownPerformanceTimingImplementationException(String message) {
        super(message);
    }
}
