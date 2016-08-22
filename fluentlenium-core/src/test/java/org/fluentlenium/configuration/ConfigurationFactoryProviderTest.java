package org.fluentlenium.configuration;


import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationFactoryProviderTest {
    public static class DummyContainer {

    }

    public static class CustomConfigurationFactory implements ConfigurationFactory {
        @Override
        public Configuration newConfiguration(Class<?> containerClass, ConfigurationProperties configurationDefaults) {
            return new ProgrammaticConfiguration();
        }
    }

    public static class FailingConfigurationFactory implements ConfigurationFactory {
        public FailingConfigurationFactory() {
            throw new IllegalStateException("This must FAIL!");
        }

        @Override
        public Configuration newConfiguration(Class<?> containerClass, ConfigurationProperties configurationDefaults) {
            return new ProgrammaticConfiguration();
        }
    }

    @FluentConfiguration(configurationFactory = CustomConfigurationFactory.class)
    public static class CustomContainer {

    }

    @FluentConfiguration(configurationFactory = FailingConfigurationFactory.class)
    public static class FailingContainer {

    }

    public static class CustomConfigurationDefaults extends ConfigurationDefaults {
        @Override
        public String getBaseUrl() {
            return "custom-base-url";
        }
    }

    @FluentConfiguration(configurationDefaults = CustomConfigurationDefaults.class)
    public static class CustomDefaultsContainer {

    }

    @Test
    public void testDefaultConfiguration() {
        Configuration configuration = ConfigurationFactoryProvider.newConfiguration(DummyContainer.class);
        assertThat(configuration).isExactlyInstanceOf(ComposedConfiguration.class);
    }

    @Test
    public void testCustomConfiguration() {
        Configuration configuration = ConfigurationFactoryProvider.newConfiguration(CustomContainer.class);
        assertThat(configuration).isExactlyInstanceOf(ProgrammaticConfiguration.class);
    }

    @Test(expected = ConfigurationException.class)
    public void testInvalidClassConfiguration() {
        ConfigurationFactoryProvider.newConfiguration(FailingContainer.class);
    }

    @Test
    public void testCustomConfigurationDefaults() {
        Configuration configuration = ConfigurationFactoryProvider.newConfiguration(CustomDefaultsContainer.class);
        assertThat(configuration.getBaseUrl()).isEqualTo("custom-base-url");
    }
}
