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
        return useHub;
    }

    public Boolean isMobileSimulator() {
        return Optional.of(Boolean.valueOf(System.getProperty("mobile.simulator")))
                .orElse(mobileSimulator);
    }

    public String getBrowserName() {
        return Optional.ofNullable(System.getProperty("browserName"))
                .orElse(browserName);
    }

    public String getPageUrl() {
        return pageUrl;
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

    public String getGridUrl() {
        return hubUrl;
    }
}
