package io.fluentlenium.configuration;

import org.openqa.selenium.Capabilities;

/**
 * Default configuration implementation.
 *
 * @see ConfigurationProperties
 */
public class ConfigurationDefaults extends BaseConfiguration implements ConfigurationProperties {
    @Override
    protected ConfigurationProperties getGlobalConfiguration() { // NOPMD UselessOverridingMethod
        // This class can be extended by end-user, so it has to be protected.
        return super.getGlobalConfiguration();
    }

    @Override
    public Class<? extends ConfigurationFactory> getConfigurationFactory() {
        return null;
    }

    @Override
    public Class<? extends ConfigurationProperties> getConfigurationDefaults() {
        return ConfigurationDefaults.class;
    }

    @Override
    public String getWebDriver() {
        return null;
    }

    @Override
    public String getRemoteUrl() {
        return null;
    }

    @Override
    public Capabilities getCapabilities() {
        return null;
    }

    @Override
    public DriverLifecycle getDriverLifecycle() {
        return DriverLifecycle.METHOD;
    }

    @Override
    public Long getBrowserTimeout() {
        return 60000L;
    }

    @Override
    public Integer getBrowserTimeoutRetries() {
        return 2;
    }

    @Override
    public Boolean getDeleteCookies() {
        return false;
    }

    @Override
    public String getBaseUrl() {
        return null;
    }

    @Override
    public Long getPageLoadTimeout() {
        return null;
    }

    @Override
    public Long getImplicitlyWait() {
        return null;
    }

    @Override
    public Long getAwaitAtMost() {
        return null;
    }

    @Override
    public Long getAwaitPollingEvery() {
        return null;
    }

    @Override
    public Long getScriptTimeout() {
        return null;
    }

    @Override
    public Boolean getEventsEnabled() {
        return true;
    }

    @Override
    public String getScreenshotPath() {
        return null;
    }

    @Override
    public String getHtmlDumpPath() {
        return null;
    }

    @Override
    public TriggerMode getScreenshotMode() {
        return null;
    }

    @Override
    public TriggerMode getHtmlDumpMode() {
        return null;
    }

    @Override
    public String getCustomProperty(String propertyName) {
        return null;
    }
}
