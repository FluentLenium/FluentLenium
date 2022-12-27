package io.fluentlenium.configuration;

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

    private Long awaitAtMost;

    private Long awaitPollingEvery;

    private Long browserTimeout;

    private Integer browserTimeoutRetries;

    private final Map<String, String> custom = new HashMap<>();

    /**
     * Set the configuration defaults.
     *
     * @param configurationDefaults configuration defaults
     */
    public void setConfigurationDefaults(Class<? extends ConfigurationProperties> configurationDefaults) {
        this.configurationDefaults = configurationDefaults;
    }

    @Override
    public String getWebDriver() {
        return webDriverName;
    }

    @Override
    public void setWebDriver(String webDriverName) {
        this.webDriverName = webDriverName;
    }

    @Override
    public String getRemoteUrl() {
        return remoteUrl;
    }

    @Override
    public void setRemoteUrl(String remoteUrl) {
        this.remoteUrl = remoteUrl;
    }

    @Override
    public Capabilities getCapabilities() {
        return capabilities;
    }

    @Override
    public void setCapabilities(Capabilities capabilities) {
        this.capabilities = capabilities;
    }

    @Override
    public DriverLifecycle getDriverLifecycle() {
        return driverLifecycle;
    }

    @Override
    public Long getBrowserTimeout() {
        return browserTimeout;
    }

    @Override
    public Integer getBrowserTimeoutRetries() {
        return browserTimeoutRetries;
    }

    @Override
    public void setBrowserTimeout(Long timeout) {
        this.browserTimeout = timeout;
    }

    @Override
    public void setBrowserTimeoutRetries(Integer retriesNumber) {
        this.browserTimeoutRetries = retriesNumber;
    }

    @Override
    public void setDriverLifecycle(DriverLifecycle driverLifecycle) {
        this.driverLifecycle = driverLifecycle;
    }

    @Override
    public Boolean getDeleteCookies() {
        return deleteCookies;
    }

    @Override
    public void setDeleteCookies(Boolean deleteCookies) {
        this.deleteCookies = deleteCookies;
    }

    @Override
    public Class<? extends ConfigurationFactory> getConfigurationFactory() {
        return configurationFactory;
    }

    @Override
    public void setConfigurationFactory(Class<? extends ConfigurationFactory> configurationFactory) {
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
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public Long getPageLoadTimeout() {
        return pageLoadTimeout;
    }

    @Override
    public void setPageLoadTimeout(Long pageLoadTimeout) {
        this.pageLoadTimeout = pageLoadTimeout;
    }

    @Override
    public Long getImplicitlyWait() {
        return implicitlyWait;
    }

    @Override
    public void setImplicitlyWait(Long implicitlyWait) {
        this.implicitlyWait = implicitlyWait;
    }

    @Override
    public Long getScriptTimeout() {
        return scriptTimeout;
    }

    @Override
    public void setScriptTimeout(Long scriptTimeout) {
        this.scriptTimeout = scriptTimeout;
    }

    @Override
    public Long getAwaitAtMost() {
        return awaitAtMost;
    }

    @Override
    public void setAwaitAtMost(Long awaitAtMost) {
        this.awaitAtMost = awaitAtMost;
    }

    @Override
    public Long getAwaitPollingEvery() {
        return awaitPollingEvery;
    }

    @Override
    public void setAwaitPollingEvery(Long awaitPollingEvery) {
        this.awaitPollingEvery = awaitPollingEvery;
    }

    @Override
    public Boolean getEventsEnabled() {
        return eventsEnabled;
    }

    @Override
    public void setEventsEnabled(Boolean eventsEnabled) {
        this.eventsEnabled = eventsEnabled;
    }

    @Override
    public void setScreenshotPath(String path) {
        screenshotPath = path;
    }

    @Override
    public void setHtmlDumpPath(String htmlDumpPath) {
        this.htmlDumpPath = htmlDumpPath;
    }

    @Override
    public void setScreenshotMode(TriggerMode mode) {
        screenshotMode = mode;
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
    public void setHtmlDumpMode(TriggerMode htmlDumpMode) {
        this.htmlDumpMode = htmlDumpMode;
    }

    @Override
    public TriggerMode getHtmlDumpMode() {
        return htmlDumpMode;
    }

    @Override
    public String getCustomProperty(String propertyName) {
        return custom.get(propertyName);
    }

    @Override
    public void setCustomProperty(String key, String value) {
        if (value == null) {
            custom.remove(key);
        } else {
            custom.put(key, value);
        }
    }
}
