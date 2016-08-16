package org.fluentlenium.configuration;

import org.openqa.selenium.WebDriver;

public interface ConfigurationReader {
    enum TriggerMode {ON_FAIL, NEVER}

    /**
     * @return
     * @deprecated Use {@link #getWebDriver()} instead.
     */
    @Deprecated
    WebDriver getDefaultDriver();


    /**
     * Get the name of the {@link WebDriver} to used, as registered in {@link WebDrivers}.
     *
     * @return
     */
    String getWebDriver();

    /**
     * Sets the base URL used to build absolute URL when relative URL is used.
     */
    String getDefaultBaseUrl();

    /**
     * Sets the amount of time to wait for a page load to complete before throwing an error.
     * If the timeout is negative, page loads can be indefinite.
     *
     * @return
     * @see org.openqa.selenium.WebDriver.Timeouts#pageLoadTimeout(long, java.util.concurrent.TimeUnit)
     */
    Long getPageLoadTimeout();

    /**
     * Specifies the amount of time the driver should wait when searching for an element if it is
     * not immediately present.
     *
     * @return
     * @see org.openqa.selenium.WebDriver.Timeouts#implicitlyWait(long, java.util.concurrent.TimeUnit)
     */
    Long getImplicitlyWait();

    /**
     * Sets the amount of time to wait for a page load to complete before throwing an error.
     * If the timeout is negative, page loads can be indefinite.
     *
     * @see org.openqa.selenium.WebDriver.Timeouts#setScriptTimeout(long, java.util.concurrent.TimeUnit)
     */
    Long getScriptTimeout();

    String getScreenshotPath();

    String getHtmlDumpPath();

    TriggerMode getScreenshotMode();

    TriggerMode getHtmlDumpMode();
}
