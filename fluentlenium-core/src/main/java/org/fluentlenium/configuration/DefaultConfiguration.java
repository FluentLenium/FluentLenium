package org.fluentlenium.configuration;

import org.openqa.selenium.WebDriver;

public class DefaultConfiguration implements ConfigurationRead {
    @Override
    public WebDriver getDefaultDriver() {
        return null;
    }

    @Override
    public Class<? extends ConfigurationFactory> getConfigurationFactory() {
        return DefaultConfigurationFactory.class;
    }

    @Override
    public String getWebDriver() {
        return "firefox";
    }

    @Override
    public String getDefaultBaseUrl() {
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
