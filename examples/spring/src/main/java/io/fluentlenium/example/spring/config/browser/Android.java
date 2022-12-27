package io.fluentlenium.example.spring.config.browser;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Represents real Android device connected to Grid
 */
class Android implements IBrowser {

    @Override
    public Capabilities getCapabilities() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browserName", "android");
        caps.setCapability("device", "Google Nexus 6");
        caps.setCapability("realMobile", "true");
        caps.setCapability("os_version", "6.0");
        return caps;
    }

    @Override
    public String toString() {
        return "Real Android Google Nexus 6 device";
    }
}
