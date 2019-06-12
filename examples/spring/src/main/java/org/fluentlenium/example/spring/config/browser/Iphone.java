package org.fluentlenium.example.spring.config.browser;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Represents real iPhone connected to Grid
 */
class Iphone implements IBrowser {

    @Override
    public Capabilities getBrowserCapabilities() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browserName", "iPhone");
        caps.setCapability("device", "iPhone 8");
        caps.setCapability("realMobile", "true");
        caps.setCapability("os_version", "12");
        return caps;
    }

    @Override
    public String toString() {
        return "Iphone";
    }

}
