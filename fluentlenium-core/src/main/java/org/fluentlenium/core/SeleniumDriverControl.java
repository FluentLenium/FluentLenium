package org.fluentlenium.core;

import org.fluentlenium.adapter.FluentAdapter;
import org.openqa.selenium.WebDriver;

public interface SeleniumDriverControl {
    /**
     * Get the actual underlying Selenium WebDriver.
     * <p>
     * To customize the WebDriver, you should configure {@link org.fluentlenium.adapter.FluentAdapter#getWebDriver()}
     * or override {@link FluentAdapter#newWebDriver()}.
     * <p>
     * This method can't be override to customize the WebDriver.
     *
     * @return The actual underlying Selenium WebDriver
     */
    WebDriver getDriver();
}
