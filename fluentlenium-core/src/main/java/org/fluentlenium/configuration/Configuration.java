package org.fluentlenium.configuration;

import org.openqa.selenium.WebDriver;

public interface Configuration extends ConfigurationReader {
    /**
     * Set the name of the {@link WebDriver} to used, as registered in {@link WebDrivers}.
     *
     * @param webDriverName
     */
    void setWebDriver(String webDriverName);

    /**
     * Sets the base URL used to build absolute URL when relative URL is used.
     *
     * @param defaultBaseUrl base URL to use.
     */
    void setDefaultBaseUrl(String defaultBaseUrl);

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

    void setScreenshotMode(TriggerMode mode);

    void setHtmlDumpMode(TriggerMode htmlDumpMode);
}
