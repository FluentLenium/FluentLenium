package org.fluentlenium.configuration;

import org.openqa.selenium.WebDriver;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface FluentConfiguration {

    /**
     * Get the name of the {@link WebDriver} to used, as registered in {@link WebDrivers}.
     *
     * @return
     */
    String webDriver() default "";

    /**
     * Get the {@link ConfigurationFactory} lcass
     *
     * @return
     */
    Class<? extends ConfigurationFactory> configurationFactory() default DefaultConfigurationFactory.class;

    /**
     * Sets the base URL used to build absolute URL when relative URL is used.
     */
    String baseUrl() default "";

    /**
     * Sets the amount of time to wait for a page load to complete before throwing an error.
     * If the timeout is negative, page loads can be indefinite.
     *
     * @return
     * @see org.openqa.selenium.WebDriver.Timeouts#pageLoadTimeout(long, java.util.concurrent.TimeUnit)
     */
    long pageLoadTimeout() default -1;

    /**
     * Specifies the amount of time the driver should wait when searching for an element if it is
     * not immediately present.
     *
     * @return
     * @see org.openqa.selenium.WebDriver.Timeouts#implicitlyWait(long, java.util.concurrent.TimeUnit)
     */
    long implicitlyWait() default -1;

    /**
     * Sets the amount of time to wait for a page load to complete before throwing an error.
     * If the timeout is negative, page loads can be indefinite.
     *
     * @see org.openqa.selenium.WebDriver.Timeouts#setScriptTimeout(long, java.util.concurrent.TimeUnit)
     */
    long scriptTimeout() default -1;

    String screenshotPath() default "";

    String htmlDumpPath() default "";

    ConfigurationRead.TriggerMode screenshotMode() default ConfigurationRead.TriggerMode.UNDEFINED;

    ConfigurationRead.TriggerMode htmlDumpMode() default ConfigurationRead.TriggerMode.UNDEFINED;
}
