package org.fluentlenium.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DefaultConfigurationFactory implements ConfigurationFactory {

    protected InputStream getPropertiesInputStream() {
        return getClass().getResourceAsStream("/fluentlenium.properties");
    }

    @Override
    public Configuration newConfiguration(Class<?> containerClass) {
        Properties properties = new Properties();

        InputStream configurationFile = getPropertiesInputStream();
        if (configurationFile != null) {
            try {
                properties.load(configurationFile);
            } catch (IOException e) {
                throw new ConfigurationException("Can't read fluentlenium.properties. " + e);
            }
        }

        ProgrammaticConfiguration programmaticConfiguration = new ProgrammaticConfiguration();
        Configuration configuration = new ComposedConfiguration(
                programmaticConfiguration,
                programmaticConfiguration,
                new SystemPropertiesConfiguration(),
                new EnvironmentVariablesConfiguration(),
                new AnnotationConfiguration(containerClass),
                new PropertiesConfiguration(properties),
                new DefaultConfiguration()
        );

        return configuration;
    }
}
