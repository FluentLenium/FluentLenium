package org.fluentlenium.configuration;

public class EnvironmentVariablesConfiguration extends AbstractPropertiesConfiguration {
    @Override
    protected String getPropertyImpl(String propertyName) {
        return System.getenv(propertyName);
    }
}
