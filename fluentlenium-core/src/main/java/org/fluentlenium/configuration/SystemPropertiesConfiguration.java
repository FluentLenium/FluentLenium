package org.fluentlenium.configuration;

/**
 * {@link ConfigurationProperties} based on Java System Properties.
 *
 * @see ConfigurationProperties
 */
public class SystemPropertiesConfiguration extends AbstractPropertiesConfiguration {
    @Override
    protected String getPropertyImpl(String propertyName) {
        return System.getProperty(propertyName);
    }
}
