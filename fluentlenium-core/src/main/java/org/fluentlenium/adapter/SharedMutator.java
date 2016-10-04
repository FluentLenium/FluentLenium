package org.fluentlenium.adapter;

import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;

/**
 * Defines mutations of parameters used for {@link SharedWebDriverContainer} method calls.
 */
public interface SharedMutator {
    class EffectiveParameters<T> {
        private final Class<T> testClass;
        private final String testName;
        private final DriverLifecycle driverLifecycle;

        public EffectiveParameters(final Class<T> testClass, final String testName, final DriverLifecycle driverLifecycle) {
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
