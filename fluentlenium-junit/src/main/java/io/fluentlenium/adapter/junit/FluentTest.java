package io.fluentlenium.adapter.junit;

import io.fluentlenium.adapter.FluentTestRunnerAdapter;
import io.fluentlenium.utils.SeleniumVersionChecker;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * JUnit 4 FluentLenium Test Runner Adapter.
 * <p>
 * Extends this class to provide FluentLenium support to your JUnit Test class.
 */
public class FluentTest extends FluentTestRunnerAdapter {
    /**
     * Fluent test adapter JUnit rule.
     */
    @Rule
    public TestRule watchman = new FluentTestRule(this) {

        @Override
        public void starting(Description description) {
            SeleniumVersionChecker.checkSeleniumVersion();
            super.starting(description);
            FluentTest.this.starting(description.getTestClass(), description.getDisplayName());
        }

        @Override
        public void finished(Description description) {
            super.finished(description);
            FluentTest.this.finished(description.getTestClass(), description.getDisplayName());
        }

        @Override
        public void failed(Throwable e, Description description) {
            super.failed(e, description);
            FluentTest.this.failed(e, description.getTestClass(), description.getDisplayName());
        }
    };

    //CHECKSTYLE.OFF: VisibilityModifier
    /**
     * Fluent test adapter JUnit class rule.
     */
    @ClassRule
    public static TestRule classWatchman = (base, description) -> new Statement() {

        @Override
        public void evaluate() throws Throwable {
            try {
                base.evaluate();
            } finally {
                classDriverCleanup(description.getTestClass());
            }
        }
    };
    //CHECKSTYLE.ON: VisibilityModifier
}
