package org.fluentlenium.configuration;

public class SystemPropertiesConfiguration extends AbstractPropertiesConfiguration {
    @Override
    protected String getPropertyImpl(String propertyName) {
        return System.getProperty(propertyName);
    }
}
