package org.fluentlenium.configuration;

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

    public static class FailingConstructorConfigurationFactory implements ConfigurationFactory {
        public FailingConstructorConfigurationFactory() {
            throw new IllegalStateException("This must FAIL!");
        }

        @Override
        public Configuration newConfiguration(Class<?> containerClass, ConfigurationProperties configurationDefaults) {
            return new ProgrammaticConfiguration();
        }
    }

    public static class FailingConfigurationConfigurationFactory implements ConfigurationFactory {
        public FailingConfigurationConfigurationFactory() {
        }

        @Override
        public Configuration newConfiguration(Class<?> containerClass, ConfigurationProperties configurationDefaults) {
            ProgrammaticConfiguration programmaticConfiguration = new ProgrammaticConfiguration();
            programmaticConfiguration.setConfigurationDefaults(FailingConfigurationDefaults.class);
            return programmaticConfiguration;
        }
    }

    @FluentConfiguration(configurationFactory = CustomConfigurationFactory.class)
    public static class CustomContainer {

    }

    @FluentConfiguration(configurationFactory = FailingConstructorConfigurationFactory.class)
    public static class FailingContainer {

    }

    @FluentConfiguration(configurationFactory = FailingConfigurationConfigurationFactory.class)
    public static class FailingConfigurationContainer {

    }

    public static class CustomConfigurationDefaults extends ConfigurationDefaults {
        @Override
        public String getBaseUrl() {
            return "custom-base-url";
        }
    }

    private static final class PrivateConfigurationDefaults extends ConfigurationDefaults {
        private PrivateConfigurationDefaults() {
        }
    }

    public static final class FailingConfigurationDefaults extends ConfigurationDefaults {
        private FailingConfigurationDefaults() {
            throw new IllegalStateException("This must FAIL!");
        }
    }

    @FluentConfiguration(configurationDefaults = CustomConfigurationDefaults.class)
    public static class CustomDefaultsContainer {

    }

    @FluentConfiguration(configurationDefaults = PrivateConfigurationDefaults.class)
    public static class PrivateDefaultsContainer {

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

    @Test
    public void testPrivateCustomConfiguration() {
        Configuration configuration = ConfigurationFactoryProvider.newConfiguration(PrivateDefaultsContainer.class);
        assertThat(configuration).isNotNull();
    }

    @Test(expected = ConfigurationException.class)
    public void testInvalidClassConfiguration() {
        ConfigurationFactoryProvider.newConfiguration(FailingContainer.class);
    }

    @Test(expected = ConfigurationException.class)
    public void testInvalidConfigurationClassConfiguration() {
        ConfigurationFactoryProvider.newConfiguration(FailingConfigurationContainer.class);
    }

    @Test
    public void testCustomConfigurationDefaults() {
        Configuration configuration = ConfigurationFactoryProvider.newConfiguration(CustomDefaultsContainer.class);
        assertThat(configuration.getBaseUrl()).isEqualTo("custom-base-url");
    }
}
