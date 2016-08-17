package org.fluentlenium.configuration;

import lombok.experimental.Delegate;

/**
 * A configuration composed by a writable configuration and others read configurations.
 * <p>
 * When writing a value, it will go in the writable configuration.
 * <p>
 * When reading a value, it will get the first value found in the composition of read configurations.
 */
public class ComposedConfiguration implements Configuration {
    private final ConfigurationRead[] configurations;

    @Delegate
    private final ConfigurationWrite writableConfiguration;

    public ComposedConfiguration(ConfigurationWrite writableConfiguration, ConfigurationRead... configurations) {
        this.writableConfiguration = writableConfiguration;
        this.configurations = configurations;
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
    public String getBaseUrl() {
        for (ConfigurationRead configuration : configurations) {
            String baseUrl = configuration.getBaseUrl();
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
