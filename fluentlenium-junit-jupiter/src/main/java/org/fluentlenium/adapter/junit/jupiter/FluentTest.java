package org.fluentlenium.adapter.junit.jupiter;

import org.fluentlenium.SeleniumVersionChecker;
import org.fluentlenium.adapter.FluentTestRunnerAdapter;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * JUnit 5 FluentLenium Test Runner Adapter.
 * <p>
 * Extends this class to provide FluentLenium support to your JUnit Test class.
 */
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
