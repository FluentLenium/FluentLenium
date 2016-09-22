package org.fluentlenium.core.wait;


import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import org.fluentlenium.core.FluentControl;

/**
 * Common class for all WaitMatcher instances.
 */
public abstract class AbstractWaitMatcher {
    /**
     * Perform the wait.
     *
     * @param wait    fluent wait object.
     * @param present predicate to wait for.
     * @param message message to use.
     */
    protected void until(FluentWait wait, Predicate<FluentControl> present, String message) {
        if (wait.useCustomMessage()) {
            wait.untilPredicate(present);
        } else {
            wait.withMessage(message).untilPredicate(present);
        }
    }

    /**
     * Perform the wait.
     *
     * @param wait            fluent wait object.
     * @param present         predicate to wait for.
     * @param messageSupplier default message to use.
     */
    protected void until(FluentWait wait, Predicate<FluentControl> present, Supplier<String> messageSupplier) {
        if (wait.useCustomMessage()) {
            wait.untilPredicate(present);
        } else {
            wait.withMessage(messageSupplier).untilPredicate(present);
        }
    }
}
