package io.fluentlenium.adapter;

import io.fluentlenium.adapter.sharedwebdriver.SharedWebDriver;
import io.fluentlenium.adapter.sharedwebdriver.SharedWebDriverContainer;
import io.fluentlenium.adapter.sharedwebdriver.SharedWebDriverContainer;
import io.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;

/**
 * Defines mutations of parameters used for {@link SharedWebDriverContainer} method calls.
 */
public interface SharedMutator {

    /**
     * Effective parameters used by the test.
     * <p>
     * This type is used for creating and identifying {@link SharedWebDriver}
     * instances.
     *
     * @param <T> type of test
     */
    class EffectiveParameters<T> {
        private final Class<T> testClass;
        private final String testName;
        private final DriverLifecycle driverLifecycle;

        public EffectiveParameters(Class<T> testClass, String testName, DriverLifecycle driverLifecycle) {
            this.testClass = testClass;
            this.testName = testName;
            this.driverLifecycle = driverLifecycle;
        }

        public Class<T> getTestClass() {
            return testClass;
        }

        public String getTestName() {
            return testName;
        }

        public DriverLifecycle getDriverLifecycle() {
            return driverLifecycle;
        }
    }

    /**
     * Effective parameters to use for {@link SharedWebDriverContainer}.
     *
     * @param testClass       test class
     * @param testName        test name
     * @param driverLifecycle WebDriver lifecycle
     * @param <T>             type of the test class
     * @return Effective parameters object.
     */
    <T> EffectiveParameters<T> getEffectiveParameters(Class<T> testClass, String testName, DriverLifecycle driverLifecycle);

}
