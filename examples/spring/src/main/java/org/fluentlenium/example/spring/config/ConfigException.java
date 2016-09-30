package org.fluentlenium.example.spring.config;

/**
 * Thrown when something wrong occurs with the configuration
 */
public class ConfigException extends RuntimeException {

    /**
     * Creates configuration exception
     *
     * @param message
     * @param cause
     */
    public ConfigException(final String message, final Exception cause) {
        super(message, cause);
    }

}
