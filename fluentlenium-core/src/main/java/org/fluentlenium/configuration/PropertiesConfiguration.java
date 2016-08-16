package org.fluentlenium.configuration;

import java.util.Properties;

public class PropertiesConfiguration extends AbstractPropertiesConfiguration {
    private Properties properties;

    public PropertiesConfiguration(Properties properties) {
        this.properties = properties;
    }

    @Override
    protected String getPropertyImpl(String propertyName) {
        return properties.getProperty(propertyName);
    }
}
