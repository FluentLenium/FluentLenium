package org.fluentlenium.configuration;

import org.openqa.selenium.WebDriver;

public interface ConfigurationWrite {
    /**
     * Set the name of the {@link WebDriver} to used, as registered in {@link WebDrivers}.
     *
     * @param webDriverName
     */
    void setWebDriver(String webDriverName);

    /**
     * Set the configuration factory.
     *
     * @param configurationFactory
     */
    void setConfigurationFactory(Class<? extends ConfigurationFactory> configurationFactory);

    /**
     * Sets the base URL used to build absolute URL when relative URL is used.
     *
     * @param defaultBaseUrl base URL to use.
     */
    void setBaseUrl(String defaultBaseUrl);

    /**
     * Sets the amount of time to wait for a page load to complete before throwing an error.
     * If the timeout is negative, page loads can be indefinite.
     *
     * @see org.openqa.selenium.WebDriver.Timeouts#pageLoadTimeout(long, java.util.concurrent.TimeUnit)
     */
    void setPageLoadTimeout(Long pageLoadTimeout);

    /**
     * Specifies the amount of time the driver should wait when searching for an element if it is
     * not immediately present.
     *
     * @see org.openqa.selenium.WebDriver.Timeouts#implicitlyWait(long, java.util.concurrent.TimeUnit)
     */
    void setImplicitlyWait(Long implicitlyWait);

    /**
     * Sets the amount of time to wait for a page load to complete before throwing an error.
     * If the timeout is negative, page loads can be indefinite.
     *
     * @see org.openqa.selenium.WebDriver.Timeouts#setScriptTimeout(long, java.util.concurrent.TimeUnit)
     */
    void setScriptTimeout(Long scriptTimeout);

    void setScreenshotPath(String path);

    void setHtmlDumpPath(String htmlDumpPath);

    void setScreenshotMode(ConfigurationRead.TriggerMode mode);

    void setHtmlDumpMode(ConfigurationRead.TriggerMode htmlDumpMode);
}

