package org.fluentlenium.adapter;

import org.junit.AssumptionViolatedException;
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
                } catch (@SuppressWarnings("deprecation") org.junit.internal.AssumptionViolatedException e) {
                    skipped(e, description);
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
    }

    /**
     * Invoked when a test fails
     */
    protected void failed(Throwable e, Description description) {
    }

    /**
     * Invoked when a test is skipped due to a failed assumption.
     */
    @SuppressWarnings("deprecation")
    protected void skipped(AssumptionViolatedException e, Description description) {
        // For backwards compatibility with JUnit 4.11 and earlier, call the legacy version
        org.junit.internal.AssumptionViolatedException asInternalException = e;
        skipped(asInternalException, description);
    }

    /**
     * Invoked when a test is skipped due to a failed assumption.
     *
     * @deprecated use {@link #skipped(AssumptionViolatedException, Description)}
     */
    @Deprecated
    protected void skipped(org.junit.internal.AssumptionViolatedException e,
            Description description) {
    }

    /**
     * Invoked when a test is about to start
     */
    protected void starting(Description description) {
    }

    /**
     * Invoked when a test method finishes (whether passing or failing)
     */
    protected void finished(Description description) {
    }
}
