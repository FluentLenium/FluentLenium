package org.fluentlenium.configuration;

import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.core.FluentControl;
import org.openqa.selenium.Capabilities;
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
 * (ie. Launch test with <pre>-Dfluentlenium.webDriver=chrome</pre>)
 * </li>
 * <li>
 * Environment Variable of the Operating System. Property names <b>must be prefixed with fluentlenium.</b>.
 * (ie: EXPORT fluentlenium.webDriver=chrome)
 * </li>
 * <li>
 * {@link FluentConfiguration} annotation on test class to configure.
 * <pre>
 * {@code
 *
 * {@literal @FluentConfiguration(webDriver="chrome")} public class SomeFluentTest extends FluentTest {
 * ....
 * }
 * }
 * </pre>
 * </li>
 * <li>
 * Java Properties file located at <pre>/fluentlenium.properties</pre> in the classpath
 * <pre>
 * {@code
 * webDriver=chrome
 * ...
 * }
 * </pre>
 * </li>
 * <li>
 * {@link ConfigurationProperties} custom implementation specified by <pre>configurationDefaults</pre> property.
 * <pre>
 * {@code
 * public class CustomConfigurationDefaults extends ConfigurationDefaults {
 * {@literal @Override} public String getWebDriver() {
 * return "chrome";
 * }
 * }
 *
 * $ cat fluentlenium.properties
 * configurationDefaults=org.your.package.CustomConfigurationDefaults
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
    /**
     * Trigger mode for Screenshots and HtmlDump features
     */
    enum TriggerMode {
        /**
         * Take screenshot when the test fail.
         */
        AUTOMATIC_ON_FAIL, /**
         * Only take screenshot manually through API.
         */
        MANUAL, /**
         * Default value.
         */
        DEFAULT
    }

    /**
     * Driver lifecycle.
     */
    enum DriverLifecycle {
        /**
         * WebDriver is created once, and same instance is used for each test class and method.
         */
        JVM, /**
         * WebDriver is created for each test class, and same instance is used for each test method in the class.
         */
        CLASS, /**
         * WebDriver is created for each test method, and this instance is used only for one test method.
         */
        METHOD, /**
         * WebDriver is created for each test thread, and this instance is used only for one test method.
         */
        THREAD, /**
         * Default value.
         */
        DEFAULT
    }

    /**
     * <pre>webDriver</pre> property.
     *
     * Sets the WebDriver type to use.
     *
     * When FluentLenium needs to create a new {@link WebDriver} instance, it calls {@link FluentAdapter#newWebDriver()}
     * which delegates to
     * {@link org.fluentlenium.configuration.WebDriversRegistryImpl#newWebDriver(String, Capabilities, ConfigurationProperties)}
     * registry using the value stored in webDriver and capabilities property.
     *
     * Possible values are "firefox", "chrome", "ie", "htmlunit", or any class name implementing {@link WebDriver}.
     *
     * Default value is "firefox".
     *
     * @return webDriver property value
     * @see FluentAdapter#newWebDriver()
     */
    String getWebDriver();

    /**
     * <pre>remoteUrl</pre> property.
     *
     * Sets the remoteUrl for "remote" webDriver.
     *
     * @return remoteUrl property value
     * @see org.openqa.selenium.remote.RemoteWebDriver
     */
    String getRemoteUrl();

    /**
     * <pre>capabilities</pre> property.
     *
     * Sets the <a href="https://github.com/SeleniumHQ/selenium/wiki/DesiredCapabilities">Capabilities</a> to use, as a
     * JSON Object or a URL pointing to a JSON Object.
     *
     * Default value is "null".
     *
     * @return Capabilities property value
     * @see Capabilities
     * @see <a href="https://github.com/SeleniumHQ/selenium/wiki/DesiredCapabilities">Selenium DesiredCapabilities Wiki Page</a>
     */
    Capabilities getCapabilities();

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
     * <pre>driverLifecycle</pre> property.
     *
     * Sets the lifecycle of the WebDriver. WebDriver is fully managed by FluentLenium, so you should never
     * create or quit a WebDriver by yourself.
     *
     * Please keep in mind that this configures when drivers are created and exited at runtime, but it does not deal with
     * concurrency of your tests.
     *
     * Default value is METHOD.
     *
     * @return driverLifecycle property value
     */
    DriverLifecycle getDriverLifecycle();

    /**
     * <pre>browserTimeout</pre> property.
     *
     * Sets the maximum amount of time when the browser should start responding to the WebDriver.
     *
     * Default value is 60 seconds.
     *
     * @return long
     */
    Long getBrowserTimeout();

    /**
     * <pre>browserTimeoutRetries</pre> property.
     *
     * Sets the maximum number of retries for failed WebDriver because of browserTimeout issues.
     *
     * Default value is 2 times.
     *
     * @return Integer
     */
    Integer getBrowserTimeoutRetries();

    /**
     * <pre>deleteCookies</pre> property.
     *
     * When using CLASS or JVM <pre>driverLifecycle</pre> configuration property, allow to delete cookies between
     * each test.
     *
     * Default value is false.
     *
     * @return deleteCookies property value.
     */
    Boolean getDeleteCookies();

    /**
     * <pre>pageLoadTimeout</pre> property.
     *
     * Sets the amount of time in millisecond to wait for a page load to complete before throwing an error.
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
     * Specifies the amount of time in millisecond the driver should wait when searching for an element if it is
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
     * Sets the amount of time in millisecond to wait for an asynchronous script to finish execution before
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
     * <pre>awaitAtMost</pre> property.
     *
     * Sets the default timeout in millisecond when using {@link FluentControl#await()} or
     * {@link org.fluentlenium.core.hook.wait.Wait} hook.
     *
     * @return awaitTimeout property value
     * @see org.fluentlenium.core.wait.FluentWait#atMost(long, java.util.concurrent.TimeUnit)
     * @see org.fluentlenium.core.wait.FluentWait#atMost(long)
     */
    Long getAwaitAtMost();

    /**
     * <pre>awaitPollingEvery</pre> property.
     *
     * Sets the default polling frequency in millisecond when using {@link FluentControl#await()} or
     * {@link org.fluentlenium.core.hook.wait.Wait} hook.
     *
     * @return awaitPollingEvery property value
     * @see org.fluentlenium.core.wait.FluentWait#pollingEvery(long, java.util.concurrent.TimeUnit)
     * @see org.fluentlenium.core.wait.FluentWait#pollingEvery(long)
     */
    Long getAwaitPollingEvery();

    /**
     * <pre>eventsEnabled</pre> property.
     *
     * Enables {@link FluentAdapter#events()} by wrapping the {@link WebDriver} in
     * {@link org.openqa.selenium.support.events.EventFiringWebDriver}.
     *
     * Default value is true.
     *
     * @return eventsEnabled property value.
     */
    Boolean getEventsEnabled();

    /**
     * <pre>screenshotPath</pre> property.
     *
     * Sets the filesystem path where screenshot will be saved when calling {@link FluentAdapter#takeScreenshot()} or
     * {@link FluentAdapter#takeScreenshot(String)}.
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

    /**
     * Get custom property value.
     *
     * @param propertyName name of the property
     * @return property value
     */
    String getCustomProperty(String propertyName);

}
