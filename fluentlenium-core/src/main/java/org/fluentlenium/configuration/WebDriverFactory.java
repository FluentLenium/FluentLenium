package org.fluentlenium.configuration;

import org.atteo.classindex.IndexSubclasses;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

/**
 * Factory of {@link WebDriver}, that can be registered in {@link WebDrivers} registry.
 */
@IndexSubclasses
public interface WebDriverFactory extends Factory {
    /**
     * Creates a new instance of {@link WebDriver}.
     *
     * @param capabilities  driver capabilities for the web driver
     * @param configuration Configuration
     * @return new instance of web driver
     */
    WebDriver newWebDriver(Capabilities capabilities, ConfigurationProperties configuration);
}
