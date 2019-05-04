package org.fluentlenium.configuration;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.Properties;

import static org.mockito.Mockito.when;

/**
 * Unit test for {@link PropertiesBackendConfiguration}.
 */
@RunWith(MockitoJUnitRunner.class)
public class PropertiesBackendConfigurationTest {

    private static final String DRIVER_LIFECYCLE = "driverLifecycle";
    @Mock
    private CapabilitiesConfigurationPropertyRetriever retriever;
    private PropertiesBackendConfiguration configuration;
    private Properties properties;

    @Before
    public void before() {
        properties = new Properties();
        configuration = new PropertiesBackendConfiguration(new DefaultPropertiesBackend(properties), "");
        configuration.setCapabilitiesRetriever(retriever);
    }

    public PropertiesBackendConfiguration getConfiguration() {
        return configuration;
    }

    @Test
    public void configurationFactory() {
        Assertions.assertThat(getConfiguration().getConfigurationFactory()).isNull();

        mockProperty("configurationFactory", DummyConfigurationFactory.class);
        Assertions.assertThat(getConfiguration().getConfigurationFactory()).isEqualTo(DummyConfigurationFactory.class);
    }

    @Test
    public void notConfigurationFactoryClass() {
        Assertions.assertThat(getConfiguration().getConfigurationFactory()).isNull();

        mockProperty("configurationFactory", Object.class);
        Assertions.assertThat(getConfiguration().getConfigurationFactory()).isNull();
    }

    @Test
    public void configurationDefaults() {
        Assertions.assertThat(getConfiguration().getConfigurationDefaults()).isNull();

        mockProperty("configurationDefaults", DummyConfigurationDefaults.class);
        Assertions.assertThat(getConfiguration().getConfigurationDefaults()).isEqualTo(DummyConfigurationDefaults.class);
    }

    @Test
    public void notFoundClass() {
        Assertions.assertThat(getConfiguration().getConfigurationFactory()).isNull();

        mockProperty("configurationFactory", "dummy");
        Assertions.assertThat(getConfiguration().getConfigurationFactory()).isNull();
    }

    @Test
    public void webDriver() {
        Assertions.assertThat(getConfiguration().getWebDriver()).isNull();

        mockProperty("webDriver", "firefox");
        Assertions.assertThat(getConfiguration().getWebDriver()).isEqualTo("firefox");
    }

    @Test
    public void remoteUrl() {
        Assertions.assertThat(getConfiguration().getRemoteUrl()).isNull();

        mockProperty("remoteUrl", "http://localhost:4444");
        Assertions.assertThat(getConfiguration().getRemoteUrl()).isEqualTo("http://localhost:4444");
    }

    @Test
    public void capabilities() {
        DesiredCapabilities capabilitiesJSEnabled = new DesiredCapabilities();
        capabilitiesJSEnabled.setJavascriptEnabled(true);
        when(retriever.getCapabilitiesProperty("{\"javascriptEnabled\": true}", null)).thenReturn(capabilitiesJSEnabled);
        DesiredCapabilities capabilitiesJSDisabled = new DesiredCapabilities();
        when(retriever.getCapabilitiesProperty("{\"javascriptEnabled\": false}", null)).thenReturn(capabilitiesJSDisabled);

        Assertions.assertThat(getConfiguration().getWebDriver()).isNull();

        mockProperty("capabilities", "{\"javascriptEnabled\": true}");
        Assertions.assertThat(getConfiguration().getCapabilities()).isEqualTo(capabilitiesJSEnabled);

        mockProperty("capabilities", "{\"javascriptEnabled\": false}");
        Assertions.assertThat(getConfiguration().getCapabilities()).isEqualTo(capabilitiesJSDisabled);
    }

    @Test
    public void desiredCapabilities() {
        DesiredCapabilities capabilitiesFirefox = DesiredCapabilities.firefox();
        when(retriever.getCapabilitiesProperty("firefox", null)).thenReturn(capabilitiesFirefox);
        DesiredCapabilities capabilitiesChrome = DesiredCapabilities.chrome();
        when(retriever.getCapabilitiesProperty("chrome", null)).thenReturn(capabilitiesChrome);

        Assertions.assertThat(getConfiguration().getWebDriver()).isNull();

        mockProperty("capabilities", "firefox");
        Assertions.assertThat(getConfiguration().getCapabilities()).isEqualTo(capabilitiesFirefox);

        mockProperty("capabilities", "chrome");
        Assertions.assertThat(getConfiguration().getCapabilities()).isEqualTo(capabilitiesChrome);
    }

