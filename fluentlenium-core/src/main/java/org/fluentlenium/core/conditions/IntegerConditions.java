package org.fluentlenium.core.conditions;

import org.fluentlenium.core.conditions.message.Message;

/**
 * Conditions API for Integer.
 */
public interface IntegerConditions extends Conditions<Integer> {
    @Override
    IntegerConditions not();

    /**
     * Check that this is equal to given value
     *
     * @param value the value to compare with
     * @return true if is equals, false otherwise
     */
    @Message("should [not ]be equal to {0}")
    boolean equalTo(final int value);

    /**
     * Check that this is less than given value
     *
     * @param value the value to compare with
     * @return true if less than, false otherwise
     */
    @Message("should [not ]be less than {0}")
    boolean lessThan(final int value);

    /**
     * Check that this is less than or equal given value
     *
     * @param value the value to compare with
     * @return true if less than or equal, false otherwise
     */
    @Message("should [not ]be less than or equal to {0}")
    boolean lessThanOrEqualTo(final int value);

    /**
     * Check that this is greater than given value
     *
     * @param value the value to compare with
     * @return true if greater than, false otherwise
     */
    @Message("should [not ]be greater than {0}")
    boolean greaterThan(final int value);

    /**
     * Check that this is greater than or equal given value
     *
     * @param value the value to compare with
     * @return true if greater than or equal, false otherwise
     */
    @Message("should [not ]be greater than or equal to {0}")
    boolean greaterThanOrEqualTo(final int value);

}
