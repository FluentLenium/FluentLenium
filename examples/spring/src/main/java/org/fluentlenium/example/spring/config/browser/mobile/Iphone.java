package org.fluentlenium.example.spring.config.browser.mobile;

import org.fluentlenium.example.spring.config.ConfigException;
import org.fluentlenium.example.spring.config.browser.IBrowser;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Iphone implements IBrowser {

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
    public String getDriverExecutableName() {
        throw new ConfigException("Not supported on iOS");
    }

    @Override
    public String getDriverSystemPropertyName() {
        throw new ConfigException("Not supported on iOS");
    }
}
