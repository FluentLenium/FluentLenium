package org.fluentlenium.configuration;

public abstract class ConfigurationFactoryProvider {
    private static ConfigurationFactory bootstrapFactory = new DefaultConfigurationFactory();

    public static ConfigurationFactory getConfigurationFactory(Class<?> container) {
        ConfigurationRead configuration = bootstrapFactory.newConfiguration(container);
        Class<? extends ConfigurationFactory> configurationFactoryClass = configuration.getConfigurationFactory();
        if (configurationFactoryClass != null) {
            try {
                return configurationFactoryClass.newInstance();
            } catch (InstantiationException e) {
                throw new IllegalStateException("Can't initialize ConfigurationFactory", e);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Can't initialize ConfigurationFactory", e);
            }
        }
        return bootstrapFactory;
    }

    public static Configuration newConfiguration(Class<?> container) {
        return getConfigurationFactory(container).newConfiguration(container);
    }
}
