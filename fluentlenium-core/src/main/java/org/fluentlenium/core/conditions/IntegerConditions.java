package org.fluentlenium.core.conditions;

import org.fluentlenium.core.conditions.message.Message;
import org.fluentlenium.core.conditions.message.NotMessage;

/**
 * Conditions API for Integer.
 */
public interface IntegerConditions extends Conditions<Integer> {
    @Override
    @Negation
    IntegerConditions not();

    /**
     * Check that this is equal to given value
     *
     * @param value the value to compare with
     * @return true if is equals, false otherwise
     */
    @Message("is equal to {0}")
    @NotMessage("is not equal to {0}")
    boolean equalTo(final int value);

    /**
     * Check that this is less than given value
     *
     * @param value the value to compare with
     * @return true if less than, false otherwise
     */
    @Message("is less than {0}")
    @NotMessage("is not less than {0}")
    boolean lessThan(final int value);

    /**
     * Check that this is less than or equal given value
     *
     * @param value the value to compare with
     * @return true if less than or equal, false otherwise
     */
    @Message("is less than or equal to {0}")
    @NotMessage("is not less than or equal to {0}")
    boolean lessThanOrEqualTo(final int value);

    /**
     * Check that this is greater than given value
     *
     * @param value the value to compare with
     * @return true if greater than, false otherwise
     */
    @Message("is greater than {0}")
    @NotMessage("is not greater than {0}")
    boolean greaterThan(final int value);

    /**
     * Check that this is greater than or equal given value
     *
     * @param value the value to compare with
     * @return true if greater than or equal, false otherwise
     */
    @Message("is greater than or equal to {0}")
    @NotMessage("is not greater than or equal to {0}")
    boolean greaterThanOrEqualTo(final int value);

}
