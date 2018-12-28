package org.fluentlenium.adapter.sharedwebdriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import org.openqa.selenium.WebDriver;

/**
 * Shared web driver container singleton implementation.
 */

@SuppressWarnings("PMD.CyclomaticComplexity")
class SharedWebdriverSingletonImpl {
    private final Map<Class<?>, SharedWebDriver> classDrivers = new HashMap<>();
    private final Map<ClassAndTestName, SharedWebDriver> methodDrivers = new HashMap<>();
    private final Map<ClassAndTestNameWithThreadId, SharedWebDriver> threadDrivers = new HashMap<>();
    private SharedWebDriver jvmDriver;

    /**
     * Get an existing or create a new shared driver for the given test, with the given shared driver
     * lifecycle strategy.
     *
     * @param webDriverFactory Supplier supplying new WebDriver instances
     * @param testClass        Test class
     * @param testName         Test name
     * @param driverLifecycle  shared driver lifecycle
     * @param <T>              type of test
     * @return shared web driver
     */
    <T> SharedWebDriver getOrCreateDriver(Supplier<WebDriver> webDriverFactory, Class<T> testClass,
                                          String testName, DriverLifecycle driverLifecycle) {
        synchronized (this) {
            SharedWebDriver driver = getDriver(testClass, testName, driverLifecycle);
            if (driver == null) {
                driver = createDriver(webDriverFactory, testClass, testName, driverLifecycle);
                registerDriver(driver);
            }
            return driver;
        }
    }

    private <T> SharedWebDriver createDriver(Supplier<WebDriver> webDriverFactory, Class<T> testClass, String testName,
                                             DriverLifecycle driverLifecycle) {
        WebDriver webDriver = webDriverFactory.get();
        return new SharedWebDriver(webDriver, testClass, testName, driverLifecycle);
    }

    private void registerDriver(SharedWebDriver driver) {

        synchronized (this) {
            switch (driver.getDriverLifecycle()) {
                case JVM:
                    jvmDriver = driver;
                    break;
                case CLASS:
                    classDrivers.put(driver.getTestClass(), driver);
                    break;
                case THREAD:
                    threadDrivers.put(new ClassAndTestNameWithThreadId(driver.getTestClass(), driver.getTestName(),
                            Thread.currentThread().getId()), driver);
                    break;

                default:
                    methodDrivers.put(new ClassAndTestName(driver.getTestClass(), driver.getTestName()), driver);
                    break;
            }
        }
    }

    /**
     * Get the current driver for given test class.
     *
     * @param testClass       test class
     * @param testName        test name
     * @param driverLifecycle driver lifecycle
     * @param <T>             type of test class
     * @return shared WebDriver
     */
    public <T> SharedWebDriver getDriver(Class<T> testClass, String testName, DriverLifecycle driverLifecycle) {
        synchronized (this) {
            switch (driverLifecycle) {
                case JVM:
                    return jvmDriver;
                case CLASS:
                    return classDrivers.get(testClass);
                case THREAD:
                    return threadDrivers.get(new ClassAndTestNameWithThreadId(testClass, testName,
                            Thread.currentThread().getId()));

                default:
                    return methodDrivers.get(new ClassAndTestName(testClass, testName));
            }
        }
    }

    /**
     * Quit an existing shared driver.
     *
     * @param driver Shared WebDriver
     */
    public void quit(SharedWebDriver driver) {
        synchronized (this) {
            switch (driver.getDriverLifecycle()) {
                case JVM:
                    quitJvmDriver(driver);
                    break;
                case CLASS:
                    quitClassDriver(driver);
                    break;
                case THREAD:
                    quitThreadDriver(driver);
                    break;

                default:
                    quitMethodDriver(driver);
                    break;
            }
        }
    }

    private void quitJvmDriver(SharedWebDriver driver) {
        if (jvmDriver == driver) { // NOPMD CompareObjectsWithEquals
            if (jvmDriver.getDriver() != null) {
                jvmDriver.getDriver().quit();
            }
            jvmDriver = null;
        }
    }

