package org.fluentlenium.configuration;

/**
 * Exception thrown when something wrong occurs because of the configuration of FluentLenium.
 */
public class ConfigurationException extends RuntimeException {
    public ConfigurationException(String message) {
        super(message);
    }

    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
