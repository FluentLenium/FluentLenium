package org.fluentlenium.core.inject;

/**
 * Exception thrown when a Fluent WebElement can't be injected.
 */
public class WebElementInjectException extends FluentInjectException {

    public WebElementInjectException(String s, Throwable t) {
        super(s, t);
    }
}
