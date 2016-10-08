package org.fluentlenium.configuration;

import java.util.Properties;

/**
 * {@link ConfigurationProperties} based on {@link Properties} object.
 *
 * @see ConfigurationProperties
 */
public class PropertiesConfiguration extends AbstractPropertiesConfiguration {
    private final Properties properties;

    /**
     * Creates a new configuration based on properties object.
     *
     * @param properties properties object
     */
    public PropertiesConfiguration(final Properties properties) {
        super("", PROPERTIES_PREFIX);
        this.properties = properties;
    }

    @Override
    protected String getPropertyImpl(final String propertyName) {
        return properties.getProperty(propertyName);
    }
}
