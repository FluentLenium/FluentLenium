package org.fluentlenium.adapter;

import com.google.common.base.Supplier;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.experimental.Delegate;
import org.fluentlenium.adapter.util.SharedDriverStrategy;
import org.fluentlenium.adapter.util.SharedWebDriverContainerShutdownHook;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        private SharedWebDriver onceDriver;

        private final Map<Class<?>, SharedWebDriver> classDrivers = new HashMap<>();

        private final Map<ClassAndTestName, SharedWebDriver> testDrivers = new HashMap<>();


        /**
         * Get an existing or create a new driver for the given test, with the given shared driver
         * strategy.
         *
         * @param webDriverFactory Supplier fournissant de nouvelles instances de WebDriver.
         * @param testClass        classe du test
         * @param testName         nom du test
         * @param strategy         stratégie
         * @return
         */
        public synchronized <T> SharedWebDriver getOrCreateDriver(Supplier<WebDriver> webDriverFactory, Class<T> testClass, String testName, SharedDriverStrategy strategy) {
            SharedWebDriver driver = getDriver(testClass, testName, strategy);
            if (driver == null) {
                driver = createDriver(webDriverFactory, testClass, testName, strategy);
                registerDriver(driver);
            }
            return driver;
        }

        private <T> SharedWebDriver createDriver(Supplier<WebDriver> webDriverFactory, Class<T> testClass, String testName, SharedDriverStrategy strategy) {
            WebDriver webDriver = webDriverFactory.get();
            SharedWebDriver sharedWebDriver = new SharedWebDriver(webDriver, testClass, testName, strategy);
            return sharedWebDriver;
        }

        private void registerDriver(SharedWebDriver driver) {
            switch (driver.getSharedDriverStrategy()) {
                case ONCE:
                    onceDriver = driver;
                    break;
                case PER_CLASS:
                    classDrivers.put(driver.getTestClass(), driver);
                    break;
                case PER_METHOD:
                default:
                    testDrivers.put(new ClassAndTestName(driver.getTestClass(), driver.getTestName()), driver);
                    break;
            }
        }

        public synchronized <T> SharedWebDriver getDriver(Class<T> testClass, String testName, SharedDriverStrategy strategy) {
            switch (strategy) {
                case ONCE:
                    return onceDriver;
                case PER_CLASS:
                    return classDrivers.get(testClass);
                case PER_METHOD:
                default:
                    return testDrivers.get(new ClassAndTestName(testClass, testName));
            }
        }

        public synchronized void quit(SharedWebDriver driver) {
            switch (driver.getSharedDriverStrategy()) {
                case ONCE:
                    if (onceDriver == driver) {
                        if (onceDriver.getDriver() != null) {
                            onceDriver.getDriver().quit();
                        }
                        onceDriver = null;
                    }
                    break;
                case PER_CLASS:
                    SharedWebDriver classDriver = classDrivers.remove(driver.getTestClass());
                    if (classDriver == driver && classDriver.getDriver() != null) {
                        classDriver.getDriver().quit();
                    }
                    break;
                case PER_METHOD:
                default:
                    SharedWebDriver testDriver = testDrivers
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

            if (onceDriver != null) {
                drivers.add(onceDriver);
            }
            for (SharedWebDriver classDriver : classDrivers.values()) {
                drivers.add(classDriver);
            }

            for (SharedWebDriver testDriver : testDrivers.values()) {
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

            for (SharedWebDriver testDriver : testDrivers.values()) {
                if (testDriver.getTestClass() == testClass) {
                    drivers.add(testDriver);
                }
            }

            return Collections.unmodifiableList(drivers);
        }

        public synchronized void quitAll() {
            if (onceDriver != null) {
                onceDriver.getDriver().quit();
                onceDriver = null;
            }

            Iterator<SharedWebDriver> classDriversIterator = classDrivers.values().iterator();
            while (classDriversIterator.hasNext()) {
                classDriversIterator.next().getDriver().quit();
                classDriversIterator.remove();
            }

            Iterator<SharedWebDriver> testDriversIterator = testDrivers.values().iterator();
            while (testDriversIterator.hasNext()) {
                testDriversIterator.next().getDriver().quit();
                testDriversIterator.remove();
            }
        }
    }

}
