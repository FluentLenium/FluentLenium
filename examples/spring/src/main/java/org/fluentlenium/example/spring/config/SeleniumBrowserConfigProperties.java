package org.fluentlenium.example.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SeleniumBrowserConfigProperties {

    @Value("${browser.name}")
    private String browserName;
    @Value("${page.url}")
    private String pageUrl;

    @Value("${mobile.simulator}")
    private Boolean mobileSimulator;

    @Value("${selenium.hub.enabled}")
    private Boolean useHub;
    @Value("${selenium.hub.url}")
    private String hubUrl;

    @Value("${safaridriver.path}")
    private String safariDriverPath;
    @Value("${iedriver.path}")
    private String ieDriverPath;
    @Value("${edgedriver.path}")
    private String edgeDriverPath;
    @Value("${operadriver.path}")
    private String operaDriverPath;
    @Value("${chromedriver.path}")
    private String chromeDriverPath;
    @Value("${firefoxdriver.path}")
    private String firefoxDriverPath;

    public Boolean useHub() {
        return getBooleanProperty("useHub", useHub);
    }

    public Boolean isMobileSimulator() {
        return getBooleanProperty("mobileSimulator", mobileSimulator);
    }

    public String getBrowserName() {
        return getStringProperty("browserName", browserName);
    }

    public String getGridUrl() {
        return getStringProperty("gridUrl", hubUrl);
    }

    public String getPageUrl() {
        return getStringProperty("pageUrl", pageUrl);
    }

    public String getDriverExecutablePath() {
        switch (browserName.toLowerCase()) {
            case "safari":
                return safariDriverPath;
            case "firefox":
                return firefoxDriverPath;
            case "ie":
                return ieDriverPath;
            case "edge":
                return edgeDriverPath;
            case "opera":
                return operaDriverPath;
            default:
                return chromeDriverPath;
        }
    }

    private String getStringProperty(String propertyName, String propertyValue) {
        return Optional.ofNullable(System.getProperty(propertyName))
                .orElse(propertyValue);
    }

    private Boolean getBooleanProperty(String propertyName, Boolean configuredValue) {
        if (System.getProperty(propertyName) == null) {
            return configuredValue;
        }
        return Boolean.valueOf(System.getProperty(propertyName));
    }
}
