package org.fluentlenium.example.spring.config;

import org.openqa.selenium.WebDriver;

public class BrowserConfig {

    private BrowserType browserType;
    private boolean useHub;
    private String hubLocation;

    public BrowserConfig(BrowserType browserType, boolean useHub, String hubLocation) {
        this.browserType = browserType;
        this.useHub = useHub;
        this.hubLocation = hubLocation;
    }

    public boolean usesHub() {
        return useHub;
    }

    public String getHubLocation() {
        return hubLocation;
    }

    public WebDriver resolveDriver(BrowserConfig browserConfig) {
        return browserConfig.usesHub() ?
                browserType.getRemoteWebDriver(browserConfig.getHubLocation()) : browserType.getWebDriver();
    }

}
