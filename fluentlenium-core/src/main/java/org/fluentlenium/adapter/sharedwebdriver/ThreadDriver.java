package org.fluentlenium.adapter.sharedwebdriver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Stores and handles {@link SharedWebDriver} instances for test methods and thred ids.
 *
 * @see org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle#THREAD
 * @see SharedWebdriverSingletonImpl
 */
public class ThreadDriver implements FluentLeniumDriver {

    private final Map<ClassAndTestNameWithThreadId, SharedWebDriver> threadDrivers = new HashMap<>();

    Map<ClassAndTestNameWithThreadId, SharedWebDriver> getThreadDrivers() {
        return threadDrivers;
    }

    @Override
    public void quitDriver(SharedWebDriver driver) {
        List<Map.Entry<ClassAndTestNameWithThreadId, SharedWebDriver>> threadDriversToClose
                = getThreadDriversToClose(driver);
        threadDriversToClose.forEach(item -> closeThreadDriver(driver, item));
    }

    @Override
    public void addDriver(SharedWebDriver driver) {
        threadDrivers.put(new ClassAndTestNameWithThreadId(driver.getTestClass(), driver.getTestName(),
                Thread.currentThread().getId()), driver);
    }

    public <T> SharedWebDriver getDriver(Class<T> testClass, String testName, long id) {
        return threadDrivers.get(new ClassAndTestNameWithThreadId(testClass, testName, id));
    }

    private void closeThreadDriver(SharedWebDriver sharedWebDriver,
                                   Map.Entry<ClassAndTestNameWithThreadId, SharedWebDriver> item) {
        SharedWebDriver testThreadDriver = threadDrivers.remove(item.getKey());
        quitDriver(sharedWebDriver, testThreadDriver);
    }

    private List<Map.Entry<ClassAndTestNameWithThreadId, SharedWebDriver>> getThreadDriversToClose(SharedWebDriver driver) {
        return threadDrivers.entrySet()
                .stream()
                .filter(entry -> entry.getKey().testClass.equals(driver.getTestClass())
                        && entry.getKey().testName.equals(driver.getTestName())
                        && entry.getValue().getDriver().equals(driver.getDriver()))
                .collect(Collectors.toList());
    }

    private class ClassAndTestNameWithThreadId {
        protected final Class<?> testClass;
        protected final String testName;
        private final Long threadId;

        ClassAndTestNameWithThreadId(Class<?> testClass, String testName, Long threadId) {
            this.testClass = testClass;
            this.testName = testName;
            this.threadId = threadId;
        }

        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }

            if (!(obj instanceof ClassAndTestNameWithThreadId)) {
                return false;
            }

            final ClassAndTestNameWithThreadId other = (ClassAndTestNameWithThreadId) obj;

            if (!other.canEqual(this)) {
                return false;
            }

            if (!Objects.equals(this.testClass, other.testClass)) {
                return false;
            }

            if (!Objects.equals(this.testName, other.testName)) {
                return false;
            }

            return Objects.equals(this.threadId, other.threadId);
        }

        boolean canEqual(final Object other) {
            return other instanceof ClassAndTestNameWithThreadId;
        }

        public int hashCode() {
            final int prime = 59;
            int result = 1;
            final Object testClazz = this.testClass;
            result = result * prime + (testClazz == null ? 43 : testClazz.hashCode());
            final Object nameOfTest = this.testName;
            result = result * prime + (nameOfTest == null ? 43 : nameOfTest.hashCode());
            final Object idOfThread = this.threadId;
            result = result * prime + (idOfThread == null ? 43 : idOfThread.hashCode());
            return result;
        }
    }
}
