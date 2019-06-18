package org.fluentlenium.example.spring.config.browser;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeOptions;

class Chrome implements IBrowser {

    @Override
    public Capabilities getCapabilities() {
        ChromeOptions options = new ChromeOptions();
        options.setCapability("os", "OS X");
        options.setCapability("os_version", "Mojave");
        return options;
    }

    @Override
    public String getDriverExecutableName() {
        return "chromedriver";
    }

    @Override
    public String getDriverSystemPropertyName() {
        return "webdriver.chrome.driver";
    }

    @Override
    public String toString() {
        return "Chrome";
    }
}
