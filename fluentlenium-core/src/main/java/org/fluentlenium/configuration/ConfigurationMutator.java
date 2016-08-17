package org.fluentlenium.configuration;

/**
 * Mutation interface of Configuration Properties.
 *
 * @see ConfigurationProperties
 */
public interface ConfigurationMutator {
    /**
     * Sets the value of <pre>webDriver</pre> property.
     *
     * @param webDriver
     * @see ConfigurationProperties#getWebDriver()
     */
    void setWebDriver(String webDriver);

    /**
     * Sets the value of <pre>configurationFactory</pre> property.
     *
     * @param configurationFactory
     * @see ConfigurationProperties#getConfigurationFactory()
     */
    void setConfigurationFactory(Class<? extends ConfigurationFactory> configurationFactory);

    /**
     * Sets the value of <pre>baseUrl</pre> property.
     *
     * @param baseUrl
     * @see ConfigurationProperties#getBaseUrl() ()
     */
    void setBaseUrl(String baseUrl);

    /**
     * Sets the value of <pre>pageLoadTimeout</pre> property.
     *
     * @param pageLoadTimeout
     * @see ConfigurationProperties#getPageLoadTimeout()
     */
    void setPageLoadTimeout(Long pageLoadTimeout);

    /**
     * Sets the value of <pre>implicitlyWait</pre> property.
     *
     * @param implicitlyWait
     * @see ConfigurationProperties#getImplicitlyWait()
     */
    void setImplicitlyWait(Long implicitlyWait);

    /**
     * Sets the value of <pre>scriptTimeout</pre> property.
     *
     * @param scriptTimeout
     * @see ConfigurationProperties#getScriptTimeout()
     */
    void setScriptTimeout(Long scriptTimeout);

    /**
     * Sets the value of <pre>screenshotPath</pre> property.
     *
     * @param screenshotPath
     * @see ConfigurationProperties#getScreenshotPath()
     */
    void setScreenshotPath(String screenshotPath);

    /**
     * Sets the value of <pre>screenshotMode</pre> property.
     *
     * @param screenshotMode
     * @see ConfigurationProperties#getScreenshotMode()
     */
    void setScreenshotMode(ConfigurationProperties.TriggerMode screenshotMode);

    /**
     * Sets the value of <pre>htmlDumpPath</pre> property.
     *
     * @param htmlDumpPath
     * @see ConfigurationProperties#getHtmlDumpPath()
     */
    void setHtmlDumpPath(String htmlDumpPath);

    /**
     * Sets the value of <pre>htmlDumpMode</pre> property.
     *
     * @param htmlDumpMode
     * @see ConfigurationProperties#getHtmlDumpMode()
     */
    void setHtmlDumpMode(ConfigurationProperties.TriggerMode htmlDumpMode);
}

