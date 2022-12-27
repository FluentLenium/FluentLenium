package io.fluentlenium.core.wait;

/**
 * Exception type to be thrown when the wait performed by FluentLenium is interrupted.
 */
public class WaitInterruptedException extends RuntimeException {

    public WaitInterruptedException(String message, Throwable cause) {
        super(message, cause);
    }
}
