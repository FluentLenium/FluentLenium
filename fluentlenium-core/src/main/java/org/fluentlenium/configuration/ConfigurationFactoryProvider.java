package org.fluentlenium.configuration;

import org.fluentlenium.utils.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;

public final class ConfigurationFactoryProvider {
    private static ConfigurationFactory bootstrapFactory = new DefaultConfigurationFactory();

    private ConfigurationFactoryProvider() {
        // Utility class
    }

    public static ConfigurationFactory getConfigurationFactory(final Class<?> container) {
        final ConfigurationProperties configuration = bootstrapFactory.newConfiguration(container, new ConfigurationDefaults());

        final Class<? extends ConfigurationFactory> configurationFactoryClass = configuration.getConfigurationFactory();

        if (configurationFactoryClass != null) {
            try {
                return ReflectionUtils.newInstance(configurationFactoryClass);
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                throw new ConfigurationException("Can't initialize ConfigurationFactory " + configurationFactoryClass.getName(),
                        e);
            }
        }
        return bootstrapFactory;
    }

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
