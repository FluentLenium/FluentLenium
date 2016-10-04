package org.fluentlenium.core.conditions;

import com.google.common.base.Predicate;

/**
 * Conditions implementation for Integer.
 */
public class IntegerConditionsImpl extends AbstractObjectConditions<Integer> implements IntegerConditions {
    public IntegerConditionsImpl(final Integer integer) {
        super(integer);
    }

    public IntegerConditionsImpl(final Integer object, final boolean negation) {
        super(object, negation);
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
