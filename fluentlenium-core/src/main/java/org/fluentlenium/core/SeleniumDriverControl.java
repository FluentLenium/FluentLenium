package org.fluentlenium.core;

import org.fluentlenium.adapter.FluentAdapter;
import org.openqa.selenium.WebDriver;

/**
 * Control interface for selenium underlying web driver.
 */
public interface SeleniumDriverControl {
    /**
     * Get the actual underlying Selenium WebDriver.
     * <p>
     * To customize the WebDriver, you should configure {@link org.fluentlenium.adapter.FluentAdapter#getWebDriver()}
     * or override {@link FluentAdapter#newWebDriver()}.
     * <p>
     * This method can't be overridden to customize the WebDriver.
     *
     * @return The actual underlying Selenium WebDriver
     */
    WebDriver getDriver();
}
