package org.fluentlenium.adapter.sharedwebdriver;

import java.util.HashMap;
import java.util.Map;

public class ClassDriver implements FluentLeniumWebDriver {

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
