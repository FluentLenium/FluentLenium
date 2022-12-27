package io.fluentlenium.adapter.sharedwebdriver;

import io.fluentlenium.configuration.ConfigurationProperties;import java.util.HashMap;
import java.util.Map;

/**
 * Stores and handles {@link SharedWebDriver} instances for test {@link Class}es.
 *
 * @see ConfigurationProperties.DriverLifecycle#CLASS
 * @see SharedWebdriverSingletonImpl
 */
public class ClassDriver implements FluentLeniumDriver {

    private final Map<Class<?>, SharedWebDriver> classDrivers = new HashMap<>();

    Map<Class<?>, SharedWebDriver> getClassDrivers() {
        return classDrivers;
    }

    @Override
    public void quitDriver(SharedWebDriver sharedWebDriver) {
        SharedWebDriver classDriver = classDrivers.remove(sharedWebDriver.getTestClass());
        quitDriver(sharedWebDriver, classDriver);
    }

    @Override
    public void addDriver(SharedWebDriver driver) {
        classDrivers.put(driver.getTestClass(), driver);
    }

    public <T> SharedWebDriver getDriver(Class<T> testClass) {
        return classDrivers.get(testClass);
    }
}
