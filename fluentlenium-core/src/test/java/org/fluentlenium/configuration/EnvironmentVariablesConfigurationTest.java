package org.fluentlenium.configuration;


import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(EnvironmentVariablesConfiguration.class)
public class EnvironmentVariablesConfigurationTest extends AbstractPropertiesConfigurationTest<EnvironmentVariablesConfiguration> {

    private EnvironmentVariablesConfiguration configuration;

    public static class DummyConfigurationFactory implements ConfigurationFactory {
        @Override
        public Configuration newConfiguration(Class<?> containerClass, ConfigurationProperties configurationDefaults) {
            return null;
        }
    }

    @Before
    public void before() {
        configuration = new EnvironmentVariablesConfiguration();
        PowerMockito.mockStatic(System.class);
    }

    @Override
    public EnvironmentVariablesConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    protected void mockProperty(String propertyName, Object propertyValue) {
        Mockito.when(System.getenv("fluentlenium." + propertyName)).thenReturn(valueToString(propertyValue));
    }

}
