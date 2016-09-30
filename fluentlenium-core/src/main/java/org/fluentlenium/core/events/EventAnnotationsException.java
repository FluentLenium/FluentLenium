package org.fluentlenium.core.events;

/**
 * Thrown when something wrong occurs with event annotations.
 */
public class EventAnnotationsException extends RuntimeException {
    public EventAnnotationsException() {
        // Default constructor
    }

    /**
     * Creates a new event annotations exception.
     *
     * @param message exception message
     */
    public EventAnnotationsException(final String message) {
        super(message);
    }

    /**
     * Creates a new event annotations exception.
     *
     * @param message exception message
     * @param cause   exception cause
     */
    public EventAnnotationsException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
