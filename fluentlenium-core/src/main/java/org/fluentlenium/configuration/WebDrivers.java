package org.fluentlenium.configuration;

import lombok.experimental.Delegate;
import org.openqa.selenium.WebDriver;

/**
 * A registry of {@link WebDriverFactory}.
 * <p>
 * Supported drivers are "firefox", "chrome", "ie", "htmlunit", or any class name implementing {@link WebDriver}.
 */
public enum WebDrivers {
    INSTANCE;

    @Delegate
    private final WebDriversRegistryImpl impl = new WebDriversRegistryImpl();

}
