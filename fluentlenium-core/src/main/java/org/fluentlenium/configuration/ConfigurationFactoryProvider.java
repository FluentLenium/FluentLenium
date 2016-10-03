package org.fluentlenium.configuration;

import org.fluentlenium.utils.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;

public final class ConfigurationFactoryProvider {
    private ConfigurationFactoryProvider() {
        // Utility class
    }

    private static ConfigurationFactory bootstrapFactory = new DefaultConfigurationFactory();

    public static ConfigurationFactory getConfigurationFactory(Class<?> container) {
        ConfigurationProperties configuration = bootstrapFactory.newConfiguration(container, new ConfigurationDefaults());

        Class<? extends ConfigurationFactory> configurationFactoryClass = configuration.getConfigurationFactory();

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

    public static Configuration newConfiguration(Class<?> container) {
        ConfigurationFactory configurationFactory = getConfigurationFactory(container);
        Configuration configuration = configurationFactory.newConfiguration(container, new ConfigurationDefaults());

        if (configuration.getConfigurationDefaults() != null
                && configuration.getConfigurationDefaults() != ConfigurationDefaults.class) {

            ConfigurationProperties configurationDefaults;

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
