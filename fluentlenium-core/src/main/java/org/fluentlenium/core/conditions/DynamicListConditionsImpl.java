package org.fluentlenium.core.conditions;

import java.util.List;
import java.util.function.Supplier;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;

/**
 * Conditions for integer
 */
public class DynamicListConditionsImpl extends AbstractObjectConditions<List<? extends FluentWebElement>>
        implements ListIntegerConditions {
    /**
     * Creates a new conditions object on integer.
     *
     * @param supplier  underlying list
     * @param negation negation value
     */
    DynamicListConditionsImpl(Supplier<List<? extends FluentWebElement>> supplier, boolean negation) {
        super(supplier.get(), negation);
    }

    @Override
    protected AbstractObjectConditions<List<? extends FluentWebElement>> newInstance(boolean negationValue) {
        return new DynamicListConditionsImpl(() -> object, negationValue);
    }

    @Override
    @Negation
    public DynamicListConditionsImpl not() {
        return (DynamicListConditionsImpl) super.not();
    }

    @Override
    public boolean equalTo(int value) {
        return verify(input -> input.size() == value);
    }

    @Override
    public boolean lessThan(int value) {
        return false;
    }

    @Override
    public boolean lessThanOrEqualTo(int value) {
        return verify(input -> input.size() <= value);
    }

    @Override
    public boolean greaterThan(int value) {
        return verify(input -> input.size() > value);
    }

    @Override
    public boolean greaterThanOrEqualTo(int value) {
        return verify(input -> input.size() >= value);
    }

    public boolean displayed() {
        return verify(input -> ((FluentList<? extends FluentWebElement>) input).first().displayed());
    }

    public boolean present() {
        return verify(input -> ((FluentList<? extends FluentWebElement>) input).first().present());
    }
}
