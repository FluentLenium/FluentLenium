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
     * @param browserType browser type
     * @param useHub      use hub
     * @param hubLocation hub url
     */
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
        return browserConfig.usesHub()
                ? browserType.getRemoteWebDriver(browserConfig.getHubLocation())
                : browserType.getWebDriver();
    }

    public String getDriverExecutableName() {
        return browserType.getDriverExecutableName();
    }

    public String getDriverSystemPropertyName() {
        return browserType.getDriverSystemPropertyName();
    }

}
