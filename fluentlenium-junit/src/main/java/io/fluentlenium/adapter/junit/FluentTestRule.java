package io.fluentlenium.adapter.junit;

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
public class FluentTestRule implements TestRule {
    private final Object target;
    private final TestClass testClass;
    private final List<FrameworkMethod> afters;

    boolean customAftersTriggered;

    /**
     * Creates a new fluent test rule.
     *
     * @param target target of the rule.
     */
    public FluentTestRule(Object target) {
        this.target = target;
        testClass = new TestClass(target.getClass());
        afters = testClass.getAnnotatedMethods(After.class);
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {

            @Override
            public void evaluate() throws Throwable {
                List<Throwable> errors = new ArrayList<>();
                try {
                    starting(description);
                    base.evaluate();
                    succeeded(description);
                } catch (Throwable e) {
                    errors.add(e);
                    try {
                        failed(e, description);
                    } catch (Throwable failedException) {
                        errors.add(failedException);
                    }
                    triggerCustomAfters(errors);
                } finally {
                    try {
                        if (!customAftersTriggered) {
                            triggerCustomAfters(errors);
                        }
                        finished(description);
                    } catch (Throwable failedException) {
                        errors.add(failedException);
                    }
                }
                MultipleFailureException.assertEmpty(errors);
            }
        };
    }

    void triggerCustomAfters(List<Throwable> errors) {
        for (FrameworkMethod each : afters) {
            try {
                each.invokeExplosively(target);
            } catch (Throwable afterException) {
                errors.add(afterException);
            }
        }
        customAftersTriggered = true;
    }

    /**
     * Invoked when a test succeeds.
     *
     * @param description test description
     */
    protected void succeeded(Description description) {
        //Do nothing.
    }

    /**
     * Invoked when a test fails
     *
     * @param e           exception
     * @param description test description
     */
    protected void failed(Throwable e, Description description) {
        //Do nothing.
    }

    /**
     * Invoked when a test is about to start
     *
     * @param description test description
     */
    protected void starting(Description description) {
        //Do nothing.
    }

    /**
     * Invoked when a test method finishes (whether passing or failing)
     *
     * @param description test description
     */
    protected void finished(Description description) {
        //Do nothing.
    }
}
