package org.fluentlenium.adapter.sharedwebdriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import org.openqa.selenium.WebDriver;

/**
 * Shared web driver container singleton implementation.
 */

@SuppressWarnings("PMD.CyclomaticComplexity")
class SharedWebdriverSingletonImpl {

    private final ClassDriver classDriverImpl = new ClassDriver();
    private final JvmDriver jvmDriverImpl = new JvmDriver();
    private final ThreadDriver threadDriverImpl = new ThreadDriver();
    private final MethodDriver methodDriverImpl = new MethodDriver();

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
                    jvmDriverImpl.addDriver(driver);
                    break;
                case CLASS:
                    classDriverImpl.addDriver(driver);
                    break;
                case THREAD:
                    threadDriverImpl.addDriver(driver);
                    break;

                default:
                    methodDriverImpl.addDriver(driver);
                    break;
            }
        }
    }

    /**
     * Get the current driver for given test class.
     *
     * @param driverLifecycle driver lifecycle
     * @param <T>             type of test class
     * @return shared WebDriver
     */
    public <T> SharedWebDriver getDriver(Class<T> testClass, String testName, DriverLifecycle driverLifecycle) {
        synchronized (this) {
            switch (driverLifecycle) {
                case JVM:
                    return jvmDriverImpl.getDriver();
                case CLASS:
                    return classDriverImpl.getDriver(testClass);
                case THREAD:
                    return threadDriverImpl.getDriver(testClass, testName,
                            Thread.currentThread().getId());

                default:
                    return methodDriverImpl.getDriver(testClass, testName);
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
                    jvmDriverImpl.quitDriver(driver);
                    break;
                case CLASS:
                    classDriverImpl.quitDriver(driver);
                    break;
                case THREAD:
                    threadDriverImpl.quitDriver(driver);
                    break;
                default:
                    methodDriverImpl.quitDriver(driver);
                    break;
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
            if (jvmDriverImpl.getDriver() != null) {
                drivers.add(jvmDriverImpl.getDriver());
            }

            drivers.addAll(classDriverImpl.getClassDrivers().values());
            drivers.addAll(threadDriverImpl.getThreadDrivers().values());
            drivers.addAll(methodDriverImpl.getMethodDrivers().values());
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
            SharedWebDriver classDriver = classDriverImpl.getClassDrivers().get(testClass);
            if (classDriver != null) {
                drivers.add(classDriver);
            }

            drivers.addAll(getDrivers(testClass, methodDriverImpl.getMethodDrivers()));
            drivers.addAll(getDrivers(testClass, threadDriverImpl.getThreadDrivers()));
            return Collections.unmodifiableList(drivers);
        }
    }

    private List<SharedWebDriver> getDrivers(Class<?> testClass, Map<?, SharedWebDriver> webDrivers) {
        List<SharedWebDriver> drivers = new ArrayList<>();
        for (SharedWebDriver testDriver : webDrivers.values()) {
            if (testDriver.getTestClass() == testClass) {
                drivers.add(testDriver);
            }
        }
        return drivers;
    }

    /**
     * Quit all shared web driver.
     */
    void quitAll() {
        synchronized (this) {
            if (jvmDriverImpl.getDriver() != null) {
                jvmDriverImpl.getDriver().getDriver().quit();
                jvmDriverImpl.addDriver(null);
            }

            quitAllDrivers(classDriverImpl.getClassDrivers());
            quitAllDrivers(methodDriverImpl.getMethodDrivers());
            quitAllDrivers(threadDriverImpl.getThreadDrivers());
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
