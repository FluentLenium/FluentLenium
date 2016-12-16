package org.fluentlenium.configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Configure a FluentLenium test class with this annotation.
 *
 * @see ConfigurationProperties
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface FluentConfiguration {
    /**
     * Boolean value used in  annotation that can has a third DEFAULT value equivalent to null.
     */
    enum BooleanValue {
        TRUE(true), FALSE(false), DEFAULT(null);

        private final Boolean value;

        /**
         * Creates a new boolean value.
         *
         * @param value boolean value
         */
        BooleanValue(Boolean value) {
            this.value = value;
        }

        /**
         * Get this Boolean value as a {@link Boolean}
         *
         * @return boolean value
         */
        Boolean asBoolean() {
            return value;
        }

    }

    /**
     * <i>configurationFactory</i> property.
     *
     * @return configurationFactory
     * @see ConfigurationProperties#getConfigurationFactory()
     */
    Class<? extends ConfigurationFactory> configurationFactory() default DefaultConfigurationFactory.class;

    /**
     * <i>configurationDefaults</i> property.
     *
     * @return configurationDefaults
     * @see ConfigurationProperties#getConfigurationDefaults()
     */
    Class<? extends ConfigurationProperties> configurationDefaults() default ConfigurationDefaults.class;

    /**
     * <i>webDriver</i> property.
     *
     * @return webDriver
     * @see ConfigurationProperties#getWebDriver()
     */
    String webDriver() default "";

    /**
     * <i>remoteUrl</i> property.
     *
     * @return remoteUrl
     * @see ConfigurationProperties#getRemoteUrl()
     */
    String remoteUrl() default "";

    /**
     * <i>capabilities</i> property.
     *
     * @return capabilities
     * @see ConfigurationProperties#getCapabilities()
     */
    String capabilities() default "";

    /**
     * <i>driverLifecycle</i> property.
     *
     * @return driverLifecycle
     * @see ConfigurationProperties#getDriverLifecycle()
     */
    ConfigurationProperties.DriverLifecycle driverLifecycle() default ConfigurationProperties.DriverLifecycle.DEFAULT;

    /**
     * <i>deleteCookies</i> property.
     *
     * @return deleteCookies
     * @see ConfigurationProperties#getDeleteCookies()
     */
    BooleanValue deleteCookies() default BooleanValue.DEFAULT;

    /**
     * <i>baseUrl</i> property.
     *
     * @return baseUrl
     * @see ConfigurationProperties#getBaseUrl()
     */
    String baseUrl() default "";

    /**
     * <i>pageLoadTimeout</i> property.
     *
     * @return pageLoadTimeout
     * @see ConfigurationProperties#getPageLoadTimeout()
     */
    long pageLoadTimeout() default -1;

    /**
     * <i>implicitlyWait</i> property.
     *
     * @return implicitlyWait
     * @see ConfigurationProperties#getImplicitlyWait()
     */
    long implicitlyWait() default -1;

    /**
     * <i>scriptTimeout</i> property.
     *
     * @return scriptTimeout
     * @see ConfigurationProperties#getScriptTimeout()
     */
    long scriptTimeout() default -1;

    /**
     * <i>awaitAtMost</i> property.
     *
     * @return awaitAtMost
     * @see ConfigurationProperties#getAwaitAtMost()
     */
    long awaitAtMost() default -1;

    /**
     * <i>awaitPollingEvery</i> property.
     *
     * @return awaitPollingEvery
     * @see ConfigurationProperties#getAwaitPollingEvery() ()
     */
    long awaitPollingEvery() default -1;

    /**
     * <i>eventsEnabled</i> property.
     *
     * @return eventsEnabled
     * @see ConfigurationProperties#getEventsEnabled()
     */
    BooleanValue eventsEnabled() default BooleanValue.DEFAULT;

    /**
     * <i>screenshotPath</i> property.
     *
     * @return screenshotPath
     * @see ConfigurationProperties#getScreenshotPath()
     */
    String screenshotPath() default "";

    /**
     * <i>htmlDumpPath</i> property.
     *
     * @return htmlDumpPath
     * @see ConfigurationProperties#getHtmlDumpPath()
     */
    String htmlDumpPath() default "";

    /**
     * <i>screenshotMode</i> property.
     *
     * @return screenshotMode
     * @see ConfigurationProperties#getScreenshotMode()
     */
    ConfigurationProperties.TriggerMode screenshotMode() default ConfigurationProperties.TriggerMode.DEFAULT;

    /**
     * <i>htmlDumpMode</i> property.
     *
     * @return htmlDumpMode
     * @see ConfigurationProperties#getHtmlDumpMode()
     */
    ConfigurationProperties.TriggerMode htmlDumpMode() default ConfigurationProperties.TriggerMode.DEFAULT;

    /**
     * Custom properties.
     *
     * @return array of CustomProperty annotations
     * @see ConfigurationProperties#getCustomProperty(String)
     */
    CustomProperty[] custom() default {};
}
