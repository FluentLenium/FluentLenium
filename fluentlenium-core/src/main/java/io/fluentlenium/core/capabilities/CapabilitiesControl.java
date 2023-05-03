package io.fluentlenium.core.capabilities;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;

/**
 * Control capabilities of the underlying Selenium WebDriver.
 */
public interface CapabilitiesControl {

    /**
     * Get the actual capabilities of the underlying Selenium WebDriver.
     *
     * @return actual capabilities.
     * @see HasCapabilities#getCapabilities()
     */
    Capabilities capabilities();
}
