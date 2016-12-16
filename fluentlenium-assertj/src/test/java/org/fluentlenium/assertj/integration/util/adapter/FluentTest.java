package org.fluentlenium.assertj.integration.util.adapter;

import org.fluentlenium.adapter.FluentTestRunnerAdapter;
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
    public static TestRule classWatchman = new TestRule() {

        @Override
        public Statement apply(final Statement base, final Description description) {
            return new Statement() {

                @Override
                public void evaluate() throws Throwable {
                    try {
                        base.evaluate();
                    } finally {
                        afterClass(description.getTestClass());
                    }
                }
            };
        }
    };
    //CHECKSTYLE.ON: VisibilityModifier

}
