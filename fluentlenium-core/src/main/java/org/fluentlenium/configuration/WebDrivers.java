package org.fluentlenium.configuration;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

/**
 * A registry of {@link WebDriverFactory}.
 * <p>
 * Supported drivers are "firefox", "chrome", "ie", "htmlunit", or any class name implementing {@link WebDriver}.
 */
public enum WebDrivers {
    /**
     * Singleton
     */
    INSTANCE;

    private final WebDriversRegistryImpl impl = new WebDriversRegistryImpl();

    public WebDriversRegistryImpl getImpl() {
        return impl;
    }

    public void register(WebDriverFactory factory) {
        getImpl().register(factory);
    }

    public WebDriverFactory getDefault() {
        return getImpl().getDefault();
    }

    public WebDriverFactory get(String name) {
        return getImpl().get(name);
    }

    public WebDriver newWebDriver(String name, Capabilities capabilities, ConfigurationProperties configuration) {
        return this.impl.newWebDriver(name, capabilities, configuration);
    }}
