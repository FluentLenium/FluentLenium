package org.fluentlenium.adapter;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * All Junit Test should extends this class. It provides default parameters.
 */
public abstract class FluentTest extends FluentTestRunnerAdapter {

    @Rule
    public TestName name = new TestName();

    private static Class<?> testClass;

    public FluentTest() {
        testClass = this.getClass();
    }

    @Rule
    public TestRule watchman = new FluentTestRule() {

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

}
