package io.fluentlenium.adapter.sharedwebdriver;

import io.fluentlenium.configuration.ConfigurationProperties;import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Stores and handles {@link SharedWebDriver} instances for test methods.
 *
 * @see ConfigurationProperties.DriverLifecycle#METHOD
 * @see SharedWebdriverSingletonImpl
 */
public class MethodDriver implements FluentLeniumDriver {

    private final Map<ClassAndTestName, SharedWebDriver> methodDrivers = new HashMap<>();

    Map<ClassAndTestName, SharedWebDriver> getMethodDrivers() {
        return methodDrivers;
    }

    @Override
    public void quitDriver(SharedWebDriver sharedWebDriver) {
        SharedWebDriver testDriver = methodDrivers
                .remove(new ClassAndTestName(sharedWebDriver.getTestClass(), sharedWebDriver.getTestName()));
        quitDriver(sharedWebDriver, testDriver);
    }

    @Override
    public void addDriver(SharedWebDriver driver) {
        methodDrivers.put(new ClassAndTestName(driver.getTestClass(), driver.getTestName()), driver);
    }

    public <T> SharedWebDriver getDriver(Class<T> testClass, String testName) {
        return methodDrivers.get(new ClassAndTestName(testClass, testName));
    }

    private class ClassAndTestName {
        private final Class<?> testClass;
        private final String testName;

        ClassAndTestName(Class<?> testClass, String testName) {
            this.testClass = testClass;
            this.testName = testName;
        }

        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }

            if (!(obj instanceof ClassAndTestName)) {
                return false;
            }

            final ClassAndTestName other = (ClassAndTestName) obj;

            if (!other.canEqual(this)) {
                return false;
            }

            if (!Objects.equals(this.testClass, other.testClass)) {
                return false;
            }

            return Objects.equals(this.testName, other.testName);
        }

        boolean canEqual(final Object other) {
            return other instanceof ClassAndTestName;
        }

        public int hashCode() {
            final int prime = 59;
            int result = 1;
            final Object testClazz = this.testClass;
            result = result * prime + (testClazz == null ? 43 : testClazz.hashCode());
            final Object nameOfTest = this.testName;
            result = result * prime + (nameOfTest == null ? 43 : nameOfTest.hashCode());
            return result;
        }
    }
}
