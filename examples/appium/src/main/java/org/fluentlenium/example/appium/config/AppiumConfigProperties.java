package org.fluentlenium.example.appium.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AppiumConfigProperties {

    @Value("${appium.server.url}")
    private String appiumServerUrl;

    public String getAppiumServerUrl() {
        return getStringProperty("appiumServerUrl", appiumServerUrl);
    }

    private String getStringProperty(String propertyName, String propertyValue) {
        return Optional.ofNullable(System.getProperty(propertyName))
                .orElse(propertyValue);
    }

}
