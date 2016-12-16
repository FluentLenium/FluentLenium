package org.fluentlenium.adapter;

import com.google.common.base.Supplier;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.experimental.Delegate;
import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A singleton container for all running {@link SharedWebDriver} in the JVM.
 */
public enum SharedWebDriverContainer {
    /**
     * Singleton
     */
    INSTANCE;

    @Delegate
    private final Impl impl = new Impl();

    private final SharedWebDriverContainerShutdownHook shutdownHook; // NOPMD SingularField

    /**
     * Creates a new Shared WebDriver Container.
     */
    SharedWebDriverContainer() {
        shutdownHook = new SharedWebDriverContainerShutdownHook("SharedWebDriverContainerShutdownHook");
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }

    @EqualsAndHashCode
    @AllArgsConstructor
    private static class ClassAndTestName {
        private Class<?> testClass;
        private String testName;
    }

    /**
     * Shared web driver container singleton implementation.
     */
    static class Impl {
        private SharedWebDriver jvmDriver;

        private final Map<Class<?>, SharedWebDriver> classDrivers = new HashMap<>();

        private final Map<ClassAndTestName, SharedWebDriver> methodDrivers = new HashMap<>();

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
        public <T> SharedWebDriver getOrCreateDriver(Supplier<WebDriver> webDriverFactory, Class<T> testClass,
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

        private <T> SharedWebDriver createDriver(Supplier<WebDriver> webDriverFactory, Class<T> testClass,
                String testName, DriverLifecycle driverLifecycle) {
            WebDriver webDriver = webDriverFactory.get();
            return new SharedWebDriver(webDriver, testClass, testName, driverLifecycle);
        }

        private void registerDriver(SharedWebDriver driver) {
            switch (driver.getDriverLifecycle()) {
            case JVM:
                jvmDriver = driver;
                break;
            case CLASS:
                classDrivers.put(driver.getTestClass(), driver);
                break;
            case METHOD:
            default:
                methodDrivers.put(new ClassAndTestName(driver.getTestClass(), driver.getTestName()), driver);
                break;
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
        public <T> SharedWebDriver getDriver(Class<T> testClass, String testName,
                DriverLifecycle driverLifecycle) {
            synchronized (this) {
                switch (driverLifecycle) {
                case JVM:
                    return jvmDriver;
                case CLASS:
                    return classDrivers.get(testClass);
                case METHOD:
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
                    if (jvmDriver == driver) { // NOPMD CompareObjectsWithEquals
                        if (jvmDriver.getDriver() != null) {
                            jvmDriver.getDriver().quit();
                        }
                        jvmDriver = null;
                    }
                    break;
                case CLASS:
                    SharedWebDriver classDriver = classDrivers.remove(driver.getTestClass());
                    if (classDriver == driver && classDriver.getDriver() != null) { // NOPMD CompareObjectsWithEquals
                        classDriver.getDriver().quit();
                    }
                    break;
                case METHOD:
                default:
                    SharedWebDriver testDriver = methodDrivers
                            .remove(new ClassAndTestName(driver.getTestClass(), driver.getTestName()));
                    if (testDriver == driver && testDriver.getDriver() != null) { // NOPMD CompareObjectsWithEquals
                        testDriver.getDriver().quit();
                    }
                    break;
                }
            }
        }

        /**
         * Get all WebDriver of this container.
         *
         * @return List of {@link SharedWebDriver}
         */
        public List<SharedWebDriver> getAllDrivers() {
            List<SharedWebDriver> drivers = new ArrayList<>();
            synchronized (this) {
                if (jvmDriver != null) {
                    drivers.add(jvmDriver);
                }
                for (SharedWebDriver classDriver : classDrivers.values()) {
                    drivers.add(classDriver);
                }

                for (SharedWebDriver testDriver : methodDrivers.values()) {
                    drivers.add(testDriver);
                }
            }
            return Collections.unmodifiableList(drivers);
        }

        /**
         * Get all shared WebDriver of this container for a given test class.
         *
         * @param testClass test class
         * @return list of shared WebDriver
         */
        public List<SharedWebDriver> getTestClassDrivers(Class<?> testClass) {
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

                return Collections.unmodifiableList(drivers);
            }
        }

        /**
         * Quit all shared web driver.
         */
        public void quitAll() {
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
            }
        }
    }

}
