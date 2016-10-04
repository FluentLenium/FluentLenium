package org.fluentlenium.configuration;

/**
 * {@link ConfigurationProperties} based on Java System Properties.
 *
 * @see ConfigurationProperties
 */
public class SystemPropertiesConfiguration extends AbstractPropertiesConfiguration {
    @Override
    protected String getPropertyImpl(final String propertyName) {
        return System.getProperty(propertyName);
    }
}
