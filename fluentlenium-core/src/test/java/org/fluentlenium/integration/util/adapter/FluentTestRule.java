package org.fluentlenium.integration.util.adapter;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Equivalent of {@link org.junit.rules.TestWatcher}, but stop process if exception occurs on
 * starting method call.
 */
class FluentTestRule implements TestRule {

    public Statement apply(final Statement base, final Description description) {
        return new Statement() {

            @Override
            public void evaluate() throws Throwable {
                try {
                    starting(description);
                    base.evaluate();
                    succeeded(description);
                } catch (Throwable e) {
                    failed(e, description);
                    throw e;
                } finally {
                    finished(description);
                }
            }
        };
    }

    /**
     * Invoked when a test succeeds
     */
    protected void succeeded(Description description) {
        //Do nothing.
    }

    /**
     * Invoked when a test fails
     */
    protected void failed(Throwable e, Description description) {
        //Do nothing.
    }

    /**
     * Invoked when a test is about to start
     */
    protected void starting(Description description) {
        //Do nothing.
    }

    /**
     * Invoked when a test method finishes (whether passing or failing)
     */
    protected void finished(Description description) {
        //Do nothing.
    }
}
