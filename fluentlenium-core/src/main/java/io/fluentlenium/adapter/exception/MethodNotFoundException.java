package io.fluentlenium.adapter.exception;

/**
 * Exception to be thrown when a method cannot be found.
 */
public class MethodNotFoundException extends RuntimeException {

    public MethodNotFoundException(Throwable cause) {
        super(cause);
    }
}
