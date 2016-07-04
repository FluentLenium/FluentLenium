package org.fluentlenium.core.conditions;

import com.google.common.base.Predicate;
import org.fluentlenium.core.domain.FluentWebElement;

/**
 * Common interface for conditions.
 *
 * @param <T>
 */
public interface Conditions<T> {
    /**
     * Check that the given predicate is verified.
     *
     * @param predicate predicate to check
     */
    boolean isVerified(Predicate<T> predicate);

    /**
     * Negates this condition object.
     *
     * @return a negated condition object
     */
    Conditions<T> not();
}
