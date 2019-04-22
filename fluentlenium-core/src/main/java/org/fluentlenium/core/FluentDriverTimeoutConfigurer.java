package org.fluentlenium.core;

import static java.util.Objects.requireNonNull;

import org.fluentlenium.configuration.Configuration;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

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
            if (configuration.getPageLoadTimeout() != null) {
                driver.manage().timeouts().pageLoadTimeout(configuration.getPageLoadTimeout(), TimeUnit.MILLISECONDS);
            }

            if (configuration.getImplicitlyWait() != null) {
                driver.manage().timeouts().implicitlyWait(configuration.getImplicitlyWait(), TimeUnit.MILLISECONDS);
            }

            if (configuration.getScriptTimeout() != null) {
                driver.manage().timeouts().setScriptTimeout(configuration.getScriptTimeout(), TimeUnit.MILLISECONDS);
            }
        }
    }
}
