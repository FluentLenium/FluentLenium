package org.fluentlenium.adapter;

import org.fluentlenium.adapter.sharedwebdriver.SharedWebDriverContainer;
import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;

/**
 * Defines mutations of parameters used for {@link SharedWebDriverContainer} method calls.
 */
public interface SharedMutator {
    /**
     * Effective parameters used by the test.
     *
     * @param <T> type of test
     */
    class EffectiveParameters<T> {
        private final Class<T> testClass;
        private final String testName;
        private final DriverLifecycle driverLifecycle;

        /**
         * Creates new effective parameters
         *
         * @param testClass       test class
         * @param testName        test name
         * @param driverLifecycle driver lifecycle
         */
        public EffectiveParameters(Class<T> testClass, String testName, DriverLifecycle driverLifecycle) {
            this.testClass = testClass;
            this.testName = testName;
            this.driverLifecycle = driverLifecycle;
        }

        /**
         * Get the test class
         *
         * @return test class
         */
        public Class<T> getTestClass() {
            return testClass;
        }

        /**
         * Get the test name
         *
         * @return test name
         */
        public String getTestName() {
            return testName;
        }

        /**
         * Get the driver lifecycle
         *
         * @return driver lifecycle
         */
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
