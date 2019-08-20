package org.fluentlenium.core;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WrapsDriver;

/**
 * Provides wrapped WebDriver capabilities for {@link FluentDriver}.
 */
public class FluentDriverWrappedCapabilitiesProvider {

    /**
     * Goes through all the underlying wrapped drivers ({@link WrapsDriver}) in the argument {@link WebDriver},
     * and returns the {@link Capabilities} of the innermost wrapped driver.
     *
     * @param driver the webdriver to get the capabilities from
     * @return capabilities of the innermost wrapped driver.
     */
    public Capabilities getCapabilities(WebDriver driver) {
        WebDriver currentDriver = driver;
        Capabilities capabilities = capabilitiesOf(currentDriver);
        while (currentDriver instanceof WrapsDriver && capabilities == null) {
            currentDriver = ((WrapsDriver) currentDriver).getWrappedDriver();
            capabilities = capabilitiesOf(currentDriver);
        }
        return capabilities;
    }

    private Capabilities capabilitiesOf(WebDriver currentDriver) {
        return currentDriver instanceof HasCapabilities
                ? ((HasCapabilities) currentDriver).getCapabilities()
                : null;
    }
}
