package org.fluentlenium.core.inject;

/**
 * Exception thrown when a Page can't be initialized.
 */
public class FluentInjectException extends RuntimeException {
    public FluentInjectException(final String message) {
        super(message);
    }

    public FluentInjectException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
