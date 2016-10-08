package org.fluentlenium.core.conditions;

import com.google.common.base.Predicate;

/**
 * Conditions for integer
 */
public class IntegerConditionsImpl extends AbstractObjectConditions<Integer> implements IntegerConditions {
    /**
     * Creates a new conditions object on integer.
     *
     * @param integer underlying integer
     */
    public IntegerConditionsImpl(final Integer integer) {
        super(integer);
    }

    /**
     * Creates a new conditions object on integer.
     *
     * @param integer  underlying integer
     * @param negation negation value
     */
    public IntegerConditionsImpl(final Integer integer, final boolean negation) {
        super(integer, negation);
    }

    @Override
    protected AbstractObjectConditions<Integer> newInstance(final boolean negationValue) {
        return new IntegerConditionsImpl(object, negationValue);
    }

    @Override
    @Negation
    public IntegerConditionsImpl not() {
        return (IntegerConditionsImpl) super.not();
    }

    @Override
    public boolean equalTo(final int value) {
        return verify(new Predicate<Integer>() {
            @Override
            public boolean apply(final Integer input) {
                return input.equals(value);
            }
        });
    }

    @Override
    public boolean lessThan(final int value) {
        return verify(new Predicate<Integer>() {
            @Override
            public boolean apply(final Integer input) {
                return input < value;
            }
        });
    }

    @Override
    public boolean lessThanOrEqualTo(final int value) {
        return verify(new Predicate<Integer>() {
            @Override
            public boolean apply(final Integer input) {
                return input <= value;
            }
        });
    }

    @Override
    public boolean greaterThan(final int value) {
        return verify(new Predicate<Integer>() {
            @Override
            public boolean apply(final Integer input) {
                return input > value;
            }
        });
    }

    @Override
    public boolean greaterThanOrEqualTo(final int value) {
        return verify(new Predicate<Integer>() {
            @Override
            public boolean apply(final Integer input) {
                return input >= value;
            }
        });
    }
}
