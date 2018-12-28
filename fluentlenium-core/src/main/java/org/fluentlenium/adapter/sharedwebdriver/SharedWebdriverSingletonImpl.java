package org.fluentlenium.adapter.sharedwebdriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.fluentlenium.configuration.ConfigurationProperties;
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
                                          String testName, ConfigurationProperties.DriverLifecycle driverLifecycle) {
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
                                             ConfigurationProperties.DriverLifecycle driverLifecycle) {
        WebDriver webDriver = webDriverFactory.get();
        return new SharedWebDriver(webDriver, testClass, testName, driverLifecycle);
    }

    private void registerDriver(SharedWebDriver driver) {
        if (driver.getDriverLifecycle() == ConfigurationProperties.DriverLifecycle.JVM) {
            jvmDriver = driver;
        } else if (driver.getDriverLifecycle() == ConfigurationProperties.DriverLifecycle.CLASS) {
            classDrivers.put(driver.getTestClass(), driver);
        } else if (driver.getDriverLifecycle() == ConfigurationProperties.DriverLifecycle.THREAD) {
            threadDrivers.put(new ClassAndTestNameWithThreadId(driver.getTestClass(), driver.getTestName(),
                    Thread.currentThread().getId()), driver);
        } else {
            methodDrivers.put(new ClassAndTestName(driver.getTestClass(), driver.getTestName()), driver);
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
    public <T> SharedWebDriver getDriver(Class<T> testClass, String testName, ConfigurationProperties.DriverLifecycle driverLifecycle) {
        synchronized (this) {
            SharedWebDriver sharedWebDriver;

            if (driverLifecycle == ConfigurationProperties.DriverLifecycle.JVM) {
                sharedWebDriver = jvmDriver;
            } else if (driverLifecycle == ConfigurationProperties.DriverLifecycle.CLASS) {
                sharedWebDriver = classDrivers.get(testClass);
            } else if (driverLifecycle == ConfigurationProperties.DriverLifecycle.THREAD) {
                sharedWebDriver = threadDrivers.get(new ClassAndTestNameWithThreadId(testClass, testName,
                        Thread.currentThread().getId()));
            } else {
                sharedWebDriver = methodDrivers.get(new ClassAndTestName(testClass, testName));
            }

            return sharedWebDriver;
        }
    }

    /**
     * Quit an existing shared driver.
     *
     * @param driver Shared WebDriver
     */
    public void quit(SharedWebDriver driver) {
        synchronized (this) {
            if (driver.getDriverLifecycle() == ConfigurationProperties.DriverLifecycle.JVM) {
                if (jvmDriver == driver) { // NOPMD CompareObjectsWithEquals
                    if (jvmDriver.getDriver() != null) {
                        jvmDriver.getDriver().quit();
                    }
                    jvmDriver = null;
                }
            } else if (driver.getDriverLifecycle() == ConfigurationProperties.DriverLifecycle.CLASS) {
                SharedWebDriver classDriver = classDrivers.remove(driver.getTestClass());
                if (classDriver == driver && classDriver.getDriver() != null) { // NOPMD CompareObjectsWithEquals
                    classDriver.getDriver().quit();
                }
            } else if (driver.getDriverLifecycle() == ConfigurationProperties.DriverLifecycle.THREAD) {
                List<Map.Entry<ClassAndTestNameWithThreadId, SharedWebDriver>> threadDriversToClose = threadDrivers.entrySet()
                        .stream()
                        .filter(entry -> entry.getKey().testClass.equals(driver.getTestClass())
                                && entry.getKey().testName.equals(driver.getTestName())
                                && entry.getValue().getDriver().equals(driver.getDriver()))
                                        .collect(Collectors.toList());

                threadDriversToClose.forEach(item -> {
                    SharedWebDriver testThreadDriver = threadDrivers.remove(item.getKey());
                    if (testThreadDriver == driver
                            && testThreadDriver.getDriver() != null) { // NOPMD CompareObjectsWithEquals
                        testThreadDriver.getDriver().quit();
                    }
                });
            } else {
                SharedWebDriver testDriver = methodDrivers
                        .remove(new ClassAndTestName(driver.getTestClass(), driver.getTestName()));
                if (testDriver == driver && testDriver.getDriver() != null) { // NOPMD CompareObjectsWithEquals
                    testDriver.getDriver().quit();
                }
            }
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

            for (SharedWebDriver testDriver : methodDrivers.values()) {
                if (testDriver.getTestClass() == testClass) {
                    drivers.add(testDriver);
                }
            }

            for (SharedWebDriver testDriver : threadDrivers.values()) {
                if (testDriver.getTestClass() == testClass) {
                    drivers.add(testDriver);
                }
            }

            return Collections.unmodifiableList(drivers);
        }
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

            Iterator<SharedWebDriver> classDriversIterator = classDrivers.values().iterator();
            while (classDriversIterator.hasNext()) {
                classDriversIterator.next().getDriver().quit();
                classDriversIterator.remove();
            }

            Iterator<SharedWebDriver> testDriversIterator = methodDrivers.values().iterator();
            while (testDriversIterator.hasNext()) {
                testDriversIterator.next().getDriver().quit();
                testDriversIterator.remove();
            }

            Iterator<SharedWebDriver> testThreadDriversIterator = threadDrivers.values().iterator();
            while (testThreadDriversIterator.hasNext()) {
                testThreadDriversIterator.next().getDriver().quit();
                testThreadDriversIterator.remove();
            }
        }
    }
}
