package org.fluentlenium.configuration;

public class EnvironmentVariableConfiguration extends AbstractPropertiesConfiguration {
    @Override
    protected String getPropertyImpl(String propertyName) {
        return System.getenv(propertyName);
    }
}
