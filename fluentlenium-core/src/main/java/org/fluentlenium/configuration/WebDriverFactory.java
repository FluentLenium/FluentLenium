package org.fluentlenium.configuration;

import org.openqa.selenium.WebDriver;

/**
 * Factory of {@link WebDriver}.
 */
public interface WebDriverFactory {
    /**
     * Creates a new instance of {@link WebDriver}.
     *
     * @return
     */
    WebDriver newWebDriver();
}
