package org.fluentlenium.core.page;

/**
 * Exception thrown when a Page can't be initialized.
 */
public class PageInitializerException extends RuntimeException {
    public PageInitializerException(String s, Throwable t) {
        super(s, t);
    }
}
