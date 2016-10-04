package org.fluentlenium.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DefaultConfigurationFactory implements ConfigurationFactory {

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
