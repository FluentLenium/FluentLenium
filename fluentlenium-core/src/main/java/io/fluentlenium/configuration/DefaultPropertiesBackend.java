package io.fluentlenium.configuration;

import java.util.Properties;

/**
 * Properties backend based on java {@link Properties} object.
 */
public class DefaultPropertiesBackend implements PropertiesBackend {
    private final Properties properties;

    /**
     * Creates a new configuration based on properties object.
     *
     * @param properties properties object
     */
    public DefaultPropertiesBackend(Properties properties) {
        this.properties = properties;
    }

    @Override
    public String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }
}
