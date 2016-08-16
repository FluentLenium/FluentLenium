package org.fluentlenium.configuration;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DefaultConfigurationFactory implements ConfigurationFactory {

    @Override
    public Configuration newConfiguration(Class<?> containerClass) {
        Properties properties = new Properties();

        InputStream configurationFile = getClass().getResourceAsStream("/fluentlenium.properties");
        if (configurationFile != null) {
            try {
                properties.load(configurationFile);
            } catch (IOException e) {
                IOUtils.closeQuietly(configurationFile);
            }
        }

        ProgrammaticConfiguration programmaticConfiguration = new ProgrammaticConfiguration();
        Configuration configuration = new ComposedConfiguration(
                programmaticConfiguration,
                programmaticConfiguration,
                new SystemPropertiesConfiguration(),
                new EnvironmentVariableConfiguration(),
                new PropertiesConfiguration(properties),
                new AnnotationsConfiguration(containerClass),
                new DefaultConfiguration()
        );

        return configuration;
    }
}
