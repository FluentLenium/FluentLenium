package io.fluentlenium.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Creates new configuration from a container class.
 */
public class DefaultConfigurationFactory implements ConfigurationFactory {

    /**
     * Get inputstream containing fluentlenium properties.
     *
     * @return propertiers input stream
     */
    protected InputStream getPropertiesInputStream() {
        return getClass().getResourceAsStream("/fluentlenium.properties");
    }

    @Override
    public Configuration newConfiguration(Class<?> containerClass, ConfigurationProperties configurationDefaults) {
        Properties properties = new Properties();

        if (configurationDefaults == null) {
            configurationDefaults = new ConfigurationDefaults();
        }

        InputStream configurationFile = getPropertiesInputStream();
        if (configurationFile != null) {
            try {
                properties.load(configurationFile);
            } catch (IOException e) {
                throw new ConfigurationException("Can't read fluentlenium.properties. " + e);
            }
        }

        ProgrammaticConfiguration programmaticConfiguration = new ProgrammaticConfiguration();
        Configuration configuration = new ComposedConfiguration(programmaticConfiguration, programmaticConfiguration,
                new PropertiesBackendConfiguration(new SystemPropertiesBackend()),
                new PropertiesBackendConfiguration(new EnvironmentVariablesBackend()),
                new AnnotationConfiguration(containerClass),
                new PropertiesBackendConfiguration(new DefaultPropertiesBackend(properties), "",
                        PropertiesBackendConfiguration.PROPERTIES_PREFIX), configurationDefaults);

        return configuration;
    }
}
