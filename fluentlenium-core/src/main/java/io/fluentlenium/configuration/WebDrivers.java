package io.fluentlenium.configuration;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

/**
 * A registry of {@link WebDriverFactory}.
 * <p>
 * For supported webdrivers, see {@link ConfigurationProperties#getWebDriver()}.
 *
 * @see DefaultWebDriverFactories
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
    }
}
