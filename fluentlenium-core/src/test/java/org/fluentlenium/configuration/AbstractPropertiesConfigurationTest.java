package org.fluentlenium.configuration;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.URL;

public abstract class AbstractPropertiesConfigurationTest<T extends AbstractPropertiesConfiguration> {

    public static class DummyConfigurationFactory implements ConfigurationFactory {
        @Override
        public Configuration newConfiguration(final Class<?> containerClass,
                final ConfigurationProperties configurationDefaults) {
            return null;
        }
    }

    public static class DummyConfigurationDefaults extends ConfigurationDefaults {

    }

    protected abstract T getConfiguration();

    protected abstract void mockProperty(String propertyName, Object propertyValue);

    protected String valueToString(final Object propertyValue) {
        if (propertyValue == null) {
            return null;
        }
        if (propertyValue instanceof Class) {
            return ((Class) propertyValue).getName();
        }
        return String.valueOf(propertyValue);
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
        Assertions.assertThat(getConfiguration().getWebDriver()).isNull();

        mockProperty("capabilities", "{\"javascriptEnabled\": true}");

        final DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setJavascriptEnabled(true);
        Assertions.assertThat(getConfiguration().getCapabilities()).isEqualTo(capabilities);

        mockProperty("capabilities", "{\"javascriptEnabled\": false}");
        Assertions.assertThat(getConfiguration().getCapabilities()).isNotEqualTo(capabilities);
    }

    @Test
    public void desiredCapabilities() {
        Assertions.assertThat(getConfiguration().getWebDriver()).isNull();

        mockProperty("capabilities", "firefox");

        final DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        Assertions.assertThat(getConfiguration().getCapabilities()).isEqualTo(capabilities);

        mockProperty("capabilities", "chrome");
        Assertions.assertThat(getConfiguration().getCapabilities()).isNotEqualTo(capabilities);
    }

    @Test
    public void capabilitiesClassName() {
        Assertions.assertThat(getConfiguration().getWebDriver()).isNull();

        mockProperty("capabilities", TestCapabilities.class.getName());

        Assertions.assertThat(getConfiguration().getCapabilities()).isExactlyInstanceOf(TestCapabilities.class);
    }

    @Test
    public void capabilitiesFactory() {
        Assertions.assertThat(getConfiguration().getWebDriver()).isNull();

        mockProperty("capabilities", "test-capabilities-factory");

        Assertions.assertThat(getConfiguration().getCapabilities()).isExactlyInstanceOf(TestCapabilities.class);
    }

    @Test
    public void capabilitiesURL() throws IOException {
        Assertions.assertThat(getConfiguration().getCapabilities()).isNull();

        final URL capabilitiesURL = getClass().getResource("/org/fluentlenium/configuration/capabilities.json");

        mockProperty("capabilities", capabilitiesURL.toString());

        final DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setJavascriptEnabled(true);
        Assertions.assertThat(getConfiguration().getCapabilities()).isEqualTo(capabilities);

        final URL capabilitiesFalseURL = getClass().getResource("/org/fluentlenium/configuration/capabilities-false.json");

        mockProperty("capabilities", capabilitiesFalseURL.toString());
        Assertions.assertThat(getConfiguration().getCapabilities()).isNotEqualTo(capabilities);

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
    public void screenshotPath() {
        Assertions.assertThat(getConfiguration().getScreenshotPath()).isNull();

        mockProperty("screenshotPath", "/path/");
        Assertions.assertThat(getConfiguration().getScreenshotPath()).isEqualTo("/path/");
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
        Assertions.assertThat(getConfiguration().getCustomProperty("key"))
                .isEqualTo("value");
    }
}
