package org.fluentlenium.configuration;

public class ProgrammaticConfiguration implements Configuration {
    private Class<? extends ConfigurationFactory> configurationFactory;

    private String webDriverName;

    private String baseUrl;

    private String screenshotPath;

    private String htmlDumpPath;

    private TriggerMode screenshotMode;

    private TriggerMode htmlDumpMode;

    private Long pageLoadTimeout;

    private Long implicitlyWait;

    private Long scriptTimeout;

    @Override
    public String getWebDriver() {
        return webDriverName;
    }

    @Override
    public void setWebDriver(String webDriverName) {
        this.webDriverName = webDriverName;
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
    public void setScreenshotPath(String path) {
        this.screenshotPath = path;
    }

    @Override
    public void setHtmlDumpPath(String htmlDumpPath) {
        this.htmlDumpPath = htmlDumpPath;
    }

    @Override
    public void setScreenshotMode(TriggerMode mode) {
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
    public void setHtmlDumpMode(TriggerMode htmlDumpMode) {
        this.htmlDumpMode = htmlDumpMode;
    }

    @Override
    public TriggerMode getHtmlDumpMode() {
        return htmlDumpMode;
    }
}
