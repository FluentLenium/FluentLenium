package org.fluentlenium.core.conditions;

import com.google.common.base.Predicate;

/**
 * Conditions implementation for Integer.
 */
public class IntegerConditionsImpl implements IntegerConditions {
    private final Integer integer;
    private boolean negation;

    public IntegerConditionsImpl(Integer integer) {
        this.integer = integer;
    }

    @Override
    public IntegerConditionsImpl not() {
        IntegerConditionsImpl negatedConditions = new IntegerConditionsImpl(integer);
        negatedConditions.negation = !negation;
        return negatedConditions;
    }

    @Override
    public boolean isVerified(Predicate<Integer> predicate) {
        boolean predicateResult = predicate.apply(integer);
        if (negation) {
            predicateResult = !predicateResult;
        }
        return predicateResult;
    }

    @Override
    public boolean equalTo(final int value) {
        return isVerified(new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input.equals(value);
            }
        });
    }

    @Override
    public boolean lessThan(final int value) {
        return isVerified(new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input < value;
            }
        });
    }

    @Override
    public boolean lessThanOrEqualTo(final int value) {
        return isVerified(new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input <= value;
            }
        });
    }

    @Override
    public boolean greaterThan(final int value) {
        return isVerified(new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input > value;
            }
        });
    }

    @Override
    public boolean greaterThanOrEqualTo(final int value) {
        return isVerified(new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input >= value;
            }
        });
    }
}
