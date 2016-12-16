package org.fluentlenium.configuration;

/**
 * Exception thrown when something wrong occurs because of the configuration of FluentLenium.
 */
public class ConfigurationException extends RuntimeException {
    /**
     * Creates a configuration exception.
     *
     * @param message the exception message
     */
    public ConfigurationException(String message) {
        super(message);
    }

    /**
     * Creates a configuration exception.
     *
     * @param message the exception message
     * @param cause   the exception cause
     */
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
