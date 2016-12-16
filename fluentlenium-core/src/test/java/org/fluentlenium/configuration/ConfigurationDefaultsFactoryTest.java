package org.fluentlenium.configuration;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationDefaultsFactoryTest {

    private final Properties environmentVariables = new Properties();
    private final Properties systemProperties = new Properties();

    @FluentConfiguration(pageLoadTimeout = 2000L)
    public static class AnnotatedContainer {

    }

    @Before
    public void before() {
        environmentVariables.clear();
        systemProperties.clear();
    }

    public void mockEnvironmentVariable(String property, String value) {
        environmentVariables.put(property, value);
    }

    public void mockSystemProperty(String property, String value) {
        systemProperties.put(property, value);
    }

    @Test
    public void testFactoryWithAnnotation() {
        DefaultConfigurationFactory factory = new DefaultConfigurationFactory() {
            @Override
            protected InputStream getPropertiesInputStream() {
                return IOUtils.toInputStream("pageLoadTimeout=5000", Charset.forName("UTF-8"));
            }
        };

        Configuration configuration = factory.newConfiguration(AnnotatedContainer.class, new ConfigurationDefaults());
        setupConfiguration((ComposedConfiguration) configuration);

        // Annotation has higher priority than configuration file, so it should be 2000L and not 5000L.
        assertThat(configuration.getPageLoadTimeout()).isEqualTo(2000L);

        mockEnvironmentVariable("fluentlenium.pageLoadTimeout", "1000");

        assertThat(configuration.getPageLoadTimeout()).isEqualTo(1000L);

        mockSystemProperty("fluentlenium.pageLoadTimeout", "500");

        assertThat(configuration.getPageLoadTimeout()).isEqualTo(500L);

        configuration.setPageLoadTimeout(250L);

        assertThat(configuration.getPageLoadTimeout()).isEqualTo(250L);
    }

    private void setupConfiguration(ComposedConfiguration composedConfiguration) {
        for (ConfigurationProperties configuration : composedConfiguration.getConfigurations()) {
            if (configuration instanceof PropertiesBackendConfiguration) {
                PropertiesBackendConfiguration readerConfiguration = (PropertiesBackendConfiguration) configuration;
                if (readerConfiguration.getPropertiesBackend() instanceof EnvironmentVariablesBackend) {
                    readerConfiguration.setPropertiesBackend(new DefaultPropertiesBackend(environmentVariables));
                } else if (readerConfiguration.getPropertiesBackend() instanceof SystemPropertiesBackend) {
                    readerConfiguration.setPropertiesBackend(new DefaultPropertiesBackend(systemProperties));
                }
            }
        }
    }

    @Test
    public void testFactoryNoAnnotation() {
        DefaultConfigurationFactory factory = new DefaultConfigurationFactory() {
            @Override
            protected InputStream getPropertiesInputStream() {
                return IOUtils.toInputStream("fluentlenium.pageLoadTimeout=5000\nscriptTimeout=1000", Charset.forName("UTF-8"));
            }
        };

        Configuration configuration = factory.newConfiguration(null, null);
        setupConfiguration((ComposedConfiguration) configuration);

        assertThat(configuration.getPageLoadTimeout()).isEqualTo(5000L);
        assertThat(configuration.getScriptTimeout()).isEqualTo(1000L);

        mockEnvironmentVariable("fluentlenium.pageLoadTimeout", "1000");

        assertThat(configuration.getPageLoadTimeout()).isEqualTo(1000L);

        mockSystemProperty("fluentlenium.pageLoadTimeout", "500");

        assertThat(configuration.getPageLoadTimeout()).isEqualTo(500L);

        configuration.setPageLoadTimeout(250L);

        assertThat(configuration.getPageLoadTimeout()).isEqualTo(250L);
    }

    @Test(expected = ConfigurationException.class)
    public void testFactoryInvalidPropertyFile() {
        DefaultConfigurationFactory factory = new DefaultConfigurationFactory() {
            @Override
            protected InputStream getPropertiesInputStream() {
                return new InputStream() {
                    @Override
                    public int read() throws IOException {
                        throw new IOException();
                    }
                };
            }
        };
        factory.newConfiguration(null, null);
    }

    public void testCustomConfigurationDefaults() {
        DefaultConfigurationFactory factory = new DefaultConfigurationFactory() {
            @Override
            protected InputStream getPropertiesInputStream() {
                return IOUtils.toInputStream("pageLoadTimeout=5000", Charset.forName("UTF-8"));
            }
        };

        ConfigurationDefaults configurationDefaults = new ConfigurationDefaults() {
            @Override
            public String getBaseUrl() {
                return "custom-default-value";
            }
        };

        Configuration configuration = factory.newConfiguration(AnnotatedContainer.class, configurationDefaults);
        setupConfiguration((ComposedConfiguration) configuration);

        // Annotation has higher priority than configuration file, so it should be 2000L and not 5000L.
        assertThat(configuration.getPageLoadTimeout()).isEqualTo(2000L);

        mockEnvironmentVariable("fluentlenium.pageLoadTimeout", "1000");

        assertThat(configuration.getPageLoadTimeout()).isEqualTo(1000L);

        mockSystemProperty("fluentlenium.pageLoadTimeout", "500");

        assertThat(configuration.getPageLoadTimeout()).isEqualTo(500L);

        configuration.setPageLoadTimeout(250L);

        assertThat(configuration.getPageLoadTimeout()).isEqualTo(250L);

        assertThat(configuration.getBaseUrl()).isEqualTo("custom-default-value");
    }

}
