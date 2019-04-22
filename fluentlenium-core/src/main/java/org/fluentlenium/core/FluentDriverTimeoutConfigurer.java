package org.fluentlenium.core;

import static java.util.Objects.requireNonNull;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import org.fluentlenium.configuration.Configuration;
import org.openqa.selenium.WebDriver;

/**
 * Configures a {@link WebDriver} instance with timeouts from a {@link Configuration}.
 */
public class FluentDriverTimeoutConfigurer {

    private final Configuration configuration;
    private final WebDriver driver;

    public FluentDriverTimeoutConfigurer(Configuration configuration, WebDriver driver) {
        this.configuration = requireNonNull(configuration);
        this.driver = driver;
    }

    /**
     * Configures a {@link WebDriver} instance with timeouts from a {@link Configuration}.
     * <p>
     * Configures the following options:
     * <ul>
     * <li>page load timeout</li>
     * <li>implicitly wait</li>
     * <li>script timeout</li>
     * </ul>
     */
    public void configureDriver() {
        if (driver != null && driver.manage() != null && driver.manage().timeouts() != null) {
            configurePageLoadTimeout();
            configureImplicitlyWait();
            configureScriptTimeout();
        }
    }

    private void configurePageLoadTimeout() {
        if (configuration.getPageLoadTimeout() != null) {
            driver.manage().timeouts().pageLoadTimeout(configuration.getPageLoadTimeout(), MILLISECONDS);
        }
    }

    private void configureImplicitlyWait() {
        if (configuration.getImplicitlyWait() != null) {
            driver.manage().timeouts().implicitlyWait(configuration.getImplicitlyWait(), MILLISECONDS);
        }
    }

    private void configureScriptTimeout() {
        if (configuration.getScriptTimeout() != null) {
            driver.manage().timeouts().setScriptTimeout(configuration.getScriptTimeout(), MILLISECONDS);
        }
    }
}
