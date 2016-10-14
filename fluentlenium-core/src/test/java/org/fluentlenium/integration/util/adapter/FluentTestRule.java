package org.fluentlenium.integration.util.adapter;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Equivalent of {@link org.junit.rules.TestWatcher}, but stop process if exception occurs on
 * starting method call.
 * <p>
 * It also supports {@link After} annotations.
 */
class FluentTestRule implements TestRule {
    private final Object target;
    private final TestClass testClass;
    private final List<FrameworkMethod> afters;

    /**
     * Creates a new fluent test rule.
     *
     * @param target target of the rule.
     */
    FluentTestRule(final Object target) {
        this.target = target;
        this.testClass = new TestClass(target.getClass());
        this.afters = testClass.getAnnotatedMethods(After.class);
    }

    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {

            @Override
            public void evaluate() throws Throwable {
                final List<Throwable> errors = new ArrayList<>();
                try {
                    starting(description);
                    base.evaluate();
                    succeeded(description);
                } catch (final Throwable e) {
                    errors.add(e);
                    try {
                        failed(e, description);
                    } catch (final Throwable failedException) {
                        errors.add(failedException);
                    }
                    for (final FrameworkMethod each : afters) {
                        try {
                            each.invokeExplosively(target);
                        } catch (final Throwable afterException) {
                            errors.add(afterException);
                        }
                    }
                } finally {
                    try {
                        finished(description);
                    } catch (final Throwable failedException) {
                        errors.add(failedException);
                    }
                }
                MultipleFailureException.assertEmpty(errors);
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
