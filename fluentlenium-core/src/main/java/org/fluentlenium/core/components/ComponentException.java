package org.fluentlenium.core.components;

/**
 * Thrown when something wrong occurs with a component.
 */
public class ComponentException extends RuntimeException {
    /**
     * Creates a new component exception.
     *
     * @param message exception message
     */
    public ComponentException(String message) {
        super(message);
    }

    /**
     * Creates a new component exception.
     *
     * @param message exception message
     * @param cause   exception cause
     */
    public ComponentException(String message, Throwable cause) {
        super(message, cause);
    }
}
