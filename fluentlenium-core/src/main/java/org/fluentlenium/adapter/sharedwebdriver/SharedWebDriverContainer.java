package org.fluentlenium.adapter.sharedwebdriver;

import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.function.Supplier;

/**
 * A singleton container for all running {@link SharedWebDriver} in the JVM.
 */
public enum SharedWebDriverContainer {
    /**
     * Singleton
     */
    INSTANCE;

    private final SharedWebdriverSingletonImpl impl = new SharedWebdriverSingletonImpl();

    /**
     * Creates a new Shared WebDriver Container.
     */
    SharedWebDriverContainer() {
        // NOPMD SingularField
        SharedWebDriverContainerShutdownHook shutdownHook =
                new SharedWebDriverContainerShutdownHook("SharedWebDriverContainerShutdownHook");
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }

    public SharedWebdriverSingletonImpl getImpl() {
        return impl;
    }

    public <T> SharedWebDriver getDriver(Class<T> testClass, String testName, DriverLifecycle driverLifecycle) {
        return getImpl().getDriver(testClass, testName, driverLifecycle);
    }

    public <T> SharedWebDriver getOrCreateDriver(Supplier<WebDriver> webDriverFactory, Class<T> testClass,
                                                 String testName, DriverLifecycle driverLifecycle) {
        return getImpl().getOrCreateDriver(webDriverFactory, testClass, testName, driverLifecycle);
    }

    public List<SharedWebDriver> getAllDrivers() {
        return getImpl().getAllDrivers();
    }

    public void quit(SharedWebDriver driver) {
        getImpl().quit(driver);
    }

    public void quitAll() {
        getImpl().quitAll();
    }

    public List<SharedWebDriver> getTestClassDrivers(Class<?> testClass) {
        return getImpl().getTestClassDrivers(testClass);
    }

}
