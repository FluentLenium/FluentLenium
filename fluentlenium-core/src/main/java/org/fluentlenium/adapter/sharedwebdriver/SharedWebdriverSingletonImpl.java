package org.fluentlenium.adapter.sharedwebdriver;

import org.fluentlenium.adapter.SharedMutator.EffectiveParameters;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

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
     * @param parameters       test parameters
     * @return shared web driver
     */
    SharedWebDriver getOrCreateDriver(Supplier<WebDriver> webDriverFactory, EffectiveParameters<?> parameters) {
        synchronized (this) {
            return Optional.ofNullable(getDriver(parameters))
                    .orElseGet(() -> createNewDriver(webDriverFactory, parameters));
        }
    }

    private SharedWebDriver createNewDriver(Supplier<WebDriver> webDriverFactory, EffectiveParameters<?> parameters) {
        SharedWebDriver driver = createDriver(webDriverFactory, parameters);
        registerDriver(driver);
        return driver;
    }

    private SharedWebDriver createDriver(Supplier<WebDriver> webDriverFactory, EffectiveParameters<?> parameters) {
        WebDriver webDriver = webDriverFactory.get();
        return new SharedWebDriver(webDriver,
                parameters.getTestClass(), parameters.getTestName(), parameters.getDriverLifecycle());
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
     * @param parameters test parameters
     * @return shared WebDriver
     */
    public SharedWebDriver getDriver(EffectiveParameters<?> parameters) {
        synchronized (this) {
            switch (parameters.getDriverLifecycle()) {
                case JVM:
                    return jvmDriverImpl.getDriver();
                case CLASS:
                    return classDriverImpl.getDriver(parameters.getTestClass());
                case THREAD:
                    return threadDriverImpl.getDriver(parameters.getTestClass(), parameters.getTestName(),
                            Thread.currentThread().getId());

                default:
                    return methodDriverImpl.getDriver(parameters.getTestClass(), parameters.getTestName());
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
            Optional.ofNullable(jvmDriverImpl.getDriver()).ifPresent(drivers::add);
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
            Optional.ofNullable(classDriverImpl.getClassDrivers().get(testClass)).ifPresent(drivers::add);
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
            Optional.ofNullable(jvmDriverImpl.getDriver()).ifPresent(jvmDriverImpl::quitDriver);
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
