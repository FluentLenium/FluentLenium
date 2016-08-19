package org.fluentlenium.configuration;


import org.assertj.core.api.Assertions;
import org.junit.Test;

public abstract class AbstractPropertiesConfigurationTest<T extends ConfigurationProperties> {

    protected abstract T getConfiguration();

    protected abstract void mockProperty(String propertyName, Object propertyValue);

    protected String valueToString(Object propertyValue) {
        if (propertyValue == null) return null;
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

    @Test
    public void configurationFactory() {
        Assertions.assertThat(getConfiguration().getConfigurationFactory()).isNull();

        mockProperty("configurationFactory", SystemPropertiesConfigurationTest.DummyConfigurationFactory.class);
        Assertions.assertThat(getConfiguration().getConfigurationFactory()).isEqualTo(SystemPropertiesConfigurationTest.DummyConfigurationFactory.class);
    }

    @Test
    public void notConfigurationFactoryClass() {
        Assertions.assertThat(getConfiguration().getConfigurationFactory()).isNull();

        mockProperty("configurationFactory", Object.class);
        Assertions.assertThat(getConfiguration().getConfigurationFactory()).isNull();
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
        Assertions.assertThat(getConfiguration().getScreenshotMode()).isEqualTo(ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL);
    }

    @Test
    public void htmlDumpMode() {
        Assertions.assertThat(getConfiguration().getHtmlDumpMode()).isNull();

        mockProperty("htmlDumpMode", ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL);
        Assertions.assertThat(getConfiguration().getHtmlDumpMode()).isEqualTo(ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL);
    }
}
