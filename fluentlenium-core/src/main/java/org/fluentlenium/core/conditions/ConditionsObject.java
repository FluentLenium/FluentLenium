package org.fluentlenium.core.conditions;

/**
 * Wrap object inside a condition.
 *
 * @param <T> type of the object
 */
public interface ConditionsObject<T> {
    /**
     * Get the actual object.
     *
     * @return actual object on which conditions are performed.
     */
    T getActualObject();
}
