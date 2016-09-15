package org.fluentlenium.configuration;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CapabilitiesTest {
    private CapabilitiesRegistryImpl capabilities;

    @Before
    public void before() {
        capabilities = new CapabilitiesRegistryImpl();
    }

    @Test
    public void testDesiredCapabilities() {
        CapabilitiesFactory firefox = capabilities.get("DesiredCapabilities.firefox");
        assertThat(firefox).isExactlyInstanceOf(MethodInvocationReflectionFactory.class);
    }

    @Test
    public void testNoDefault() throws NoSuchFieldException, IllegalAccessException {
        Field factoriesField = AbstractFactoryRegistryImpl.class.getDeclaredField("factories");
        factoriesField.setAccessible(true);
        Map<String, Object> factories = (Map<String, Object>) factoriesField.get(capabilities);
        factories.remove("test-capabilities-factory");

        CapabilitiesFactory webDriverFactory = capabilities.get(null);
        assertThat(webDriverFactory).isNull();
    }

    @Test
    public void testDefault() throws NoSuchFieldException, IllegalAccessException {
        CapabilitiesFactory webDriverFactory = capabilities.get(null);
        assertThat(webDriverFactory).isExactlyInstanceOf(TestCapabilitiesFactory.class);
    }

    @Test
    public void testSingleton() {
        assertThat(CapabilitiesRegistry.INSTANCE.get("DesiredCapabilities.firefox")).isNotNull();
    }


}