    @Test
    public void capabilitiesClassName() {
        TestCapabilities testCapabilities = new TestCapabilities();
        when(retriever.getCapabilitiesProperty(TestCapabilities.class.getName(), null)).thenReturn(testCapabilities);

        Assertions.assertThat(getConfiguration().getWebDriver()).isNull();

        mockProperty("capabilities", TestCapabilities.class.getName());
        Assertions.assertThat(getConfiguration().getCapabilities()).isSameAs(testCapabilities);
    }

    @Test
    public void capabilitiesFactory() {
        TestCapabilities testCapabilities = new TestCapabilities();
        when(retriever.getCapabilitiesProperty("test-capabilities-factory", null)).thenReturn(testCapabilities);
        Assertions.assertThat(getConfiguration().getWebDriver()).isNull();

        mockProperty("capabilities", "test-capabilities-factory");
        Assertions.assertThat(getConfiguration().getCapabilities()).isSameAs(testCapabilities);
    }

    @Test
    public void capabilitiesURL() {
        URL capabilitiesURL = getClass().getResource("/org/fluentlenium/configuration/capabilities.json");
        DesiredCapabilities capabilitiesJSEnabled = new DesiredCapabilities();
        capabilitiesJSEnabled.setJavascriptEnabled(true);
        when(retriever.getCapabilitiesProperty(capabilitiesURL.toString(), null)).thenReturn(capabilitiesJSEnabled);
        URL capabilitiesFalseURL = getClass().getResource("/org/fluentlenium/configuration/capabilities-false.json");
        DesiredCapabilities capabilitiesJSDisabled = new DesiredCapabilities();
        when(retriever.getCapabilitiesProperty(capabilitiesFalseURL.toString(), null)).thenReturn(capabilitiesJSDisabled);

        Assertions.assertThat(getConfiguration().getCapabilities()).isNull();

        mockProperty("capabilities", capabilitiesURL.toString());
        Assertions.assertThat(getConfiguration().getCapabilities()).isEqualTo(capabilitiesJSEnabled);

        mockProperty("capabilities", capabilitiesFalseURL.toString());
        Assertions.assertThat(getConfiguration().getCapabilities()).isSameAs(capabilitiesJSDisabled);
    }

    @Test
    public void baseUrl() {
        Assertions.assertThat(getConfiguration().getBaseUrl()).isNull();

        mockProperty("baseUrl", "http://localhost:3000");
        Assertions.assertThat(getConfiguration().getBaseUrl()).isEqualTo("http://localhost:3000");
    }

    @Test
    public void baseUrlWithPrefix() {
        Assertions.assertThat(getConfiguration().getBaseUrl()).isNull();

        mockProperty("baseUrl", "http://localhost:3000");
        Assertions.assertThat(getConfiguration().getBaseUrl()).isEqualTo("http://localhost:3000");
    }

    @Test
    public void baseUrlNull() {
        Assertions.assertThat(getConfiguration().getBaseUrl()).isNull();

        mockProperty("baseUrl", null);
        Assertions.assertThat(getConfiguration().getBaseUrl()).isNull();
    }

    @Test
    public void eventsEnabled() {
        Assertions.assertThat(getConfiguration().getEventsEnabled()).isNull();

        mockProperty("eventsEnabled", true);
        Assertions.assertThat(getConfiguration().getEventsEnabled()).isTrue();
    }

    @Test
    public void pageLoadTimeout() {
        Assertions.assertThat(getConfiguration().getPageLoadTimeout()).isNull();

        mockProperty("pageLoadTimeout", 1000L);
        Assertions.assertThat(getConfiguration().getPageLoadTimeout()).isEqualTo(1000L);
    }

    @Test
    public void implicitlyWait() {
        Assertions.assertThat(getConfiguration().getImplicitlyWait()).isNull();

        mockProperty("implicitlyWait", 1000L);
        Assertions.assertThat(getConfiguration().getImplicitlyWait()).isEqualTo(1000L);
    }

    @Test
    public void implicitlyWaitNotNumber() {
        Assertions.assertThat(getConfiguration().getImplicitlyWait()).isNull();

        mockProperty("implicitlyWait", "dummy");
        Assertions.assertThat(getConfiguration().getImplicitlyWait()).isNull();
    }

