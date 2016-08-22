package org.fluentlenium.configuration;

import org.atteo.classindex.IndexSubclasses;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

/**
 * Factory of {@link WebDriver}, that can be registered in {@link WebDrivers} registry.
 */
@IndexSubclasses
public interface WebDriverFactory {
    /**
     * Creates a new instance of {@link WebDriver}.
     *
     * @param desiredCapabilities Desired capabilities for the web driver
     * @return
     */
    WebDriver newWebDriver(Capabilities desiredCapabilities);

    /**
     * Primary name of this factory.
     * <p>
     * To register it with alternative name, use {@link AlternativeNames}.
     *
     * @return Primary name.
     */
    String getName();
}
