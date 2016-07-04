package org.fluentlenium.core.conditions;

public interface IntegerConditions extends Conditions<Integer> {
    @Override
    IntegerConditions not();

    /**
     * Check that this is equal to given value
     *
     * @param value the value to compare with
     * @return true if is equals, false otherwise
     */
    boolean equalTo(final int value);

    /**
     * Check that this is less than given value
     *
     * @param value the value to compare with
     * @return true if less than, false otherwise
     */
    boolean lessThan(final int value);

    /**
     * Check that this is less than or equal given value
     *
     * @param value the value to compare with
     * @return true if less than or equal, false otherwise
     */
    boolean lessThanOrEqualTo(final int value);

    /**
     * Check that this is greater than given value
     *
     * @param value the value to compare with
     * @return true if greater than, false otherwise
     */
    boolean greaterThan(final int value);

    /**
     * Check that this is greater than or equal given value
     *
     * @param value the value to compare with
     * @return true if greater than or equal, false otherwise
     */
    boolean greaterThanOrEqualTo(final int value);

}
