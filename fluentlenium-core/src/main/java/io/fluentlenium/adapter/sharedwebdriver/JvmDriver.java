package io.fluentlenium.adapter.sharedwebdriver;

import io.fluentlenium.configuration.ConfigurationProperties;

/**
 * Stores and handles a {@link SharedWebDriver} instance for the whole JVM.
 *
 * @see ConfigurationProperties.DriverLifecycle#JVM
 * @see SharedWebdriverSingletonImpl
 */
public class JvmDriver implements FluentLeniumDriver {

    private SharedWebDriver jvmDriver;

    public SharedWebDriver getDriver() {
        return jvmDriver;
    }

    @Override
    public void quitDriver(SharedWebDriver driver) {
        if (jvmDriver == driver) { // NOPMD CompareObjectsWithEquals
            if (jvmDriver.getDriver() != null) {
                jvmDriver.getDriver().quit();
            }
            jvmDriver = null;
        }
    }

    @Override
    public void addDriver(SharedWebDriver driver) {
        this.jvmDriver = driver;
    }
}
