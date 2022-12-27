package org.fluentlenium.example.spring.config.browser;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ie.InternetExplorerOptions;

class IE implements IBrowser {

    @Override
    public Capabilities getCapabilities() {
        InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
        internetExplorerOptions.destructivelyEnsureCleanSession();
        internetExplorerOptions.setCapability("browser_version", "11.0");
        internetExplorerOptions.setCapability("os_version", "10");
        return internetExplorerOptions;
    }

    @Override
    public String toString() {
        return "IE11";
    }
}
