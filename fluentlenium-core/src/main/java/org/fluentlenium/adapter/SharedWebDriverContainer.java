package org.fluentlenium.adapter;

import com.google.common.base.Supplier;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.experimental.Delegate;
import org.fluentlenium.adapter.util.SharedWebDriverContainerShutdownHook;
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
    INSTANCE;

    @EqualsAndHashCode
    @AllArgsConstructor
    private static class ClassAndTestName {
        private Class<?> testClass;
        private String testName;
    }

    private final SharedWebDriverContainerShutdownHook shutdownHook;

    SharedWebDriverContainer() {
        shutdownHook = new SharedWebDriverContainerShutdownHook("SharedWebDriverContainerShutdownHook");
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }

    @Delegate
    private final Impl impl = new Impl();

    static class Impl {
        private SharedWebDriver jvmDriver;

        private final Map<Class<?>, SharedWebDriver> classDrivers = new HashMap<>();

        private final Map<ClassAndTestName, SharedWebDriver> methodDrivers = new HashMap<>();


        /**
         * Get an existing or create a new driver for the given test, with the given shared driver
         * strategy.
         *
         * @param webDriverFactory Supplier supplying new WebDriver instances
         * @param testClass        Test class
         * @param testName         Test name
         * @param driverLifecycle  WebDriver lifecycle
         * @return
         */
        public synchronized <T> SharedWebDriver getOrCreateDriver(Supplier<WebDriver> webDriverFactory, Class<T> testClass, String testName, DriverLifecycle driverLifecycle) {
            SharedWebDriver driver = getDriver(testClass, testName, driverLifecycle);
            if (driver == null) {
                driver = createDriver(webDriverFactory, testClass, testName, driverLifecycle);
                registerDriver(driver);
            }
            return driver;
        }

        private <T> SharedWebDriver createDriver(Supplier<WebDriver> webDriverFactory, Class<T> testClass, String testName, DriverLifecycle driverLifecycle) {
            WebDriver webDriver = webDriverFactory.get();
            SharedWebDriver sharedWebDriver = new SharedWebDriver(webDriver, testClass, testName, driverLifecycle);
            return sharedWebDriver;
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

        public synchronized <T> SharedWebDriver getDriver(Class<T> testClass, String testName, DriverLifecycle driverLifecycle) {
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

        public synchronized void quit(SharedWebDriver driver) {
            switch (driver.getDriverLifecycle()) {
                case JVM:
                    if (jvmDriver == driver) {
                        if (jvmDriver.getDriver() != null) {
                            jvmDriver.getDriver().quit();
                        }
                        jvmDriver = null;
                    }
                    break;
                case CLASS:
                    SharedWebDriver classDriver = classDrivers.remove(driver.getTestClass());
                    if (classDriver == driver && classDriver.getDriver() != null) {
                        classDriver.getDriver().quit();
                    }
                    break;
                case METHOD:
                default:
                    SharedWebDriver testDriver = methodDrivers
                            .remove(new ClassAndTestName(driver.getTestClass(), driver.getTestName()));
                    if (testDriver == driver && testDriver.getDriver() != null) {
                        testDriver.getDriver().quit();
                    }
                    break;
            }
        }

        /**
         * Get all WebDriver of this container.
         *
         * @return List of {@link SharedWebDriver}
         */
        public synchronized List<SharedWebDriver> getAllDrivers() {
            List<SharedWebDriver> drivers = new ArrayList<>();

            if (jvmDriver != null) {
                drivers.add(jvmDriver);
            }
            for (SharedWebDriver classDriver : classDrivers.values()) {
                drivers.add(classDriver);
            }

            for (SharedWebDriver testDriver : methodDrivers.values()) {
                drivers.add(testDriver);
            }

            return Collections.unmodifiableList(drivers);
        }

        /**
         * Get all WebDriver of this container for given class.
         */
        public synchronized List<SharedWebDriver> getTestClassDrivers(Class<?> testClass) {
            List<SharedWebDriver> drivers = new ArrayList<>();

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

        public synchronized void quitAll() {
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
