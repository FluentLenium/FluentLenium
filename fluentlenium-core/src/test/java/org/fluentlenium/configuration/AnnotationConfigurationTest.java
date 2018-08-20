package org.fluentlenium.configuration;

import org.assertj.core.api.Assertions;
import org.fluentlenium.configuration.PropertiesBackendConfigurationTest.DummyConfigurationDefaults;
import org.fluentlenium.configuration.PropertiesBackendConfigurationTest.DummyConfigurationFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

public class AnnotationConfigurationTest {
    private static AnnotationConfiguration configuration;
    private static AnnotationConfiguration defaultConfiguration;
    private static AnnotationConfiguration noConfiguration;
    private static AnnotationConfiguration desiredCapabilitiesConfiguration;
    private static AnnotationConfiguration capabilitiesClassNameConfiguration;
    private static AnnotationConfiguration capabilitiesFactoryConfiguration;

    @FluentConfiguration(baseUrl = "http://localhost:3000", configurationFactory = DummyConfigurationFactory.class,
            configurationDefaults = DummyConfigurationDefaults.class, eventsEnabled = FluentConfiguration.BooleanValue.FALSE,
            capabilities = "{\"javascriptEnabled\": true}", remoteUrl = "http://localhost:4444", htmlDumpMode =
            ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL, htmlDumpPath = "/html-path", implicitlyWait = 1000,
            pageLoadTimeout = 2000, awaitPollingEvery = 10, awaitAtMost = 100, screenshotMode = ConfigurationProperties
            .TriggerMode.MANUAL, screenshotPath = "/screenshot-path", scriptTimeout = 3000, webDriver = "firefox", custom =
            @CustomProperty(name = "key", value = "value"))
    public static class ConfiguredClass {
    }

    @FluentConfiguration(capabilities = "firefox")
    public static class DesiredCapabilitiesClass {
    }

    @FluentConfiguration(capabilities = "org.fluentlenium.configuration.TestCapabilities")
    public static class CapabilitiesClassNameClass {
    }

    @FluentConfiguration(capabilities = "test-capabilities-factory")
    public static class CapabilitiesFactoryClass {
    }

    @FluentConfiguration
    public static class DefaultClass {
    }

    @BeforeClass
    public static void beforeClass() {
        configuration = new AnnotationConfiguration(ConfiguredClass.class);
        defaultConfiguration = new AnnotationConfiguration(DefaultClass.class);
        noConfiguration = new AnnotationConfiguration(Object.class);
        desiredCapabilitiesConfiguration = new AnnotationConfiguration(DesiredCapabilitiesClass.class);
        capabilitiesClassNameConfiguration = new AnnotationConfiguration(CapabilitiesClassNameClass.class);
        capabilitiesFactoryConfiguration = new AnnotationConfiguration(CapabilitiesFactoryClass.class);
    }

    @Test
    public void configurationFactory() {
        Assertions.assertThat(configuration.getConfigurationFactory()).isEqualTo(DummyConfigurationFactory.class);
    }

    @Test
    public void configurationDefaults() {
        Assertions.assertThat(configuration.getConfigurationDefaults()).isEqualTo(DummyConfigurationDefaults.class);
    }

    @Test
    public void webDriver() {
        Assertions.assertThat(noConfiguration.getWebDriver()).isNull();
        Assertions.assertThat(defaultConfiguration.getWebDriver()).isNull();

        Assertions.assertThat(configuration.getWebDriver()).isEqualTo("firefox");
    }

    @Test
    public void remoteUrl() {
        Assertions.assertThat(noConfiguration.getRemoteUrl()).isNull();
        Assertions.assertThat(defaultConfiguration.getRemoteUrl()).isNull();

        Assertions.assertThat(configuration.getRemoteUrl()).isEqualTo("http://localhost:4444");
    }

    @Test
    public void capabilities() {
        Assertions.assertThat(noConfiguration.getCapabilities()).isNull();
        Assertions.assertThat(defaultConfiguration.getCapabilities()).isNull();

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setJavascriptEnabled(true);

        Assertions.assertThat(configuration.getCapabilities()).isEqualTo(capabilities);
    }

    @Test
    public void desiredCapabilities() {
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        Assertions.assertThat(desiredCapabilitiesConfiguration.getCapabilities()).isEqualTo(capabilities);

        DesiredCapabilities differentCapabilities = DesiredCapabilities.chrome();
        Assertions.assertThat(desiredCapabilitiesConfiguration.getCapabilities()).isNotEqualTo(differentCapabilities);
    }

