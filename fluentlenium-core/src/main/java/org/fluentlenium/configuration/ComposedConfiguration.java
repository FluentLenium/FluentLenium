package org.fluentlenium.configuration;

import lombok.experimental.Delegate;
import org.openqa.selenium.WebDriver;

public class ComposedConfiguration implements Configuration {
    private final ConfigurationRead[] configurations;

    @Delegate
    private final ConfigurationWrite writableConfiguration;

    public ComposedConfiguration(ConfigurationWrite writableConfiguration, ConfigurationRead... configurations) {
        this.writableConfiguration = writableConfiguration;
        this.configurations = configurations;
    }

    @Override
    public WebDriver getDefaultDriver() {
        for (ConfigurationRead configuration : configurations) {
            WebDriver defaultDriver = configuration.getDefaultDriver();
            if (defaultDriver != null) return defaultDriver;
        }
        return null;
    }

    @Override
    public Class<? extends ConfigurationFactory> getConfigurationFactory() {
        for (ConfigurationRead configuration : configurations) {
            Class<? extends ConfigurationFactory> configurationFactory = configuration.getConfigurationFactory();
            if (configurationFactory != null) return configurationFactory;
        }
        return null;
    }

    @Override
    public String getWebDriver() {
        for (ConfigurationRead configuration : configurations) {
            String webDriver = configuration.getWebDriver();
            if (webDriver != null) return webDriver;
        }
        return null;
    }

    @Override
    public String getDefaultBaseUrl() {
        for (ConfigurationRead configuration : configurations) {
            String baseUrl = configuration.getDefaultBaseUrl();
            if (baseUrl != null) return baseUrl;
        }
        return null;
    }

    @Override
    public Long getPageLoadTimeout() {
        for (ConfigurationRead configuration : configurations) {
            Long pageLoadTimeout = configuration.getPageLoadTimeout();
            if (pageLoadTimeout != null) return pageLoadTimeout;
        }
        return null;
    }

    @Override
    public Long getImplicitlyWait() {
        for (ConfigurationRead configuration : configurations) {
            Long implicitlyWait = configuration.getImplicitlyWait();
            if (implicitlyWait != null) return implicitlyWait;
        }
        return null;
    }

    @Override
    public Long getScriptTimeout() {
        for (ConfigurationRead configuration : configurations) {
            Long scriptTimeout = configuration.getScriptTimeout();
            if (scriptTimeout != null) return scriptTimeout;
        }
        return null;
    }

    @Override
    public String getScreenshotPath() {
        for (ConfigurationRead configuration : configurations) {
            String screenshotPath = configuration.getScreenshotPath();
            if (screenshotPath != null) return screenshotPath;
        }
        return null;
    }

    @Override
    public String getHtmlDumpPath() {
        for (ConfigurationRead configuration : configurations) {
            String htmlDumpPath = configuration.getHtmlDumpPath();
            if (htmlDumpPath != null) return htmlDumpPath;
        }
        return null;
    }

    @Override
    public TriggerMode getScreenshotMode() {
        for (ConfigurationRead configuration : configurations) {
            TriggerMode screenshotMode = configuration.getScreenshotMode();
            if (screenshotMode != null) return screenshotMode;
        }
        return null;
    }

    @Override
    public TriggerMode getHtmlDumpMode() {
        for (ConfigurationRead configuration : configurations) {
            TriggerMode htmlDumpMode = configuration.getHtmlDumpMode();
            if (htmlDumpMode != null) return htmlDumpMode;
        }
        return null;
    }


}
