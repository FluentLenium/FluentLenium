package org.fluentlenium.configuration;

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
    public Configuration newConfiguration(final Class<?> containerClass, ConfigurationProperties configurationDefaults) {
        final Properties properties = new Properties();

        if (configurationDefaults == null) {
            configurationDefaults = new ConfigurationDefaults();
        }

        final InputStream configurationFile = getPropertiesInputStream();
        if (configurationFile != null) {
            try {
                properties.load(configurationFile);
            } catch (final IOException e) {
                throw new ConfigurationException("Can't read fluentlenium.properties. " + e);
            }
        }

        final ProgrammaticConfiguration programmaticConfiguration = new ProgrammaticConfiguration();
        final Configuration configuration = new ComposedConfiguration(programmaticConfiguration, programmaticConfiguration,
                new SystemPropertiesConfiguration(), new EnvironmentVariablesConfiguration(),
                new AnnotationConfiguration(containerClass), new PropertiesConfiguration(properties), configurationDefaults);

        return configuration;
    }
}
