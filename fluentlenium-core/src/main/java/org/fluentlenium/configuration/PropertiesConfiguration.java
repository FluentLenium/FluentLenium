package org.fluentlenium.configuration;

import java.util.Properties;

/**
 * {@link ConfigurationProperties} based on {@link Properties} object.
 *
 * @see ConfigurationProperties
 */
public class PropertiesConfiguration extends AbstractPropertiesConfiguration {
    private Properties properties;

    public PropertiesConfiguration(Properties properties) {
        super("", PROPERTIES_PREFIX);
        this.properties = properties;
    }

    @Override
    protected String getPropertyImpl(String propertyName) {
        return properties.getProperty(propertyName);
    }
}
