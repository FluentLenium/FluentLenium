package io.fluentlenium.core;

import io.appium.java_client.AppiumDriver;
import io.fluentlenium.adapter.FluentAdapter;
import org.openqa.selenium.WebDriver;

/**
 * Control interface for selenium underlying web driver.
 */
public interface SeleniumDriverControl {
    /**
     * Get the actual underlying Selenium WebDriver.
     * <p>
     * To customize the WebDriver, you should configure {@link FluentAdapter#getWebDriver()}
     * or override {@link FluentAdapter#newWebDriver()}.
     * <p>
     * This method can't be overridden to customize the WebDriver.
     *
     * @return The actual underlying Selenium WebDriver
     */
    WebDriver getDriver();

    /**
     * Get the actual underlying AppiumDriver.
     * <p>
     * To customize the WebDriver, you should configure {@link FluentAdapter#getWebDriver()}
     * or override {@link FluentAdapter#newWebDriver()}.
     * <p>
     * This method can't be overridden to customize the AppiumDriver.
     *
     * @return The actual underlying AppiumDriver
     */
    AppiumDriver getAppiumDriver();
}
