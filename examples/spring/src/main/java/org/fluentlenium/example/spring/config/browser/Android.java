package org.fluentlenium.example.spring.config.browser;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

class Android implements IBrowser {

    @Override
    public Capabilities getBrowserCapabilities() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browserName", "android");
        caps.setCapability("device", "Google Nexus 6");
        caps.setCapability("realMobile", "true");
        caps.setCapability("os_version", "6.0");
        return caps;
    }

}
