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

    public <T> SharedWebDriver getDriver(Class<T> testClass, String testName, DriverLifecycle driverLifecycle) {
        return impl.getDriver(testClass, testName, driverLifecycle);
    }

    public <T> SharedWebDriver getOrCreateDriver(Supplier<WebDriver> webDriverFactory, Class<T> testClass,
                                                 String testName, DriverLifecycle driverLifecycle) {
        return impl.getOrCreateDriver(webDriverFactory, testClass, testName, driverLifecycle);
    }

    public List<SharedWebDriver> getAllDrivers() {
        return impl.getAllDrivers();
    }

    public void quit(SharedWebDriver driver) {
        impl.quit(driver);
    }

    public void quitAll() {
        impl.quitAll();
    }

    public List<SharedWebDriver> getTestClassDrivers(Class<?> testClass) {
        return impl.getTestClassDrivers(testClass);
    }

}
