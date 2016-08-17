package org.fluentlenium.configuration;

import lombok.experimental.Delegate;

/**
 * A configuration composed with a writable configuration and list of read configurations.
 * <p>
 * When writing a value, it will go in the writable configuration ({@link ConfigurationMutator}).
 * <p>
 * When reading a value, it will get the first value found in the composition of read configurations ({@link ConfigurationProperties}).
 */
public class ComposedConfiguration implements Configuration {
    private final ConfigurationProperties[] configurations;

    @Delegate
    private final ConfigurationMutator writableConfiguration;

    public ComposedConfiguration(ConfigurationMutator writableConfiguration, ConfigurationProperties... configurations) {
        this.writableConfiguration = writableConfiguration;
        this.configurations = configurations;
    }

    @Override
    public Class<? extends ConfigurationFactory> getConfigurationFactory() {
        for (ConfigurationProperties configuration : configurations) {
            Class<? extends ConfigurationFactory> configurationFactory = configuration.getConfigurationFactory();
            if (configurationFactory != null) return configurationFactory;
        }
        return null;
    }

    @Override
    public String getWebDriver() {
        for (ConfigurationProperties configuration : configurations) {
            String webDriver = configuration.getWebDriver();
            if (webDriver != null) return webDriver;
        }
        return null;
    }

    @Override
    public String getBaseUrl() {
        for (ConfigurationProperties configuration : configurations) {
            String baseUrl = configuration.getBaseUrl();
            if (baseUrl != null) return baseUrl;
        }
        return null;
    }

    @Override
    public Long getPageLoadTimeout() {
        for (ConfigurationProperties configuration : configurations) {
            Long pageLoadTimeout = configuration.getPageLoadTimeout();
            if (pageLoadTimeout != null) return pageLoadTimeout;
        }
        return null;
    }

    @Override
    public Long getImplicitlyWait() {
        for (ConfigurationProperties configuration : configurations) {
            Long implicitlyWait = configuration.getImplicitlyWait();
            if (implicitlyWait != null) return implicitlyWait;
        }
        return null;
    }

    @Override
    public Long getScriptTimeout() {
        for (ConfigurationProperties configuration : configurations) {
            Long scriptTimeout = configuration.getScriptTimeout();
            if (scriptTimeout != null) return scriptTimeout;
        }
        return null;
    }

    @Override
    public String getScreenshotPath() {
        for (ConfigurationProperties configuration : configurations) {
            String screenshotPath = configuration.getScreenshotPath();
            if (screenshotPath != null) return screenshotPath;
        }
        return null;
    }

    @Override
    public String getHtmlDumpPath() {
        for (ConfigurationProperties configuration : configurations) {
            String htmlDumpPath = configuration.getHtmlDumpPath();
            if (htmlDumpPath != null) return htmlDumpPath;
        }
        return null;
    }

    @Override
    public TriggerMode getScreenshotMode() {
        for (ConfigurationProperties configuration : configurations) {
            TriggerMode screenshotMode = configuration.getScreenshotMode();
            if (screenshotMode != null) return screenshotMode;
        }
        return null;
    }

    @Override
    public TriggerMode getHtmlDumpMode() {
        for (ConfigurationProperties configuration : configurations) {
            TriggerMode htmlDumpMode = configuration.getHtmlDumpMode();
            if (htmlDumpMode != null) return htmlDumpMode;
        }
        return null;
    }


}
