package org.fluentlenium.configuration;

/**
 * Properties backend based on environment variables.
 */
public class EnvironmentVariablesBackend implements PropertiesBackend {
    @Override
    public String getProperty(final String propertyName) {
        return System.getenv(propertyName);
    }
}
