package org.fluentlenium.example.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SeleniumBrowserConfigProperties {
    @Value("${selenium.browser.type}")
    private BrowserType browserType;
    @Value("${selenium.hub.enabled}")
    private Boolean useHub;
    @Value("${selenium.hub.location}")
    private String hubLocation;
    @Value("${selenium.get.url}")
    private String pageUrl;

    public BrowserConfig getBrowserConfig() {
        return new BrowserConfig(browserType, useHub, hubLocation);
    }

    public String getPageUrl() {
        return pageUrl;
    }
}
