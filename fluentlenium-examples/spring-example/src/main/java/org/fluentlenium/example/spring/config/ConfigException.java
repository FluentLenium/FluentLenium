package org.fluentlenium.example.spring.config;

public class ConfigException extends RuntimeException {

    public ConfigException(String message, Exception e) {
        super(message, e);
    }

}
