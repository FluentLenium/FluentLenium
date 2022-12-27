package io.fluentlenium.core.wait;

import io.fluentlenium.core.FluentControl;
import io.fluentlenium.core.conditions.Conditions;
import io.fluentlenium.core.conditions.message.MessageProxy;
import io.fluentlenium.core.FluentControl;
import io.fluentlenium.core.conditions.Conditions;
import io.fluentlenium.core.conditions.message.MessageProxy;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Common class for all wait conditions instances.
 */
public class BaseWaitConditions {
    /**
     * Build the final message from default message.
     *
     * @return final message
     */
    protected Function<String, String> messageCustomizer() {
        return Function.identity();
    }

    /**
     * Perform the wait.
     *
     * @param wait    fluent wait object.
     * @param present predicate to wait for.
     * @param message message to use.
     */
    protected void until(FluentWait wait, Predicate<FluentControl> present, String message) {
        if (wait.hasMessageDefined()) {
            wait.untilPredicate(present);
        } else {
            message = messageCustomizer().apply(message);
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
        if (wait.hasMessageDefined()) {
            wait.untilPredicate(present);
        } else {
            Supplier<String> customMessageSupplier = () -> messageCustomizer().apply(messageSupplier.get());
            wait.withMessage(customMessageSupplier).untilPredicate(present);
        }
    }

    /**
     * Perform the wait.
     *
     * @param wait              fluent wait object
     * @param condition         condition object to wait for
     * @param messageBuilder    message builder matching the condition object
     * @param conditionFunction condition fonction
     * @param <T>               type of the condition.
     */
    protected <T extends Conditions<?>> void until(FluentWait wait, T condition, T messageBuilder,
                                                   Function<T, Boolean> conditionFunction) {
        Predicate<FluentControl> predicate = input -> conditionFunction.apply(condition);
        Supplier<String> messageSupplier = () -> {
            conditionFunction.apply(messageBuilder);
            return MessageProxy.message(messageBuilder);
        };

        until(wait, predicate, messageSupplier);
    }
}
