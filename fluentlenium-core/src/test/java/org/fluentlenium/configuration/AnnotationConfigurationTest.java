package org.fluentlenium.configuration;


import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;

public class AnnotationConfigurationTest {
    private static AnnotationConfiguration configuration;
    private static AnnotationConfiguration defaultConfiguration;
    private static AnnotationConfiguration noConfiguration;

    public static class DummyConfigurationFactory implements ConfigurationFactory {
        @Override
        public Configuration newConfiguration(Class<?> containerClass) {
            return null;
        }
    }

    @FluentConfiguration(baseUrl = "http://localhost:3000", configurationFactory = DummyConfigurationFactory.class,
            htmlDumpMode = ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL, htmlDumpPath = "/html-path", implicitlyWait = 1000, pageLoadTimeout = 2000,
            screenshotMode = ConfigurationProperties.TriggerMode.MANUAL, screenshotPath = "/screenshot-path", scriptTimeout = 3000, webDriver = "firefox")
    public static class ConfiguredClass {
    }

    @FluentConfiguration
    public static class DefaultClass {
    }

    @BeforeClass
    public static void beforeClass() {
        configuration = new AnnotationConfiguration(ConfiguredClass.class);
        defaultConfiguration = new AnnotationConfiguration(DefaultClass.class);
        noConfiguration = new AnnotationConfiguration(Object.class);
    }

    @Test
    public void configurationFactory() {
        Assertions.assertThat(configuration.getConfigurationFactory()).isEqualTo(DummyConfigurationFactory.class);
    }

    @Test
    public void webDriver() {
        Assertions.assertThat(noConfiguration.getWebDriver()).isNull();
        Assertions.assertThat(defaultConfiguration.getWebDriver()).isNull();

        Assertions.assertThat(configuration.getWebDriver()).isEqualTo("firefox");
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
    public void scriptTimeout() {
        Assertions.assertThat(noConfiguration.getScriptTimeout()).isNull();
        Assertions.assertThat(defaultConfiguration.getScriptTimeout()).isNull();

        Assertions.assertThat(configuration.getScriptTimeout()).isEqualTo(3000L);
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
}
