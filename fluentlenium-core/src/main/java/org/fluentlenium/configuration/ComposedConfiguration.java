package org.fluentlenium.configuration;

import lombok.experimental.Delegate;
import org.openqa.selenium.Capabilities;

/**
 * A configuration composed with a writable configuration and list of read configurations.
 * <p>
 * When writing a value, it will go in the writable configuration ({@link ConfigurationMutator}).
 * <p>
 * When reading a value, it will get the first value found in the composition of read configurations
 * ({@link ConfigurationProperties}).
 */
public class ComposedConfiguration implements Configuration {
    private final ConfigurationProperties[] configurations;

    @Delegate
    private final ConfigurationMutator writableConfiguration; // NOPMD SingularField

    public ComposedConfiguration(final ConfigurationMutator writableConfiguration,
            final ConfigurationProperties... configurations) {
        this.writableConfiguration = writableConfiguration;
        this.configurations = configurations;
    }

    @Override
    public Class<? extends ConfigurationFactory> getConfigurationFactory() {
        for (final ConfigurationProperties configuration : configurations) {
            final Class<? extends ConfigurationFactory> configurationFactory = configuration.getConfigurationFactory();
            if (configurationFactory != null) {
                return configurationFactory;
            }
        }
        return null;
    }

    @Override
    public Class<? extends ConfigurationProperties> getConfigurationDefaults() {
        for (final ConfigurationProperties configuration : configurations) {
            final Class<? extends ConfigurationProperties> configurationDefaults = configuration.getConfigurationDefaults();
            if (configurationDefaults != null) {
                return configurationDefaults;
            }
        }
        return null;
    }

    @Override
    public String getWebDriver() {
        for (final ConfigurationProperties configuration : configurations) {
            final String webDriver = configuration.getWebDriver();
            if (webDriver != null) {
                return webDriver;
            }
        }
        return null;
    }

    @Override
    public String getRemoteUrl() {
        for (final ConfigurationProperties configuration : configurations) {
            final String remoteUrl = configuration.getRemoteUrl();
            if (remoteUrl != null) {
                return remoteUrl;
            }
        }
        return null;
    }

    @Override
    public Capabilities getCapabilities() {
        for (final ConfigurationProperties configuration : configurations) {
            final Capabilities capabilities = configuration.getCapabilities();
            if (capabilities != null) {
                return capabilities;
            }
        }
        return null;
    }

    @Override
    public DriverLifecycle getDriverLifecycle() {
        for (final ConfigurationProperties configuration : configurations) {
            final DriverLifecycle driverLifecycle = configuration.getDriverLifecycle();
            if (driverLifecycle != null) {
                return driverLifecycle;
            }
        }
        return null;
    }

    @Override
    public Boolean getDeleteCookies() {
        for (final ConfigurationProperties configuration : configurations) {
            final Boolean deleteCookies = configuration.getDeleteCookies();
            if (deleteCookies != null) {
                return deleteCookies;
            }
        }
        return null;
    }

    @Override
    public String getBaseUrl() {
        for (final ConfigurationProperties configuration : configurations) {
            final String baseUrl = configuration.getBaseUrl();
            if (baseUrl != null) {
                return baseUrl;
            }
        }
        return null;
    }

    @Override
    public Long getPageLoadTimeout() {
        for (final ConfigurationProperties configuration : configurations) {
            final Long pageLoadTimeout = configuration.getPageLoadTimeout();
            if (pageLoadTimeout != null) {
                return pageLoadTimeout;
            }
        }
        return null;
    }

    @Override
    public Long getImplicitlyWait() {
        for (final ConfigurationProperties configuration : configurations) {
            final Long implicitlyWait = configuration.getImplicitlyWait();
            if (implicitlyWait != null) {
                return implicitlyWait;
            }
        }
        return null;
    }

    @Override
    public Long getScriptTimeout() {
        for (final ConfigurationProperties configuration : configurations) {
            final Long scriptTimeout = configuration.getScriptTimeout();
            if (scriptTimeout != null) {
                return scriptTimeout;
            }
        }
        return null;
    }

    @Override
    public Boolean getEventsEnabled() {
        for (final ConfigurationProperties configuration : configurations) {
            final Boolean eventsEnabled = configuration.getEventsEnabled();
            if (eventsEnabled != null) {
                return eventsEnabled;
            }
        }
        return null;
    }

    @Override
    public String getScreenshotPath() {
        for (final ConfigurationProperties configuration : configurations) {
            final String screenshotPath = configuration.getScreenshotPath();
            if (screenshotPath != null) {
                return screenshotPath;
            }
        }
        return null;
    }

    @Override
    public String getHtmlDumpPath() {
        for (final ConfigurationProperties configuration : configurations) {
            final String htmlDumpPath = configuration.getHtmlDumpPath();
            if (htmlDumpPath != null) {
                return htmlDumpPath;
            }
        }
        return null;
    }

    @Override
    public TriggerMode getScreenshotMode() {
        for (final ConfigurationProperties configuration : configurations) {
            final TriggerMode screenshotMode = configuration.getScreenshotMode();
            if (screenshotMode != null) {
                return screenshotMode;
            }
        }
        return null;
    }

    @Override
    public TriggerMode getHtmlDumpMode() {
        for (final ConfigurationProperties configuration : configurations) {
            final TriggerMode htmlDumpMode = configuration.getHtmlDumpMode();
            if (htmlDumpMode != null) {
                return htmlDumpMode;
            }
        }
        return null;
    }

}
