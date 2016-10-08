package org.fluentlenium.configuration;

import org.openqa.selenium.Capabilities;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link Configuration} based on JavaBean properties.
 *
 * @see Configuration
 * @see ConfigurationProperties
 */
public class ProgrammaticConfiguration implements Configuration { // NOPMD TooManyFields
    private Class<? extends ConfigurationFactory> configurationFactory;

    private Class<? extends ConfigurationProperties> configurationDefaults;

    private String webDriverName;

    private String remoteUrl;

    private Capabilities capabilities;

    private DriverLifecycle driverLifecycle;

    private Boolean deleteCookies;

    private String baseUrl;

    private Boolean eventsEnabled;

    private String screenshotPath;

    private String htmlDumpPath;

    private TriggerMode screenshotMode;

    private TriggerMode htmlDumpMode;

    private Long pageLoadTimeout;

    private Long implicitlyWait;

    private Long scriptTimeout;

    private final Map<String, String> custom = new HashMap<>();

    /**
     * Set the configuration defaults.
     *
     * @param configurationDefaults configuration defaults
     */
    public void setConfigurationDefaults(final Class<? extends ConfigurationProperties> configurationDefaults) {
        this.configurationDefaults = configurationDefaults;
    }

    @Override
    public String getWebDriver() {
        return webDriverName;
    }

    @Override
    public void setWebDriver(final String webDriverName) {
        this.webDriverName = webDriverName;
    }

    @Override
    public String getRemoteUrl() {
        return remoteUrl;
    }

    @Override
    public void setRemoteUrl(final String remoteUrl) {
        this.remoteUrl = remoteUrl;
    }

    @Override
    public Capabilities getCapabilities() {
        return capabilities;
    }

    @Override
    public void setCapabilities(final Capabilities capabilities) {
        this.capabilities = capabilities;
    }

    @Override
    public DriverLifecycle getDriverLifecycle() {
        return driverLifecycle;
    }

    @Override
    public void setDriverLifecycle(final DriverLifecycle driverLifecycle) {
        this.driverLifecycle = driverLifecycle;
    }

    @Override
    public Boolean getDeleteCookies() {
        return deleteCookies;
    }

    @Override
    public void setDeleteCookies(final Boolean deleteCookies) {
        this.deleteCookies = deleteCookies;
    }

    @Override
    public Class<? extends ConfigurationFactory> getConfigurationFactory() {
        return configurationFactory;
    }

    @Override
    public void setConfigurationFactory(final Class<? extends ConfigurationFactory> configurationFactory) {
        this.configurationFactory = configurationFactory;
    }

    @Override
    public Class<? extends ConfigurationProperties> getConfigurationDefaults() {
        return configurationDefaults;
    }

    @Override
    public String getBaseUrl() {
        return baseUrl;
    }

    @Override
    public void setBaseUrl(final String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public Long getPageLoadTimeout() {
        return pageLoadTimeout;
    }

    @Override
    public void setPageLoadTimeout(final Long pageLoadTimeout) {
        this.pageLoadTimeout = pageLoadTimeout;
    }

    @Override
    public Long getImplicitlyWait() {
        return implicitlyWait;
    }

    @Override
    public void setImplicitlyWait(final Long implicitlyWait) {
        this.implicitlyWait = implicitlyWait;
    }

    @Override
    public Long getScriptTimeout() {
        return scriptTimeout;
    }

    @Override
    public void setScriptTimeout(final Long scriptTimeout) {
        this.scriptTimeout = scriptTimeout;
    }

    @Override
    public Boolean getEventsEnabled() {
        return eventsEnabled;
    }

    @Override
    public void setEventsEnabled(final Boolean eventsEnabled) {
        this.eventsEnabled = eventsEnabled;
    }

    @Override
    public void setScreenshotPath(final String path) {
        this.screenshotPath = path;
    }

    @Override
    public void setHtmlDumpPath(final String htmlDumpPath) {
        this.htmlDumpPath = htmlDumpPath;
    }

    @Override
    public void setScreenshotMode(final TriggerMode mode) {
        this.screenshotMode = mode;
    }

    @Override
    public TriggerMode getScreenshotMode() {
        return screenshotMode;
    }

    @Override
    public String getScreenshotPath() {
        return screenshotPath;
    }

    @Override
    public String getHtmlDumpPath() {
        return htmlDumpPath;
    }

    @Override
    public void setHtmlDumpMode(final TriggerMode htmlDumpMode) {
        this.htmlDumpMode = htmlDumpMode;
    }

    @Override
    public TriggerMode getHtmlDumpMode() {
        return htmlDumpMode;
    }

    @Override
    public String getCustomProperty(final String propertyName) {
        return custom.get(propertyName);
    }

    @Override
    public void setCustomProperty(final String key, final String value) {
        if (value == null) {
            custom.remove(key);
        } else {
            custom.put(key, value);
        }
    }
}
