package org.fluentlenium.adapter;

import org.fluentlenium.adapter.util.SharedDriverStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.internal.WrapsDriver;

/**
 * A reference to a {@link WebDriver} used by a test.
 */
public class SharedWebDriver implements WrapsDriver {

    private final WebDriver driver;

    private final Class<?> testClass;

    private final String testName;

    private final SharedDriverStrategy sharedDriverStrategy;

    public SharedWebDriver(WebDriver driver, Class<?> testClass, String testName,
                           SharedDriverStrategy sharedDriverStrategy) {
        this.driver = driver;
        this.testClass = testClass;
        this.testName = testName;
        this.sharedDriverStrategy = sharedDriverStrategy;
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

    public SharedDriverStrategy getSharedDriverStrategy() {
        return sharedDriverStrategy;
    }

    @Override
    public String toString() {
        return "SharedWebDriver{" + "driver=" + driver + ", testClass=" + testClass + ", testName='"
                + testName + '\'' + ", sharedDriverStrategy=" + sharedDriverStrategy + '}';
    }

}
