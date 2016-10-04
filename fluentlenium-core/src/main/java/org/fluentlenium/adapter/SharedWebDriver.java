package org.fluentlenium.adapter;

import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.internal.WrapsDriver;

/**
 * A reference to a {@link WebDriver} used by a test.
 */
public class SharedWebDriver implements WrapsDriver {

    private final WebDriver driver;

    private final Class<?> testClass;

    private final String testName;

    private final DriverLifecycle driverLifecycle;

    public SharedWebDriver(final WebDriver driver, final Class<?> testClass, final String testName,
            final DriverLifecycle driverLifecycle) {
        this.driver = driver;
        this.testClass = testClass;
        this.testName = testName;
        this.driverLifecycle = driverLifecycle;
    }

    @Override
    public WebDriver getWrappedDriver() {
        return this.driver;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public Class<?> getTestClass() {
        return testClass;
    }

    public String getTestName() {
        return testName;
    }

    public DriverLifecycle getDriverLifecycle() {
        return driverLifecycle;
    }

    @Override
    public String toString() {
        return "SharedWebDriver{" + "driver=" + driver + ", testClass=" + testClass + ", testName='" + testName + '\''
                + ", driverLifecycle=" + driverLifecycle + '}';
    }

}