    private void quitMethodDriver(SharedWebDriver sharedWebDriver) {
        SharedWebDriver testDriver = methodDrivers
                .remove(new ClassAndTestName(sharedWebDriver.getTestClass(), sharedWebDriver.getTestName()));
        quitDriver(sharedWebDriver, testDriver);
    }

    private void quitClassDriver(SharedWebDriver sharedWebDriver) {
        SharedWebDriver classDriver = classDrivers.remove(sharedWebDriver.getTestClass());
        quitDriver(sharedWebDriver, classDriver);
    }

    private void quitThreadDriver(SharedWebDriver driver) {
        List<Entry<ClassAndTestNameWithThreadId, SharedWebDriver>> threadDriversToClose
                = getThreadDriversToClose(driver);
        threadDriversToClose.forEach(item -> closeThreadDriver(driver, item));
    }

    private List<Entry<ClassAndTestNameWithThreadId, SharedWebDriver>> getThreadDriversToClose(SharedWebDriver driver) {
        return threadDrivers.entrySet()
                .stream()
                .filter(entry -> entry.getKey().testClass.equals(driver.getTestClass())
                        && entry.getKey().testName.equals(driver.getTestName())
                        && entry.getValue().getDriver().equals(driver.getDriver()))
                .collect(Collectors.toList());
    }

    private void closeThreadDriver(SharedWebDriver sharedWebDriver,
                                   Entry<ClassAndTestNameWithThreadId, SharedWebDriver> item) {
        SharedWebDriver testThreadDriver = threadDrivers.remove(item.getKey());
        quitDriver(sharedWebDriver, testThreadDriver);
    }

    private void quitDriver(SharedWebDriver sharedWebDriver, SharedWebDriver testDriver) {
        if (testDriver == sharedWebDriver && testDriver.getDriver() != null) { // NOPMD CompareObjectsWithEquals
            testDriver.getDriver().quit();
        }
    }

    /**
     * Get all WebDriver of this container.
     *
     * @return List of {@link SharedWebDriver}
     */
    List<SharedWebDriver> getAllDrivers() {
        List<SharedWebDriver> drivers = new ArrayList<>();
        synchronized (this) {
            if (jvmDriver != null) {
                drivers.add(jvmDriver);
            }

            drivers.addAll(classDrivers.values());

            drivers.addAll(threadDrivers.values());

            drivers.addAll(methodDrivers.values());
        }
        return Collections.unmodifiableList(drivers);
    }

    /**
     * Get all shared WebDriver of this container for a given test class.
     *
     * @param testClass test class
     * @return list of shared WebDriver
     */
    List<SharedWebDriver> getTestClassDrivers(Class<?> testClass) {
        List<SharedWebDriver> drivers = new ArrayList<>();

        synchronized (this) {
            SharedWebDriver classDriver = classDrivers.get(testClass);
            if (classDriver != null) {
                drivers.add(classDriver);
            }

            drivers.addAll(getDrivers(testClass, methodDrivers));
            drivers.addAll(getDrivers(testClass, threadDrivers));
            return Collections.unmodifiableList(drivers);
        }
    }

    private List<SharedWebDriver> getDrivers(Class<?> testClass, Map<?, SharedWebDriver> webDrivers) {
        List<SharedWebDriver> drivers = new ArrayList<>();
        for (SharedWebDriver testDriver : webDrivers.values())
            if (testDriver.getTestClass() == testClass) {
                drivers.add(testDriver);
            }
        return drivers;
    }

    /**
     * Quit all shared web driver.
     */
    void quitAll() {
        synchronized (this) {
            if (jvmDriver != null) {
                jvmDriver.getDriver().quit();
                jvmDriver = null;
            }

            quitAllDrivers(classDrivers);
            quitAllDrivers(methodDrivers);
            quitAllDrivers(threadDrivers);
        }
    }

    private void quitAllDrivers(Map<?, SharedWebDriver> driverType) {
        Iterator<SharedWebDriver> testThreadDriversIterator = driverType.values().iterator();
        while (testThreadDriversIterator.hasNext()) {
            testThreadDriversIterator.next().getDriver().quit();
            testThreadDriversIterator.remove();
        }
    }

}
