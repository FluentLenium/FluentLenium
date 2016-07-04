package org.fluentlenium.core.conditions;

import com.google.common.base.Predicate;
import com.google.common.base.Supplier;


public class IntegerConditionsImpl implements IntegerConditions {
    private final Supplier<Integer> intSupplier;
    private boolean negation;

    public IntegerConditionsImpl(Supplier<Integer> intSupplier) {
        this.intSupplier = intSupplier;
    }

    @Override
    public IntegerConditionsImpl not() {
        IntegerConditionsImpl negatedConditions = new IntegerConditionsImpl(intSupplier);
        negatedConditions.negation = !negation;
        return negatedConditions;
    }

    @Override
    public boolean isVerified(Predicate<Integer> predicate) {
        boolean predicateResult = predicate.apply(intSupplier.get());
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
