package org.fluentlenium.configuration;


import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SystemPropertiesConfiguration.class, EnvironmentVariablesConfiguration.class})
public class ConfigurationDefaultsFactoryTest {

    @FluentConfiguration(pageLoadTimeout = 2000L)
    public static class AnnotatedContainer {

    }

    @Before
    public void before() {
        PowerMockito.mockStatic(System.class);
    }

    @Test
    public void testFactoryWithAnnotation() {
        DefaultConfigurationFactory factory = new DefaultConfigurationFactory() {
            @Override
            protected InputStream getPropertiesInputStream() {
                return IOUtils.toInputStream("pageLoadTimeout=5000");
            }
        };

        Configuration configuration = factory.newConfiguration(AnnotatedContainer.class, new ConfigurationDefaults());

        // Annotation has higher priority than configuration file, so it should be 2000L and not 5000L.
        assertThat(configuration.getPageLoadTimeout()).isEqualTo(2000L);

        Mockito.when(System.getenv("fluentlenium.pageLoadTimeout")).thenReturn("1000");

        assertThat(configuration.getPageLoadTimeout()).isEqualTo(1000L);

        Mockito.when(System.getProperty("fluentlenium.pageLoadTimeout")).thenReturn("500");

        assertThat(configuration.getPageLoadTimeout()).isEqualTo(500L);

        configuration.setPageLoadTimeout(250L);

        assertThat(configuration.getPageLoadTimeout()).isEqualTo(250L);
    }

    @Test
    public void testFactoryNoAnnotation() {
        DefaultConfigurationFactory factory = new DefaultConfigurationFactory() {
            @Override
            protected InputStream getPropertiesInputStream() {
                return IOUtils.toInputStream("fluentlenium.pageLoadTimeout=5000\nscriptTimeout=1000");
            }
        };

        Configuration configuration = factory.newConfiguration(null, null);

        assertThat(configuration.getPageLoadTimeout()).isEqualTo(5000L);
        assertThat(configuration.getScriptTimeout()).isEqualTo(1000L);

        Mockito.when(System.getenv("fluentlenium.pageLoadTimeout")).thenReturn("1000");

        assertThat(configuration.getPageLoadTimeout()).isEqualTo(1000L);

        Mockito.when(System.getProperty("fluentlenium.pageLoadTimeout")).thenReturn("500");

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
                return IOUtils.toInputStream("pageLoadTimeout=5000");
            }
        };

        ConfigurationDefaults configurationDefaults = new ConfigurationDefaults() {
            @Override
            public String getBaseUrl() {
                return "custom-default-value";
            }
        };

        Configuration configuration = factory.newConfiguration(AnnotatedContainer.class, configurationDefaults);

        // Annotation has higher priority than configuration file, so it should be 2000L and not 5000L.
        assertThat(configuration.getPageLoadTimeout()).isEqualTo(2000L);

        Mockito.when(System.getenv("fluentlenium.pageLoadTimeout")).thenReturn("1000");

        assertThat(configuration.getPageLoadTimeout()).isEqualTo(1000L);

        Mockito.when(System.getProperty("fluentlenium.pageLoadTimeout")).thenReturn("500");

        assertThat(configuration.getPageLoadTimeout()).isEqualTo(500L);

        configuration.setPageLoadTimeout(250L);

        assertThat(configuration.getPageLoadTimeout()).isEqualTo(250L);

        assertThat(configuration.getBaseUrl()).isEqualTo("custom-default-value");
    }

}
