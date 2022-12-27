package io.fluentlenium.configuration;

/**
 * Properties backend based on java system properties.
 */
public class SystemPropertiesBackend implements PropertiesBackend {
    @Override
    public String getProperty(String propertyName) {
        return System.getProperty(propertyName);
    }
}
