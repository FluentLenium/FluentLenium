package io.fluentlenium.example.spring.config;

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
    @Value("${appium.server.url}")
    private String appiumServerUrl;

    @Value("${selenium.hub.enabled}")
    private Boolean useHub;
    @Value("${selenium.hub.url}")
    private String hubUrl;

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

    public String getAppiumServerUrl() {
        return getStringProperty("appiumServerUrl", appiumServerUrl);
    }

    public String getPageUrl() {
        return getStringProperty("pageUrl", pageUrl);
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