    @Test
    public void scriptTimeout() {
        Assertions.assertThat(getConfiguration().getScriptTimeout()).isNull();

        mockProperty("scriptTimeout", 1000L);
        Assertions.assertThat(getConfiguration().getScriptTimeout()).isEqualTo(1000L);
    }

    @Test
    public void awaitAtMost() {
        Assertions.assertThat(getConfiguration().getAwaitAtMost()).isNull();

        mockProperty("awaitAtMost", 1000L);
        Assertions.assertThat(getConfiguration().getAwaitAtMost()).isEqualTo(1000L);
    }

    @Test
    public void awaitPollingEvery() {
        Assertions.assertThat(getConfiguration().getAwaitPollingEvery()).isNull();

        mockProperty("awaitPollingEvery", 1000L);
        Assertions.assertThat(getConfiguration().getAwaitPollingEvery()).isEqualTo(1000L);
    }

    @Test
    public void screenshotPath() {
        Assertions.assertThat(getConfiguration().getScreenshotPath()).isNull();

        mockProperty("screenshotPath", "/path/");
        Assertions.assertThat(getConfiguration().getScreenshotPath()).isEqualTo("/path/");
    }

    @Test
    public void driverLifecycleClass() {
        Assertions.assertThat(getConfiguration().getDriverLifecycle()).isNull();

        mockProperty(DRIVER_LIFECYCLE, "cLaSS");
        Assertions.assertThat(getConfiguration().getDriverLifecycle()).isEqualTo(ConfigurationProperties.DriverLifecycle.CLASS);
    }

    @Test
    public void driverLifecycleMethod() {
        Assertions.assertThat(getConfiguration().getDriverLifecycle()).isNull();

        mockProperty(DRIVER_LIFECYCLE, "mEthOd");
        Assertions.assertThat(getConfiguration().getDriverLifecycle())
                .isEqualTo(ConfigurationProperties.DriverLifecycle.METHOD);
    }

    @Test
    public void driverLifecycleJvm() {
        Assertions.assertThat(getConfiguration().getDriverLifecycle()).isNull();

        mockProperty(DRIVER_LIFECYCLE, "jvm");
        Assertions.assertThat(getConfiguration().getDriverLifecycle())
                .isEqualTo(ConfigurationProperties.DriverLifecycle.JVM);
    }

    @Test
    public void driverLifecycleDefault() {
        Assertions.assertThat(getConfiguration().getDriverLifecycle()).isNull();

        mockProperty(DRIVER_LIFECYCLE, "deFaUlT");
        Assertions.assertThat(getConfiguration().getDriverLifecycle())
                .isEqualTo(ConfigurationProperties.DriverLifecycle.DEFAULT);
    }

    @Test
    public void htmlDumpPath() {
        Assertions.assertThat(getConfiguration().getHtmlDumpPath()).isNull();

        mockProperty("htmlDumpPath", "/path/");
        Assertions.assertThat(getConfiguration().getHtmlDumpPath()).isEqualTo("/path/");
    }

    @Test
    public void screenshotMode() {
        Assertions.assertThat(getConfiguration().getScreenshotMode()).isNull();

        mockProperty("screenshotMode", ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL);
        Assertions.assertThat(getConfiguration().getScreenshotMode())
                .isEqualTo(ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL);
    }

    @Test
    public void htmlDumpMode() {
        Assertions.assertThat(getConfiguration().getHtmlDumpMode()).isNull();

        mockProperty("htmlDumpMode", ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL);
        Assertions.assertThat(getConfiguration().getHtmlDumpMode())
                .isEqualTo(ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL);
    }

    @Test
    public void custom() {
        Assertions.assertThat(getConfiguration().getHtmlDumpMode()).isNull();

        mockProperty("key", "value");
        Assertions.assertThat(getConfiguration().getCustomProperty("key")).isEqualTo("value");
    }

    protected void mockProperty(String propertyName, Object propertyValue) {
        if (propertyValue == null) {
            properties.remove(propertyName);
        } else {
            properties.setProperty(propertyName, valueToString(propertyValue));
        }
    }

    protected String valueToString(Object propertyValue) {
        if (propertyValue == null) {
            return null;
        }
        if (propertyValue instanceof Class) {
            return ((Class) propertyValue).getName();
        }
        return String.valueOf(propertyValue);
    }

    public static class DummyConfigurationFactory implements ConfigurationFactory {
        @Override
        public Configuration newConfiguration(Class<?> containerClass, ConfigurationProperties configurationDefaults) {
            return null;
        }
    }

    public static class DummyConfigurationDefaults extends ConfigurationDefaults {

    }
}
