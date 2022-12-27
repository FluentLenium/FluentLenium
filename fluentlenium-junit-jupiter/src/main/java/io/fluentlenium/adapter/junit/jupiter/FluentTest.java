package io.fluentlenium.adapter.junit.jupiter;

import io.fluentlenium.adapter.FluentTestRunnerAdapter;
import io.fluentlenium.utils.SeleniumVersionChecker;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * JUnit 5 FluentLenium Test Runner Adapter.
 * <p>
 * Extends this class to provide FluentLenium support to your JUnit Test class.
 */
@SuppressWarnings("PMD.MethodNamingConventions")
@ExtendWith({FluentJUnitJupiter.class, MockitoExtension.class})
public class FluentTest extends FluentTestRunnerAdapter {
    // JUnitJupiter support, called from FluentJUnitJupiter

    /*package*/ void _starting(Class<?> testClass, String testName) {
        SeleniumVersionChecker.checkSeleniumVersion();
        starting(testClass, testName);
    }

    /*package*/ void _finished(Class<?> testClass, String testName) {
        finished(testClass, testName);
    }

    /*package*/ void _failed(Throwable e, Class<?> testClass, String testName) {
        failed(e, testClass, testName);
        finished(testClass, testName);
    }
}
