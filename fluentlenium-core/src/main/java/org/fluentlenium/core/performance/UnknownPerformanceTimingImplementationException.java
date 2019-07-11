package org.fluentlenium.core.performance;

/**
 * Exception for when the browser specific PerformanceTiming API implementation is not (yet) supported by FluentLenium.
 */
public class UnknownPerformanceTimingImplementationException extends RuntimeException {

    public UnknownPerformanceTimingImplementationException(String message) {
        super(message);
    }
}
