package org.fluentlenium.configuration;

import org.fluentlenium.utils.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Provider of configuration factory.
 */
public final class ConfigurationFactoryProvider {
    private static final ConfigurationFactory BOOTSTRAP_FACTORY = new DefaultConfigurationFactory();

    private ConfigurationFactoryProvider() {
        // Utility class
    }

    /**
     * Provides the configuration factory for a given container, base on configuration of this configuration.
     *
     * @param container container class
     * @return configuration factory
     */
    public static ConfigurationFactory getConfigurationFactory(final Class<?> container) {
        final ConfigurationProperties configuration = BOOTSTRAP_FACTORY.newConfiguration(container, new ConfigurationDefaults());

        final Class<? extends ConfigurationFactory> configurationFactoryClass = configuration.getConfigurationFactory();

        if (configurationFactoryClass != null) {
            try {
                return ReflectionUtils.newInstance(configurationFactoryClass);
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                throw new ConfigurationException("Can't initialize ConfigurationFactory " + configurationFactoryClass.getName(),
                        e);
            }
        }
        return BOOTSTRAP_FACTORY;
    }

    /**
     * Creates a new configuration for a given container, using configuration factory from this provider.
     *
     * @param container container class
     * @return configuration
     */
    public static Configuration newConfiguration(final Class<?> container) {
        final ConfigurationFactory configurationFactory = getConfigurationFactory(container);
        Configuration configuration = configurationFactory.newConfiguration(container, new ConfigurationDefaults());

        if (configuration.getConfigurationDefaults() != null
                && configuration.getConfigurationDefaults() != ConfigurationDefaults.class) {

            final ConfigurationProperties configurationDefaults;

            try {
                configurationDefaults = ReflectionUtils.newInstance(configuration.getConfigurationDefaults());
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                throw new ConfigurationException(
                        "Can't initialize ConfigurationDefaults:" + configuration.getConfigurationDefaults(), e);
            }

            configuration = configurationFactory.newConfiguration(container, configurationDefaults);
        }
        return configuration;

    }
}
