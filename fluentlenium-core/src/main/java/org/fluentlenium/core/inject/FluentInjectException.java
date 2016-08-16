package org.fluentlenium.core.inject;

/**
 * Exception thrown when a Page can't be initialized.
 */
public class FluentInjectException extends RuntimeException {
    public FluentInjectException(String s, Throwable t) {
        super(s, t);
    }
}
