package org.fluentlenium.core.events;

public class EventAnnotationsException extends RuntimeException {
    public EventAnnotationsException() {
    }

    public EventAnnotationsException(String message) {
        super(message);
    }

    public EventAnnotationsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EventAnnotationsException(Throwable cause) {
        super(cause);
    }
}
