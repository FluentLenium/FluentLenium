package org.fluentlenium.configuration;

import org.openqa.selenium.WebDriver;

public interface ConfigurationRead {
    enum TriggerMode {ON_FAIL, NEVER, UNDEFINED}

    /**
     * Get the class name of the {@link ConfigurationFactory}
     *
     * @return
     */
    Class<? extends ConfigurationFactory> getConfigurationFactory();

    /**
     * Get the name of the {@link WebDriver} to used, as registered in {@link WebDrivers}.
     *
     * @return
     */
    String getWebDriver();

    /**
     * Sets the base URL used to build absolute URL when relative URL is used.
     */
    String getBaseUrl();

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
