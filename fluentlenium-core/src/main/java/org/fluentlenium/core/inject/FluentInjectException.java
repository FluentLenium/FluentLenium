package org.fluentlenium.core.inject;

/**
 * Exception thrown when a Page can't be initialized.
 */
public class FluentInjectException extends RuntimeException {
    /**
     * Creates a new fluent inject exception
     *
     * @param message exception message
     */
    public FluentInjectException(String message) {
        super(message);
    }

    /**
     * Creates a new fluent inject exception
     *
     * @param message exception message
     * @param cause   exception cause
     */
    public FluentInjectException(String message, Throwable cause) {
        super(message, cause);
    }
}
