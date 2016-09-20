package org.fluentlenium.core.conditions;

import com.google.common.base.Predicate;
import org.fluentlenium.core.conditions.message.Message;

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
    @Message("should [not ]verify predicate {0}")
    boolean verify(Predicate<T> predicate);

    /**
     * Negates this condition object.
     *
     * @return a negated condition object
     */
    Conditions<T> not();
}
