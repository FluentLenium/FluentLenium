package org.fluentlenium.configuration;

/**
 * Creates new configuration from a container class.
 */
public interface ConfigurationFactory {
    /**
     * Creates a new configuration from a container class.
     *
     * @param containerClass Container class that may be used to read annotations.
     * @return a new configuration instance.
     */
    Configuration newConfiguration(Class<?> containerClass);
}
