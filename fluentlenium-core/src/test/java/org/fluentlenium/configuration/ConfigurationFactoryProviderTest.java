package org.fluentlenium.configuration;


import org.assertj.core.api.Assertions;
import org.junit.Test;

public class ConfigurationFactoryProviderTest {
    public static class DummyContainer {

    }

    public static class CustomConfigurationFactory implements ConfigurationFactory {
        @Override
        public Configuration newConfiguration(Class<?> containerClass) {
            return new ProgrammaticConfiguration();
        }
    }

    public static class FailingConfigurationFactory implements ConfigurationFactory {
        public FailingConfigurationFactory() {
            throw new IllegalStateException("This must FAIL!");
        }

        @Override
        public Configuration newConfiguration(Class<?> containerClass) {
            return new ProgrammaticConfiguration();
        }
    }

    @FluentConfiguration(configurationFactory = CustomConfigurationFactory.class)
    public static class CustomContainer {

    }

    @FluentConfiguration(configurationFactory = FailingConfigurationFactory.class)
    public static class FailingContainer {

    }

    @Test
    public void testDefaultConfiguration() {
        Configuration configuration = ConfigurationFactoryProvider.newConfiguration(DummyContainer.class);
        Assertions.assertThat(configuration).isExactlyInstanceOf(ComposedConfiguration.class);
    }

    @Test
    public void testCustomConfiguration() {
        Configuration configuration = ConfigurationFactoryProvider.newConfiguration(CustomContainer.class);
        Assertions.assertThat(configuration).isExactlyInstanceOf(ProgrammaticConfiguration.class);
    }

    @Test(expected = ConfigurationException.class)
    public void testInvalidClassConfiguration() {
        ConfigurationFactoryProvider.newConfiguration(FailingContainer.class);
    }
}
