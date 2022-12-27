package io.fluentlenium.core.conditions;

import java.util.function.Predicate;

import io.fluentlenium.core.conditions.message.Message;
import io.fluentlenium.core.conditions.message.NotMessage;
import io.fluentlenium.core.conditions.message.Message;
import io.fluentlenium.core.conditions.message.NotMessage;

/**
 * Common interface for conditions.
 *
 * @param <T> type of condition
 */
public interface Conditions<T> {
    /**
     * Check that the given predicate is verified against this condition object.
     *
     * @param predicate predicate to check
     * @return true if the predicated is checked, false otherwise
     */
    @NotMessage("does not verify predicate {0}")
    @Message("verify predicate {0}")
    boolean verify(Predicate<T> predicate);

    /**
     * Negates this condition object.
     *
     * @return a negated condition object
     */
    @Negation
    Conditions<T> not();
}
