package org.fluentlenium.example.spring.config.browser;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.safari.SafariOptions;

class Safari implements IBrowser {

    @Override
    public Capabilities getCapabilities() {
        SafariOptions safariOptions = new SafariOptions();
        safariOptions.setCapability("browser_version", "12.0");
        safariOptions.setCapability("os_version", "Mojave");
        return safariOptions;
    }

    @Override
    public String toString() {
        return "Safari";
    }
}
