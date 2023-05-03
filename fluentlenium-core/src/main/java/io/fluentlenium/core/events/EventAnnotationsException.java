package io.fluentlenium.core.events;

/**
 * Thrown when something wrong occurs with event annotations.
 */
public class EventAnnotationsException extends RuntimeException {
    /**
     * Creates a new event annotations exception.
     *
     * @param message exception message
     * @param cause   exception cause
     */
    public EventAnnotationsException(String message, Throwable cause) {
        super(message, cause);
    }
}
