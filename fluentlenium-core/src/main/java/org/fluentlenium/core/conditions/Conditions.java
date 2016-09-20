package org.fluentlenium.core.conditions;

import com.google.common.base.Predicate;

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
    boolean verify(Predicate<T> predicate);

    /**
     * Negates this condition object.
     *
     * @return a negated condition object
     */
    Conditions<T> not();
}
