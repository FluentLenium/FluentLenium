package org.fluentlenium.configuration;


import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SystemPropertiesConfiguration.class)
public class SystemPropertiesConfigurationTest extends AbstractPropertiesConfigurationTest<SystemPropertiesConfiguration> {

    private SystemPropertiesConfiguration configuration;

    public static class DummyConfigurationFactory implements ConfigurationFactory {
        @Override
        public Configuration newConfiguration(Class<?> containerClass) {
            return null;
        }
    }

    @Before
    public void before() {
        configuration = new SystemPropertiesConfiguration();
        PowerMockito.mockStatic(System.class);
    }

    @Override
    public SystemPropertiesConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    protected void mockProperty(String propertyName, Object propertyValue) {
        Mockito.when(System.getProperty("fluentlenium." + propertyName)).thenReturn(valueToString(propertyValue));
    }
}
