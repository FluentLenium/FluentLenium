package org.fluentlenium.adapter;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * All Junit Test should extends this class. It provides default parameters.
 */
public abstract class FluentTest extends FluentTestRunnerAdapter {
    @Rule
    public TestName name = new TestName();
    @Rule
    public TestRule watchman = new TestWatcher() {
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

    @AfterClass
    public static void afterClass() {
        FluentTestRunnerAdapter.releaseSharedDriver();
    }

}
