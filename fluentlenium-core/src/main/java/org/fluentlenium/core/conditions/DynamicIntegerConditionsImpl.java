package org.fluentlenium.core.conditions;

import java.util.List;
import java.util.function.Supplier;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;

/**
 * Conditions for integer
 */
public class DynamicIntegerConditionsImpl extends AbstractObjectConditions<List<? extends FluentWebElement>>
        implements ListIntegerConditions {
    /**
     * Creates a new conditions object on integer.
     *
     * @param supplier underlying list
     * @param negation negation value
     */
    public DynamicIntegerConditionsImpl(Supplier<List<? extends FluentWebElement>> supplier, boolean negation) {
        super(supplier.get(), negation);
    }

    @Override
    protected AbstractObjectConditions<List<? extends FluentWebElement>> newInstance(boolean negationValue) {
        return new DynamicIntegerConditionsImpl(() -> object, negationValue);
    }

    @Override
    @Negation
    public DynamicIntegerConditionsImpl not() {
        return (DynamicIntegerConditionsImpl) super.not();
    }

    @Override
    public boolean equalTo(int value) {
        return verify(input -> ((FluentList<FluentWebElement>) input).count() == value);
    }

    @Override
    public boolean lessThan(int value) {
        return verify(input -> ((FluentList<FluentWebElement>) input).count() < value);
    }

    @Override
    public boolean lessThanOrEqualTo(int value) {
        return verify(input -> ((FluentList<FluentWebElement>) input).count() <= value);
    }

    @Override
    public boolean greaterThan(int value) {
        return verify(input -> ((FluentList<FluentWebElement>) input).count() > value);
    }

    @Override
    public boolean greaterThanOrEqualTo(int value) {
        return verify(input -> ((FluentList<FluentWebElement>) input).count() >= value);
    }
}
