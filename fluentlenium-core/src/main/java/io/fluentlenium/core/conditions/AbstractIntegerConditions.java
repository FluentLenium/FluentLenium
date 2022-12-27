package io.fluentlenium.core.conditions;

import io.fluentlenium.core.conditions.message.Message;import io.fluentlenium.core.conditions.message.NotMessage;import io.fluentlenium.core.conditions.message.Message;
import io.fluentlenium.core.conditions.message.NotMessage;

/**
 * Common interface for Integer conditions.
 *
 * @param <T> type of condition
 * @param <C> type of interface extended by this common interface
 */
public interface AbstractIntegerConditions<T, C extends AbstractIntegerConditions> extends Conditions<T> {
    /**
     * Negates this condition object.
     *
     * @return a negated condition object
     */
    @Override
    @Negation
    C not();

    /**
     * Check that this is equal to given value
     *
     * @param value the value to compare with
     * @return true if is equals, false otherwise
     */
    @Message("is equal to {0}")
    @NotMessage("is not equal to {0}")
    boolean equalTo(int value);

    /**
     * Check that this is less than given value
     *
     * @param value the value to compare with
     * @return true if less than, false otherwise
     */
    @Message("is less than {0}")
    @NotMessage("is not less than {0}")
    boolean lessThan(int value);

    /**
     * Check that this is less than or equal given value
     *
     * @param value the value to compare with
     * @return true if less than or equal, false otherwise
     */
    @Message("is less than or equal to {0}")
    @NotMessage("is not less than or equal to {0}")
    boolean lessThanOrEqualTo(int value);

    /**
     * Check that this is greater than given value
     *
     * @param value the value to compare with
     * @return true if greater than, false otherwise
     */
    @Message("is greater than {0}")
    @NotMessage("is not greater than {0}")
    boolean greaterThan(int value);

    /**
     * Check that this is greater than or equal given value
     *
     * @param value the value to compare with
     * @return true if greater than or equal, false otherwise
     */
    @Message("is greater than or equal to {0}")
    @NotMessage("is not greater than or equal to {0}")
    boolean greaterThanOrEqualTo(int value);
}
