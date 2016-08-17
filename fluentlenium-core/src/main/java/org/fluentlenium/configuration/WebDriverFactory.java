package org.fluentlenium.configuration;

import org.openqa.selenium.WebDriver;

/**
 * Factory of {@link WebDriver}, that can be registered in {@link WebDrivers} registry.
 */
public interface WebDriverFactory {
    /**
     * Creates a new instance of {@link WebDriver}.
     *
     * @return
     */
    WebDriver newWebDriver();

    /**
     * Names of this factory.
     *
     * @return
     */
    String[] getNames();
}
