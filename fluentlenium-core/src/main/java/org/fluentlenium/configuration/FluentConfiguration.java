package org.fluentlenium.configuration;

import org.openqa.selenium.WebDriver;

import javax.annotation.processing.SupportedAnnotationTypes;
import java.lang.annotation.ElementType;
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
public @interface FluentConfiguration {
    enum BooleanValue {
        TRUE(true),
        FALSE(false),
        DEFAULT(null);

        private final Boolean value;

        BooleanValue(Boolean value) {
            this.value = value;
        }

        Boolean asBoolean() {
            return this.value;
        }

    }

    /**
     * @see ConfigurationProperties#getConfigurationFactory()
     */
    Class<? extends ConfigurationFactory> configurationFactory() default DefaultConfigurationFactory.class;


    /**
     * @see ConfigurationProperties#getConfigurationDefaults()
     */
    Class<? extends ConfigurationProperties> configurationDefaults() default ConfigurationDefaults.class;

    /**
     * @see ConfigurationProperties#getWebDriver()
     */
    String webDriver() default "";

    /**
     * @see ConfigurationProperties#getBaseUrl()
     */
    String baseUrl() default "";

    /**
     * @see ConfigurationProperties#getPageLoadTimeout()
     */
    long pageLoadTimeout() default -1;

    /**
     * @see ConfigurationProperties#getImplicitlyWait()
     */
    long implicitlyWait() default -1;

    /**
     * @see ConfigurationProperties#getScriptTimeout()
     */
    long scriptTimeout() default -1;

    /**
     * @see ConfigurationProperties#getEventsEnabled()
     */
    BooleanValue eventsEnabled() default BooleanValue.DEFAULT;

    /**
     * @see ConfigurationProperties#getScreenshotPath()
     */
    String screenshotPath() default "";

    /**
     * @see ConfigurationProperties#getHtmlDumpPath()
     */
    String htmlDumpPath() default "";

    /**
     * @see ConfigurationProperties#getScreenshotMode()
     */
    ConfigurationProperties.TriggerMode screenshotMode() default ConfigurationProperties.TriggerMode.DEFAULT;

    /**
     * @see ConfigurationProperties#getHtmlDumpMode()
     */
    ConfigurationProperties.TriggerMode htmlDumpMode() default ConfigurationProperties.TriggerMode.DEFAULT;
}
