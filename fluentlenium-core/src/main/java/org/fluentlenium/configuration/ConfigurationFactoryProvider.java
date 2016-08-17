package org.fluentlenium.configuration;

import org.fluentlenium.utils.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;

public abstract class ConfigurationFactoryProvider {
    private static ConfigurationFactory bootstrapFactory = new DefaultConfigurationFactory();

    public static ConfigurationFactory getConfigurationFactory(Class<?> container) {
        ConfigurationProperties configuration = bootstrapFactory.newConfiguration(container);
        Class<? extends ConfigurationFactory> configurationFactoryClass = configuration.getConfigurationFactory();
        if (configurationFactoryClass != null) {
            try {
                return ReflectionUtils.newInstance(configurationFactoryClass);
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                throw new ConfigurationException("Can't initialize ConfigurationFactory " + configurationFactoryClass.getName(), e);
            }
        }
        return bootstrapFactory;
    }

    public static Configuration newConfiguration(Class<?> container) {
        return getConfigurationFactory(container).newConfiguration(container);
    }
}
