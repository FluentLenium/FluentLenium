package org.fluentlenium.configuration;

public class DefaultConfiguration implements ConfigurationRead {
    @Override
    public Class<? extends ConfigurationFactory> getConfigurationFactory() {
        return DefaultConfigurationFactory.class;
    }

    @Override
    public String getWebDriver() {
        return "firefox";
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
    public Long getScriptTimeout() {
        return null;
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
}
