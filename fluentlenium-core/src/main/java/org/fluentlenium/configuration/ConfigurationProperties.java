package org.fluentlenium.configuration;

import org.fluentlenium.adapter.FluentAdapter;
import org.openqa.selenium.WebDriver;

/**
 * FluentLenium can be configured in many ways through configuration properties.
 * <p>
 * It's possible to define those properties using:
 * <ul>
 * <li>Overrides of JavaBean property getters of the test class.
 * (ie. override {@link Configuration#getWebDriver()})
 * </li>
 * <li>JavaBean property setters of the test class.
 * (ie. call {@link Configuration#setWebDriver(String)})
 * </li>
 * <li>System properties of the Java Environment, passed using -D on the command line.
 * Property names must be <b>prefixed with fluentlenium.</b>.
 * (ie. Launch test with <pre>-Dfluentlenium.webDriver=chrome</pre></quote>)
 * </li>
 * <li>
 * Environment Variable of the Operating System. Property names <b>must be prefixed with fluentlenium.</b>.
 * (ie: EXPORT fluentlenium.webDriver=chrome)
 * </li>
 * <li>
 * {@link FluentConfiguration} annotation on test class to configure.
 * <pre>
 * {@code
 * @FluentConfiguration(webDriver="chrome")
 * public class SomeFluentTest extends FluentTest {
 *     ....
 * }
 * }
 * </pre>
 * </li>
 * <li>
 * Java Properties file located at <code>/fluentlenium.properties</code> in the classpath
 * <pre>
 * {@code
 * webDriver=chrome
 * ...
 * }
 * </pre>
 * </li>
 * </ul>
 * This list of way to configure fluentlenium is ordered by priority. If a value is defined in an element, lower ways
 * to define it will be ignored.
 * <p>
 * You may implement additionnal ways to read configuration property by implementing another
 * {@link ConfigurationFactory} and set your configuration factory class in the
 * <pre>configurationFactory</pre> property.
 *
 * @see ConfigurationFactory
 * @see DefaultConfigurationFactory
 */

public interface ConfigurationProperties {
    enum TriggerMode {AUTOMATIC_ON_FAIL, MANUAL, UNDEFINED}

    /**
     * <pre>webDriver</pre> property.
     *
     * Set this property to a value supported by {@link WebDrivers} registry.
     *
     * When FluentLenium needs to create a new {@link WebDriver} instance, it calls FluentAdapter#newWebDriver()
     * which delegates to {@link WebDrivers#newWebDriver(String)} registry using the value stored in webDriver property.
     *
     * Possible values are "firefox", "chrome", "ie", "htmlunit", or any class name implementing {@link WebDriver}.
     *
     * Default value is "firefox".
     *
     * @return webDriver property value
     * @see FluentAdapter#newWebDriver().
     */
    String getWebDriver();

    /**
     * <pre>baseUrl</pre> property.
     *
     * Sets the base URL used to build absolute URL when relative URL is given to {@link FluentAdapter#goTo(String)}.
     *
     * Default value is null.
     *
     * @return baseUrl property value
     */
    String getBaseUrl();

    /**
     * <pre>pageLoadTimeout</pre> property.
     *
     * Sets the amount of time to wait for a page load to complete before throwing an error.
     * If the timeout is negative, page loads can be indefinite.
     *
     * Default value is null.
     *
     * @return pageLoadTimeout property value
     * @see org.openqa.selenium.WebDriver.Timeouts#pageLoadTimeout(long, java.util.concurrent.TimeUnit)
     */
    Long getPageLoadTimeout();

    /**
     * <pre>implicitlyWait</pre> property.
     *
     * Specifies the amount of time the driver should wait when searching for an element if it is
     * not immediately present.
     *
     * Default value is null.
     *
     * @return implicitlyWait property value
     * @see org.openqa.selenium.WebDriver.Timeouts#implicitlyWait(long, java.util.concurrent.TimeUnit)
     */
    Long getImplicitlyWait();

    /**
     * <pre>scriptTimeout</pre> property.
     *
     * Sets the amount of time to wait for an asynchronous script to finish execution before
     * throwing an error. If the timeout is negative, then the script will be allowed to run
     * indefinitely.
     *
     * Default value is null.
     *
     * @return scriptTimeout property value
     * @see org.openqa.selenium.WebDriver.Timeouts#setScriptTimeout(long, java.util.concurrent.TimeUnit)
     */
    Long getScriptTimeout();


    /**
     * <pre>screenshotPath</pre> property.
     *
     * Sets the filesystem path where screenshot will be saved when calling {@link FluentAdapter#takeScreenShot()} or
     * {@link FluentAdapter#takeScreenShot(String)}.
     *
     * Default value is null.
     *
     * @return screenshotPath property value
     */
    String getScreenshotPath();

    /**
     * <pre>screenshotMode</pre> property.
     *
     * Sets the trigger mode of screenshots. Can be {AUTOMATIC_ON_FAIL} to take screenshot when the test fail or {MANUAL}.
     *
     * Default value is null.
     *
     * @return screenshotMode property value.
     * @see TriggerMode
     */
    TriggerMode getScreenshotMode();

    /**
     * <pre>htmlDumpPath</pre> property.
     *
     * Sets the filesystem path where screenshot will be saved when calling {@link FluentAdapter#takeHtmlDump()} or
     * {@link FluentAdapter#takeHtmlDump(String)}.
     *
     * Default value is null.
     *
     * @return htmlDumpPath property value
     */
    String getHtmlDumpPath();

    /**
     * <pre>htmlDumpMode</pre> property.
     *
     * Sets the trigger mode of htmlDump. Can be {AUTOMATIC_ON_FAIL} to take html dump when the test fail or {MANUAL}.
     *
     * Default value is null.
     *
     * @return htmlDumpMode property value.
     * @see TriggerMode
     */
    TriggerMode getHtmlDumpMode();

    /**
     * <pre>configurationDefaults</pre> property.
     *
     * Set this to a class implementing {@link ConfigurationProperties} to provide the default values
     * of the configuration properties.
     *
     * Default value is {@link ConfigurationDefaults}
     *
     * @return Custom {@link ConfigurationProperties} instance with default values.
     */
    Class<? extends ConfigurationProperties> getConfigurationDefaults();

    /**
     * <pre>configurationFactory</pre> property.
     *
     * Set this to a class implementing {@link ConfigurationFactory} to customize the ways properties are read.
     * This allow to configure properties from sources that are not supported by default FluentLenium.
     *
     * Default value is {@link DefaultConfigurationFactory} class.
     *
     * @return Custom {@link ConfigurationFactory} class.
     */
    Class<? extends ConfigurationFactory> getConfigurationFactory();


}
