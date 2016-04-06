package org.fluentlenium.core.wait;


import com.google.common.base.Predicate;
import org.fluentlenium.core.Fluent;

/**
 * Common class for all WaitMatcher instances.
 */
public abstract class AbstractWaitMatcher {
    /**
     * Build the final message from default message.
     *
     * @param defaultMessage the default message that will be used as base.
     * @return final message
     */
    protected String buildMessage(String defaultMessage) {
        return defaultMessage;
    }

    /**
     * Perform the wait.
     *
     * @param wait fluent wait object.
     * @param present predicate to wait for.
     * @param defaultMessage default message to use.
     */
    protected void until(FluentWait wait, Predicate<Fluent> present, String defaultMessage) {
        if (wait.useCustomMessage()) {
            wait.untilPredicate(present);
        } else {
            String message = buildMessage(defaultMessage);
            wait.withMessage(message).untilPredicate(present);
        }
    }
}
