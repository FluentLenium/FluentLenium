package org.fluentlenium.adapter.junit;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Equivalent of {@link org.junit.rules.TestWatcher}, but stop process if exception occurs on
 * starting method call.
 */
class FluentTestRule implements TestRule {

    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {

            @Override
            public void evaluate() throws Throwable {
                try {
                    starting(description);
                    base.evaluate();
                    succeeded(description);
                } catch (final Throwable e) {
                    failed(e, description);
                    throw e;
                } finally {
                    finished(description);
                }
            }
        };
    }

    /**
     * Invoked when a test succeeds.
     *
     * @param description test description
     */
    protected void succeeded(final Description description) {
        //Do nothing.
    }

    /**
     * Invoked when a test fails
     *
     * @param e           exception
     * @param description test description
     */
    protected void failed(final Throwable e, final Description description) {
        //Do nothing.
    }

    /**
     * Invoked when a test is about to start
     *
     * @param description test description
     */
    protected void starting(final Description description) {
        //Do nothing.
    }

    /**
     * Invoked when a test method finishes (whether passing or failing)
     *
     * @param description test description
     */
    protected void finished(final Description description) {
        //Do nothing.
    }
}
