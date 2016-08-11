package org.fluentlenium.adapter;

import org.fluentlenium.adapter.util.SharedDriverStrategy;

/**
 * Defines mutations of parameters used for {@link SharedWebDriverContainer} method calls.
 */
public interface SharedMutator {
    class EffectiveParameters<T> {
        private Class<T> testClass;
        private String testName;
        private SharedDriverStrategy strategy;

        public EffectiveParameters(Class<T> testClass, String testName, SharedDriverStrategy strategy) {
            this.testClass = testClass;
            this.testName = testName;
            this.strategy = strategy;
        }

        public Class<T> getTestClass() {
            return testClass;
        }

        public String getTestName() {
            return testName;
        }

        public SharedDriverStrategy getStrategy() {
            return strategy;
        }
    }

    /**
     * Effective parameters to use for {@link SharedWebDriverContainer}.
     *
     * @param testClass test class
     * @param testName test name
     * @param strategy shared driver strategy
     * @param <T> type of the test class
     * @return Effective parameters object.
     */
    <T> EffectiveParameters<T> getEffectiveParameters(Class<T> testClass, String testName, SharedDriverStrategy strategy);

}
