package org.fluentlenium.configuration;

import lombok.experimental.Delegate;
import org.openqa.selenium.Capabilities;

import java.util.Arrays;
import java.util.List;

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

    /**
     * Creates a new composed configuration
     *
     * @param writableConfiguration writable configuration
     * @param configurations        readable configurations
     */
    public ComposedConfiguration(ConfigurationMutator writableConfiguration, ConfigurationProperties... configurations) {
        this.writableConfiguration = writableConfiguration;
        this.configurations = configurations;
        if (writableConfiguration instanceof BaseConfiguration) {
            ((BaseConfiguration) writableConfiguration).setGlobalConfiguration(this);
        }
    }

    /**
     * Get configurations.
     *
     * @return configurations
     */
    List<ConfigurationProperties> getConfigurations() {
        return Arrays.asList(configurations);
    }

    /**
     * Get writable configuration.
     *
     * @return writable configuration
     */
    ConfigurationMutator getWritableConfiguration() {
        return writableConfiguration;
    }

    @Override
    public Class<? extends ConfigurationFactory> getConfigurationFactory() {
        for (ConfigurationProperties configuration : configurations) {
            Class<? extends ConfigurationFactory> configurationFactory = configuration.getConfigurationFactory();
            if (configurationFactory != null) {
                return configurationFactory;
            }
        }
        return null;
    }

    @Override
    public Class<? extends ConfigurationProperties> getConfigurationDefaults() {
        for (ConfigurationProperties configuration : configurations) {
            Class<? extends ConfigurationProperties> configurationDefaults = configuration.getConfigurationDefaults();
            if (configurationDefaults != null) {
                return configurationDefaults;
            }
        }
        return null;
    }

    @Override
    public String getWebDriver() {
        for (ConfigurationProperties configuration : configurations) {
            String webDriver = configuration.getWebDriver();
            if (webDriver != null) {
                return webDriver;
            }
        }
        return null;
    }

    @Override
    public String getRemoteUrl() {
        for (ConfigurationProperties configuration : configurations) {
            String remoteUrl = configuration.getRemoteUrl();
            if (remoteUrl != null) {
                return remoteUrl;
            }
        }
        return null;
    }

    @Override
    public Capabilities getCapabilities() {
        for (ConfigurationProperties configuration : configurations) {
            Capabilities capabilities = configuration.getCapabilities();
            if (capabilities != null) {
                return capabilities;
            }
        }
        return null;
    }

    @Override
    public DriverLifecycle getDriverLifecycle() {
        for (ConfigurationProperties configuration : configurations) {
            DriverLifecycle driverLifecycle = configuration.getDriverLifecycle();
            if (driverLifecycle != null) {
                return driverLifecycle;
            }
        }
        return null;
    }

    @Override
    public Long getBrowserTimeout() {
        for (ConfigurationProperties configuration : configurations) {
            Long browserTimeout = configuration.getBrowserTimeout();
            if (browserTimeout != null) {
                return browserTimeout;
            }
        }
        return null;
    }

    @Override
    public Integer getBrowserTimeoutRetries() {
        for (ConfigurationProperties configuration : configurations) {
            Integer browserTimeoutRetries = configuration.getBrowserTimeoutRetries();
            if (browserTimeoutRetries != null) {
                return browserTimeoutRetries;
            }
        }
        return null;
    }

    @Override
    public Boolean getDeleteCookies() {
        for (ConfigurationProperties configuration : configurations) {
            Boolean deleteCookies = configuration.getDeleteCookies();
            if (deleteCookies != null) {
                return deleteCookies;
            }
        }
        return null;
    }

    @Override
    public String getBaseUrl() {
        for (ConfigurationProperties configuration : configurations) {
            String baseUrl = configuration.getBaseUrl();
            if (baseUrl != null) {
                return baseUrl;
            }
        }
        return null;
    }

    @Override
    public Long getPageLoadTimeout() {
        for (ConfigurationProperties configuration : configurations) {
            Long pageLoadTimeout = configuration.getPageLoadTimeout();
            if (pageLoadTimeout != null) {
                return pageLoadTimeout;
            }
        }
        return null;
    }

    @Override
    public Long getImplicitlyWait() {
        for (ConfigurationProperties configuration : configurations) {
            Long implicitlyWait = configuration.getImplicitlyWait();
            if (implicitlyWait != null) {
                return implicitlyWait;
            }
        }
        return null;
    }

    @Override
    public Long getScriptTimeout() {
        for (ConfigurationProperties configuration : configurations) {
            Long scriptTimeout = configuration.getScriptTimeout();
            if (scriptTimeout != null) {
                return scriptTimeout;
            }
        }
        return null;
    }

    @Override
    public Long getAwaitAtMost() {
        for (ConfigurationProperties configuration : configurations) {
            Long awaitAtMost = configuration.getAwaitAtMost();
            if (awaitAtMost != null) {
                return awaitAtMost;
            }
        }
        return null;
    }

    @Override
    public Long getAwaitPollingEvery() {
        for (ConfigurationProperties configuration : configurations) {
            Long awaitPollingEvery = configuration.getAwaitPollingEvery();
            if (awaitPollingEvery != null) {
                return awaitPollingEvery;
            }
        }
        return null;
    }

    @Override
    public Boolean getEventsEnabled() {
        for (ConfigurationProperties configuration : configurations) {
            Boolean eventsEnabled = configuration.getEventsEnabled();
            if (eventsEnabled != null) {
                return eventsEnabled;
            }
        }
        return null;
    }

    @Override
    public String getScreenshotPath() {
        for (ConfigurationProperties configuration : configurations) {
            String screenshotPath = configuration.getScreenshotPath();
            if (screenshotPath != null) {
                return screenshotPath;
            }
        }
        return null;
    }

    @Override
    public String getHtmlDumpPath() {
        for (ConfigurationProperties configuration : configurations) {
            String htmlDumpPath = configuration.getHtmlDumpPath();
            if (htmlDumpPath != null) {
                return htmlDumpPath;
            }
        }
        return null;
    }

    @Override
    public TriggerMode getScreenshotMode() {
        for (ConfigurationProperties configuration : configurations) {
            TriggerMode screenshotMode = configuration.getScreenshotMode();
            if (screenshotMode != null) {
                return screenshotMode;
            }
        }
        return null;
    }

    @Override
    public TriggerMode getHtmlDumpMode() {
        for (ConfigurationProperties configuration : configurations) {
            TriggerMode htmlDumpMode = configuration.getHtmlDumpMode();
            if (htmlDumpMode != null) {
                return htmlDumpMode;
            }
        }
        return null;
    }

    @Override
    public String getCustomProperty(String propertyName) {
        for (ConfigurationProperties configuration : configurations) {
            String value = configuration.getCustomProperty(propertyName);
            if (value != null) {
                return value;
            }
        }
        return null;
    }
}
