package io.fluentlenium.configuration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
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
        assertThat(getConfiguration().getConfigurationFactory()).isNull();

        mockProperty("configurationFactory", DummyConfigurationFactory.class);
        assertThat(getConfiguration().getConfigurationFactory()).isEqualTo(DummyConfigurationFactory.class);
    }

    @Test
    public void notConfigurationFactoryClass() {
        assertThat(getConfiguration().getConfigurationFactory()).isNull();

        mockProperty("configurationFactory", Object.class);
        assertThat(getConfiguration().getConfigurationFactory()).isNull();
    }

    @Test
    public void configurationDefaults() {
        assertThat(getConfiguration().getConfigurationDefaults()).isNull();

        mockProperty("configurationDefaults", DummyConfigurationDefaults.class);
        assertThat(getConfiguration().getConfigurationDefaults()).isEqualTo(DummyConfigurationDefaults.class);
    }

    @Test
    public void notFoundClass() {
        assertThat(getConfiguration().getConfigurationFactory()).isNull();

        mockProperty("configurationFactory", "dummy");
        assertThat(getConfiguration().getConfigurationFactory()).isNull();
    }

    @Test
    public void webDriver() {
        assertThat(getConfiguration().getWebDriver()).isNull();

        mockProperty("webDriver", "firefox");
        assertThat(getConfiguration().getWebDriver()).isEqualTo("firefox");
    }

    @Test
    public void remoteUrl() {
        assertThat(getConfiguration().getRemoteUrl()).isNull();

        mockProperty("remoteUrl", "http://localhost:4444");
        assertThat(getConfiguration().getRemoteUrl()).isEqualTo("http://localhost:4444");
    }

    @Test
    public void capabilities() {
        DesiredCapabilities capabilitiesJSEnabled = new DesiredCapabilities();
        capabilitiesJSEnabled.setJavascriptEnabled(true);
        when(retriever.getCapabilitiesProperty("{\"javascriptEnabled\": true}", null)).thenReturn(capabilitiesJSEnabled);
        DesiredCapabilities capabilitiesJSDisabled = new DesiredCapabilities();
        when(retriever.getCapabilitiesProperty("{\"javascriptEnabled\": false}", null)).thenReturn(capabilitiesJSDisabled);

        assertThat(getConfiguration().getWebDriver()).isNull();

        mockProperty("capabilities", "{\"javascriptEnabled\": true}");
        assertThat(getConfiguration().getCapabilities()).isEqualTo(capabilitiesJSEnabled);

        mockProperty("capabilities", "{\"javascriptEnabled\": false}");
        assertThat(getConfiguration().getCapabilities()).isEqualTo(capabilitiesJSDisabled);
    }

    @Test
    public void desiredCapabilities() {
        DesiredCapabilities capabilitiesFirefox = PredefinedDesiredCapabilities.firefox();
        when(retriever.getCapabilitiesProperty("firefox", null)).thenReturn(capabilitiesFirefox);
        DesiredCapabilities capabilitiesChrome = PredefinedDesiredCapabilities.chrome();
        when(retriever.getCapabilitiesProperty("chrome", null)).thenReturn(capabilitiesChrome);

        assertThat(getConfiguration().getWebDriver()).isNull();

        mockProperty("capabilities", "firefox");
        assertThat(getConfiguration().getCapabilities()).isEqualTo(capabilitiesFirefox);

        mockProperty("capabilities", "chrome");
        assertThat(getConfiguration().getCapabilities()).isEqualTo(capabilitiesChrome);
    }

    @Test
    public void capabilitiesClassName() {
        TestCapabilities testCapabilities = new TestCapabilities();
        when(retriever.getCapabilitiesProperty(TestCapabilities.class.getName(), null)).thenReturn(testCapabilities);

        assertThat(getConfiguration().getWebDriver()).isNull();

        mockProperty("capabilities", TestCapabilities.class.getName());
        assertThat(getConfiguration().getCapabilities()).isSameAs(testCapabilities);
    }

    @Test
    public void capabilitiesFactory() {
        TestCapabilities testCapabilities = new TestCapabilities();
        when(retriever.getCapabilitiesProperty("test-capabilities-factory", null)).thenReturn(testCapabilities);
        assertThat(getConfiguration().getWebDriver()).isNull();

        mockProperty("capabilities", "test-capabilities-factory");
        assertThat(getConfiguration().getCapabilities()).isSameAs(testCapabilities);
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

        assertThat(getConfiguration().getCapabilities()).isNull();

        mockProperty("capabilities", capabilitiesURL.toString());
        assertThat(getConfiguration().getCapabilities()).isEqualTo(capabilitiesJSEnabled);

        mockProperty("capabilities", capabilitiesFalseURL.toString());
        assertThat(getConfiguration().getCapabilities()).isSameAs(capabilitiesJSDisabled);
    }

    @Test
    public void baseUrl() {
        assertThat(getConfiguration().getBaseUrl()).isNull();

        mockProperty("baseUrl", "http://localhost:3000");
        assertThat(getConfiguration().getBaseUrl()).isEqualTo("http://localhost:3000");
    }

    @Test
    public void baseUrlWithPrefix() {
        assertThat(getConfiguration().getBaseUrl()).isNull();

        mockProperty("baseUrl", "http://localhost:3000");
        assertThat(getConfiguration().getBaseUrl()).isEqualTo("http://localhost:3000");
    }

    @Test
    public void baseUrlNull() {
        assertThat(getConfiguration().getBaseUrl()).isNull();

        mockProperty("baseUrl", null);
        assertThat(getConfiguration().getBaseUrl()).isNull();
    }

    @Test
    public void eventsEnabled() {
        assertThat(getConfiguration().getEventsEnabled()).isNull();

        mockProperty("eventsEnabled", true);
        assertThat(getConfiguration().getEventsEnabled()).isTrue();
    }

    @Test
    public void pageLoadTimeout() {
        assertThat(getConfiguration().getPageLoadTimeout()).isNull();

        mockProperty("pageLoadTimeout", 1000L);
        assertThat(getConfiguration().getPageLoadTimeout()).isEqualTo(1000L);
    }

    @Test
    public void implicitlyWait() {
        assertThat(getConfiguration().getImplicitlyWait()).isNull();

        mockProperty("implicitlyWait", 1000L);
        assertThat(getConfiguration().getImplicitlyWait()).isEqualTo(1000L);
    }

    @Test
    public void implicitlyWaitNotNumber() {
        assertThat(getConfiguration().getImplicitlyWait()).isNull();

        mockProperty("implicitlyWait", "dummy");
        assertThat(getConfiguration().getImplicitlyWait()).isNull();
    }

    @Test
    public void scriptTimeout() {
        assertThat(getConfiguration().getScriptTimeout()).isNull();

        mockProperty("scriptTimeout", 1000L);
        assertThat(getConfiguration().getScriptTimeout()).isEqualTo(1000L);
    }

    @Test
    public void awaitAtMost() {
        assertThat(getConfiguration().getAwaitAtMost()).isNull();

        mockProperty("awaitAtMost", 1000L);
        assertThat(getConfiguration().getAwaitAtMost()).isEqualTo(1000L);
    }

    @Test
    public void awaitPollingEvery() {
        assertThat(getConfiguration().getAwaitPollingEvery()).isNull();

        mockProperty("awaitPollingEvery", 1000L);
        assertThat(getConfiguration().getAwaitPollingEvery()).isEqualTo(1000L);
    }

    @Test
    public void screenshotPath() {
        assertThat(getConfiguration().getScreenshotPath()).isNull();

        mockProperty("screenshotPath", "/path/");
        assertThat(getConfiguration().getScreenshotPath()).isEqualTo("/path/");
    }

    @Test
    public void driverLifecycleClass() {
        assertThat(getConfiguration().getDriverLifecycle()).isNull();

        mockProperty(DRIVER_LIFECYCLE, "cLaSS");
        assertThat(getConfiguration().getDriverLifecycle()).isEqualTo(ConfigurationProperties.DriverLifecycle.CLASS);
    }

    @Test
    public void driverLifecycleMethod() {
        assertThat(getConfiguration().getDriverLifecycle()).isNull();

        mockProperty(DRIVER_LIFECYCLE, "mEthOd");
        assertThat(getConfiguration().getDriverLifecycle()).isEqualTo(ConfigurationProperties.DriverLifecycle.METHOD);
    }

    @Test
    public void driverLifecycleJvm() {
        assertThat(getConfiguration().getDriverLifecycle()).isNull();

        mockProperty(DRIVER_LIFECYCLE, "jvm");
        assertThat(getConfiguration().getDriverLifecycle()).isEqualTo(ConfigurationProperties.DriverLifecycle.JVM);
    }

    @Test
    public void driverLifecycleDefault() {
        assertThat(getConfiguration().getDriverLifecycle()).isNull();

        mockProperty(DRIVER_LIFECYCLE, "deFaUlT");
        assertThat(getConfiguration().getDriverLifecycle()).isEqualTo(ConfigurationProperties.DriverLifecycle.DEFAULT);
    }

    @Test
    public void htmlDumpPath() {
        assertThat(getConfiguration().getHtmlDumpPath()).isNull();

        mockProperty("htmlDumpPath", "/path/");
        assertThat(getConfiguration().getHtmlDumpPath()).isEqualTo("/path/");
    }

    @Test
    public void screenshotMode() {
        assertThat(getConfiguration().getScreenshotMode()).isNull();

        mockProperty("screenshotMode", ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL);
        assertThat(getConfiguration().getScreenshotMode()).isEqualTo(ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL);
    }

    @Test
    public void htmlDumpMode() {
        assertThat(getConfiguration().getHtmlDumpMode()).isNull();

        mockProperty("htmlDumpMode", ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL);
        assertThat(getConfiguration().getHtmlDumpMode()).isEqualTo(ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL);
    }

    @Test
    public void custom() {
        assertThat(getConfiguration().getHtmlDumpMode()).isNull();

        mockProperty("key", "value");
        assertThat(getConfiguration().getCustomProperty("key")).isEqualTo("value");
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
