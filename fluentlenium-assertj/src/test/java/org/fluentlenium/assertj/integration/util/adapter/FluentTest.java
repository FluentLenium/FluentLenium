package org.fluentlenium.assertj.integration.util.adapter;

import org.fluentlenium.adapter.FluentTestRunnerAdapter;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Duplicate of fluentlenium-junit adapter classes to avoid maven dependency cycle.
 */
public class FluentTest extends FluentTestRunnerAdapter {
    @Rule
    public TestRule watchman = new FluentTestRule() {

        @Override
        public void starting(final Description description) {
            super.starting(description);
            FluentTest.this.starting(description.getTestClass(), description.getDisplayName());
        }

        @Override
        public void finished(final Description description) {
            super.finished(description);
            FluentTest.this.finished(description.getTestClass(), description.getDisplayName());
        }

        @Override
        public void failed(final Throwable e, final Description description) {
            super.failed(e, description);
            FluentTest.this.failed(e, description.getTestClass(), description.getDisplayName());
        }
    };

    //CHECKSTYLE.OFF: VisibilityModifier
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
