package org.fluentlenium.configuration;

import org.openqa.selenium.Capabilities;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

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
        return getConfig(ConfigurationProperties::getConfigurationFactory);
    }

    @Override
    public Class<? extends ConfigurationProperties> getConfigurationDefaults() {
        return getConfig(ConfigurationProperties::getConfigurationDefaults);
    }

    @Override
    public String getWebDriver() {
        return getConfig(ConfigurationProperties::getWebDriver);
    }

    @Override
    public String getRemoteUrl() {
        return getConfig(ConfigurationProperties::getRemoteUrl);
    }

    @Override
    public Capabilities getCapabilities() {
        return getConfig(ConfigurationProperties::getCapabilities);
    }

    @Override
    public DriverLifecycle getDriverLifecycle() {
        return getConfig(ConfigurationProperties::getDriverLifecycle);
    }

    @Override
    public Long getBrowserTimeout() {
        return getConfig(ConfigurationProperties::getBrowserTimeout);
    }

    @Override
    public Integer getBrowserTimeoutRetries() {
        return getConfig(ConfigurationProperties::getBrowserTimeoutRetries);
    }

    @Override
    public Boolean getDeleteCookies() {
        return getConfig(ConfigurationProperties::getDeleteCookies);
    }

    @Override
    public String getBaseUrl() {
        return getConfig(ConfigurationProperties::getBaseUrl);
    }

    @Override
    public Long getPageLoadTimeout() {
        return getConfig(ConfigurationProperties::getPageLoadTimeout);
    }

    @Override
    public Long getImplicitlyWait() {
        return getConfig(ConfigurationProperties::getImplicitlyWait);
    }

    @Override
    public Long getScriptTimeout() {
        return getConfig(ConfigurationProperties::getScriptTimeout);
    }

    @Override
    public Long getAwaitAtMost() {
        return getConfig(ConfigurationProperties::getAwaitAtMost);
    }

    @Override
    public Long getAwaitPollingEvery() {
        return getConfig(ConfigurationProperties::getAwaitPollingEvery);
    }

    @Override
    public Boolean getEventsEnabled() {
        return getConfig(ConfigurationProperties::getEventsEnabled);
    }

    @Override
    public String getScreenshotPath() {
        return getConfig(ConfigurationProperties::getScreenshotPath);
    }

    @Override
    public String getHtmlDumpPath() {
        return getConfig(ConfigurationProperties::getHtmlDumpPath);
    }

    @Override
    public TriggerMode getScreenshotMode() {
        return getConfig(ConfigurationProperties::getScreenshotMode);
    }

    @Override
    public TriggerMode getHtmlDumpMode() {
        return getConfig(ConfigurationProperties::getHtmlDumpMode);
    }

    @Override
    public String getCustomProperty(String propertyName) {
        return getConfig(configuration -> configuration.getCustomProperty(propertyName));
    }

    private <T> T getConfig(Function<ConfigurationProperties, T> configProvider) {
        return Arrays.stream(configurations)
                .map(configProvider)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    public void setConfigurationFactory(Class<? extends ConfigurationFactory> configurationFactory) {
        getWritableConfiguration().setConfigurationFactory(configurationFactory);
    }

    public void setDeleteCookies(Boolean deleteCookies) {
        getWritableConfiguration().setDeleteCookies(deleteCookies);
    }

    public void setCustomProperty(String key, String value) {
        getWritableConfiguration().setCustomProperty(key, value);
    }

    public void setBaseUrl(String baseUrl) {
        getWritableConfiguration().setBaseUrl(baseUrl);
    }

    public void setWebDriver(String webDriver) {
        getWritableConfiguration().setWebDriver(webDriver);
    }

    public void setPageLoadTimeout(Long pageLoadTimeout) {
        getWritableConfiguration().setPageLoadTimeout(pageLoadTimeout);
    }

    public void setHtmlDumpMode(TriggerMode htmlDumpMode) {
        getWritableConfiguration().setHtmlDumpMode(htmlDumpMode);
    }

    public void setScreenshotPath(String screenshotPath) {
        getWritableConfiguration().setScreenshotPath(screenshotPath);
    }

    public void setBrowserTimeoutRetries(Integer retriesNumber) {
        getWritableConfiguration().setBrowserTimeoutRetries(retriesNumber);
    }

    public void setRemoteUrl(String remoteUrl) {
        getWritableConfiguration().setRemoteUrl(remoteUrl);
    }

    public void setImplicitlyWait(Long implicitlyWait) {
        getWritableConfiguration().setImplicitlyWait(implicitlyWait);
    }

    public void setDriverLifecycle(DriverLifecycle driverLifecycle) {
        getWritableConfiguration().setDriverLifecycle(driverLifecycle);
    }

    public void setAwaitPollingEvery(Long awaitPollingEvery) {
        getWritableConfiguration().setAwaitPollingEvery(awaitPollingEvery);
    }

    public void setCapabilities(Capabilities capabilities) {
        getWritableConfiguration().setCapabilities(capabilities);
    }

    public void setScreenshotMode(TriggerMode screenshotMode) {
        getWritableConfiguration().setScreenshotMode(screenshotMode);
    }

    public void setHtmlDumpPath(String htmlDumpPath) {
        getWritableConfiguration().setHtmlDumpPath(htmlDumpPath);
    }

    public void setAwaitAtMost(Long awaitAtMost) {
        getWritableConfiguration().setAwaitAtMost(awaitAtMost);
    }

    public void setBrowserTimeout(Long timeout) {
        getWritableConfiguration().setBrowserTimeout(timeout);
    }

    public void setScriptTimeout(Long scriptTimeout) {
        getWritableConfiguration().setScriptTimeout(scriptTimeout);
    }

    public void setEventsEnabled(Boolean eventsEnabled) {
        getWritableConfiguration().setEventsEnabled(eventsEnabled);
    }
}
