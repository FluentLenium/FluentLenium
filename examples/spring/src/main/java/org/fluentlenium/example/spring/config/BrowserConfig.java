package org.fluentlenium.example.spring.config;

import org.openqa.selenium.WebDriver;

/**
 * Browser configuration
 */
public class BrowserConfig {

    private final BrowserType browserType;
    private final boolean useHub;
    private final String hubLocation;

    /**
     * Creates a new browser configuration
     *
     * @param browserType
     * @param useHub
     * @param hubLocation
     */
    public BrowserConfig(final BrowserType browserType, final boolean useHub, final String hubLocation) {
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

    public WebDriver resolveDriver(final BrowserConfig browserConfig) {
        return browserConfig.usesHub() ?
                browserType.getRemoteWebDriver(browserConfig.getHubLocation()) : browserType.getWebDriver();
    }

}
