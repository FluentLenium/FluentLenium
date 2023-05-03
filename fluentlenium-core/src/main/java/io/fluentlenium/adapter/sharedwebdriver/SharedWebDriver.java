package io.fluentlenium.adapter.sharedwebdriver;

import io.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WrapsDriver;

/**
 * A reference to a shared {@link WebDriver} used by a test.
 */
public class SharedWebDriver implements WrapsDriver {

    private final WebDriver driver;

    private final Class<?> testClass;

    private final String testName;

    private final DriverLifecycle driverLifecycle;

    /**
     * Creates a new shared WebDriver.
     *
     * @param driver          selenium WebDriver
     * @param testClass       test class
     * @param testName        test name
     * @param driverLifecycle driver lifecycle
     */
    public SharedWebDriver(WebDriver driver, Class<?> testClass, String testName, DriverLifecycle driverLifecycle) {
        this.driver = driver;
        this.testClass = testClass;
        this.testName = testName;
        this.driverLifecycle = driverLifecycle;
    }

    @Override
    public WebDriver getWrappedDriver() {
        return driver;
    }

    /**
     * Get the underlying driver.
     *
     * @return selenium driver
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Get the test class.
     *
     * @return test class
     */
    public Class<?> getTestClass() {
        return testClass;
    }

    /**
     * Get the test name.
     *
     * @return test name
     */
    public String getTestName() {
        return testName;
    }

    /**
     * Get the driver lifecycle of this shared driver.
     *
     * @return driver lifecycle
     */
    public DriverLifecycle getDriverLifecycle() {
        return driverLifecycle;
    }

    @Override
    public String toString() {
        return "SharedWebDriver{" + "driver=" + driver + ", testClass=" + testClass + ", testName='" + testName + '\''
                + ", driverLifecycle=" + driverLifecycle + '}';
    }

}
