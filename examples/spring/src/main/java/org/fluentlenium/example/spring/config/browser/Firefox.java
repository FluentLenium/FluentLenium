package org.fluentlenium.example.spring.config.browser;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.firefox.FirefoxOptions;

class Firefox implements IBrowser {

    @Override
    public Capabilities getCapabilities() {
        FirefoxOptions options = new FirefoxOptions();
        options.setCapability("os", "Windows");
        return options;
    }

    @Override
    public String toString() {
        return "Firefox";
    }
}