    @Test
    public void capabilitiesClassName() {
        Assertions.assertThat(capabilitiesClassNameConfiguration.getCapabilities()).isExactlyInstanceOf(TestCapabilities.class);
    }

    @Test
    public void capabilitiesFactory() {
        Assertions.assertThat(capabilitiesFactoryConfiguration.getCapabilities()).isExactlyInstanceOf(TestCapabilities.class);
    }

    @Test
    public void baseUrl() {
        Assertions.assertThat(noConfiguration.getBaseUrl()).isNull();
        Assertions.assertThat(defaultConfiguration.getBaseUrl()).isNull();

        Assertions.assertThat(configuration.getBaseUrl()).isEqualTo("http://localhost:3000");
    }

    @Test
    public void pageLoadTimeout() {
        Assertions.assertThat(noConfiguration.getPageLoadTimeout()).isNull();
        Assertions.assertThat(defaultConfiguration.getPageLoadTimeout()).isNull();

        Assertions.assertThat(configuration.getPageLoadTimeout()).isEqualTo(2000L);
    }

    @Test
    public void implicitlyWait() {
        Assertions.assertThat(noConfiguration.getImplicitlyWait()).isNull();
        Assertions.assertThat(defaultConfiguration.getImplicitlyWait()).isNull();

        Assertions.assertThat(configuration.getImplicitlyWait()).isEqualTo(1000L);
    }

    @Test
    public void awaitAtMost() {
        Assertions.assertThat(noConfiguration.getAwaitAtMost()).isNull();
        Assertions.assertThat(defaultConfiguration.getAwaitAtMost()).isNull();

        Assertions.assertThat(configuration.getAwaitAtMost()).isEqualTo(100L);
    }

    @Test
    public void awaitPollingEvery() {
        Assertions.assertThat(noConfiguration.getAwaitPollingEvery()).isNull();
        Assertions.assertThat(defaultConfiguration.getAwaitPollingEvery()).isNull();

        Assertions.assertThat(configuration.getAwaitPollingEvery()).isEqualTo(10L);
    }

    @Test
    public void scriptTimeout() {
        Assertions.assertThat(noConfiguration.getScriptTimeout()).isNull();
        Assertions.assertThat(defaultConfiguration.getScriptTimeout()).isNull();

        Assertions.assertThat(configuration.getScriptTimeout()).isEqualTo(3000L);
    }

    @Test
    public void eventsEnabled() {
        Assertions.assertThat(noConfiguration.getEventsEnabled()).isNull();
        Assertions.assertThat(defaultConfiguration.getEventsEnabled()).isNull();

        Assertions.assertThat(configuration.getEventsEnabled()).isEqualTo(false);
    }

    @Test
    public void screenshotPath() {
        Assertions.assertThat(noConfiguration.getScreenshotPath()).isNull();
        Assertions.assertThat(defaultConfiguration.getScreenshotPath()).isNull();

        Assertions.assertThat(configuration.getScreenshotPath()).isEqualTo("/screenshot-path");
    }

    @Test
    public void htmlDumpPath() {
        Assertions.assertThat(noConfiguration.getHtmlDumpPath()).isNull();
        Assertions.assertThat(defaultConfiguration.getHtmlDumpPath()).isNull();

        Assertions.assertThat(configuration.getHtmlDumpPath()).isEqualTo("/html-path");
    }

    @Test
    public void screenshotMode() {
        Assertions.assertThat(noConfiguration.getScreenshotMode()).isNull();
        Assertions.assertThat(defaultConfiguration.getScreenshotMode()).isNull();

        Assertions.assertThat(configuration.getScreenshotMode()).isEqualTo(ConfigurationProperties.TriggerMode.MANUAL);
    }

    @Test
    public void htmlDumpMode() {
        Assertions.assertThat(noConfiguration.getHtmlDumpMode()).isNull();
        Assertions.assertThat(defaultConfiguration.getHtmlDumpMode()).isNull();

        Assertions.assertThat(configuration.getHtmlDumpMode()).isEqualTo(ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL);
    }

    @Test
    public void custom() {
        Assertions.assertThat(noConfiguration.getCustomProperty("key")).isNull();
        Assertions.assertThat(defaultConfiguration.getCustomProperty("key")).isNull();

        Assertions.assertThat(configuration.getCustomProperty("key")).isEqualTo("value");
    }
}
