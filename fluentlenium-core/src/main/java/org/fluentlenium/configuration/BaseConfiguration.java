package org.fluentlenium.configuration;

/**
 * Base configuration class supporting setting of the global configuration.
 */
public class BaseConfiguration {
    private ConfigurationProperties globalConfiguration;

    /**
     * Set the global configuration.
     *
     * @param configuration global configuration to retrieve values defined in other configuration sources
     */
    /* default */ void setGlobalConfiguration(ConfigurationProperties configuration) {
        globalConfiguration = configuration;
    }

    /**
     * Get the global configuration
     *
     * @return global configuration to retrieve values defined in other configuration sources
     */
    /* default */ ConfigurationProperties getGlobalConfiguration() {
        return globalConfiguration;
    }
}
