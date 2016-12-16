package org.fluentlenium.example.spring.config;

/**
 * Thrown when something wrong occurs with the configuration
 */
public class ConfigException extends RuntimeException {

    /**
     * Creates configuration exception
     *
     * @param message message
     * @param cause   cause
     */
    public ConfigException(String message, Exception cause) {
        super(message, cause);
    }

}
