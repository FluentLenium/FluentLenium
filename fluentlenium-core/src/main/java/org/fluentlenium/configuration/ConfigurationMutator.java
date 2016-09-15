package org.fluentlenium.configuration;

import org.openqa.selenium.Capabilities;

/**
 * Mutation interface of Configuration Properties.
 *
 * @see ConfigurationProperties
 */
public interface ConfigurationMutator {
    /**
     * Sets the value of <pre>webDriver</pre> property.
     *
     * @param webDriver property value
     * @see ConfigurationProperties#getWebDriver()
     */
    void setWebDriver(String webDriver);

    /**
     * Sets the value of <pre>remoteUrl</pre> property.
     *
     * @param remoteUrl property value
     * @see ConfigurationProperties#getRemoteUrl()
     */
    void setRemoteUrl(String remoteUrl);

    /**
     * Sets the value of <pre>capabilities</pre> property.
     *
     * @param capabilities property value
     * @see ConfigurationProperties#getCapabilities()
     */
    void setCapabilities(Capabilities capabilities);

    /**
     * Sets the value of <pre>configurationFactory</pre> property.
     *
     * @param configurationFactory property value
     * @see ConfigurationProperties#getConfigurationFactory()
     */
    void setConfigurationFactory(Class<? extends ConfigurationFactory> configurationFactory);

    /**
     * Sets the value of <pre>driverLifecycle</pre> property.
     *
     * @param driverLifecycle property value
     * @see ConfigurationProperties#getDriverLifecycle()
     */
    void setDriverLifecycle(ConfigurationProperties.DriverLifecycle driverLifecycle);

    /**
     * Sets the value of <pre>deleteCookies</pre> property.
     *
     * @param deleteCookies property value
     * @see ConfigurationProperties#getDeleteCookies()
     */
    void setDeleteCookies(Boolean deleteCookies);


    /**
     * Sets the value of <pre>baseUrl</pre> property.
     *
     * @param baseUrl property value
     * @see ConfigurationProperties#getBaseUrl() ()
     */
    void setBaseUrl(String baseUrl);

    /**
     * Sets the value of <pre>pageLoadTimeout</pre> property.
     *
     * @param pageLoadTimeout property value
     * @see ConfigurationProperties#getPageLoadTimeout()
     */
    void setPageLoadTimeout(Long pageLoadTimeout);

    /**
     * Sets the value of <pre>implicitlyWait</pre> property.
     *
     * @param implicitlyWait property value
     * @see ConfigurationProperties#getImplicitlyWait()
     */
    void setImplicitlyWait(Long implicitlyWait);

    /**
     * Sets the value of <pre>scriptTimeout</pre> property.
     *
     * @param scriptTimeout property value
     * @see ConfigurationProperties#getScriptTimeout()
     */
    void setScriptTimeout(Long scriptTimeout);

    /**
     * Sets the value of <pre>eventsEnabled</pre> property.
     *
     * @param eventsEnabled property value
     */
    void setEventsEnabled(Boolean eventsEnabled);

    /**
     * Sets the value of <pre>screenshotPath</pre> property.
     *
     * @param screenshotPath property value
     * @see ConfigurationProperties#getScreenshotPath()
     */
    void setScreenshotPath(String screenshotPath);

    /**
     * Sets the value of <pre>screenshotMode</pre> property.
     *
     * @param screenshotMode property value
     * @see ConfigurationProperties#getScreenshotMode()
     */
    void setScreenshotMode(ConfigurationProperties.TriggerMode screenshotMode);

    /**
     * Sets the value of <pre>htmlDumpPath</pre> property.
     *
     * @param htmlDumpPath property value
     * @see ConfigurationProperties#getHtmlDumpPath()
     */
    void setHtmlDumpPath(String htmlDumpPath);

    /**
     * Sets the value of <pre>htmlDumpMode</pre> property.
     *
     * @param htmlDumpMode property value
     * @see ConfigurationProperties#getHtmlDumpMode()
     */
    void setHtmlDumpMode(ConfigurationProperties.TriggerMode htmlDumpMode);
}

