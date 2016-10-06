package org.fluentlenium.configuration;

/**
 * Base configuration class supporting setting of the global configuration.
 */
public class BaseConfiguration {
    private ConfigurationProperties globalConfiguration;

    /**
     * Set the global configuration.
     *
     * @param configuration
     */
    /* default */ void setGlobalConfiguration(final ConfigurationProperties configuration) {
        this.globalConfiguration = configuration;
    }

    /**
     * Get the global configuration
     *
     * @return global configuration
     */
    /* default */ ConfigurationProperties getGlobalConfiguration() {
        return globalConfiguration;
    }
}
