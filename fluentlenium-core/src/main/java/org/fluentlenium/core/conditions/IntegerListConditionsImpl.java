package org.fluentlenium.core.conditions;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import org.fluentlenium.core.domain.FluentWebElement;

/**
 * Conditions implementation for integer on list of elements.
 */
public class IntegerListConditionsImpl implements IntegerConditions {

    private Conditions<FluentWebElement> listConditions;
    private final Function<FluentWebElement, Integer> integerGetter;
    private final Function<FluentWebElement, IntegerConditions> conditionsGetter;

    public IntegerListConditionsImpl(final Conditions<FluentWebElement> listConditions,
            final Function<FluentWebElement, Integer> integerGetter,
            final Function<FluentWebElement, IntegerConditions> conditionsGetter) {
        this.listConditions = listConditions;
        this.integerGetter = integerGetter;
        this.conditionsGetter = conditionsGetter;
    }

    public IntegerListConditionsImpl(final Conditions<FluentWebElement> listConditions,
            final Function<FluentWebElement, Integer> integerGetter) {
        this(listConditions, integerGetter, new Function<FluentWebElement, IntegerConditions>() {
            @Override
            public IntegerConditions apply(final FluentWebElement input) {
                return new IntegerConditionsImpl(integerGetter.apply(input));
            }
        });
    }

    @Override
    public boolean verify(final Predicate<Integer> predicate) {
        return listConditions.verify(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(final FluentWebElement input) {
                return predicate.apply(integerGetter.apply(input));
            }
        });
    }

    @Override
    public IntegerListConditionsImpl not() {
        return new IntegerListConditionsImpl(listConditions.not(), integerGetter, conditionsGetter);
    }

    @Override
    public boolean equalTo(final int value) {
        return this.listConditions.verify(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(final FluentWebElement input) {
                return conditionsGetter.apply(input).equalTo(value);
            }
        });
    }

    @Override
    public boolean lessThan(final int value) {
        return this.listConditions.verify(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(final FluentWebElement input) {
                return conditionsGetter.apply(input).lessThan(value);
            }
        });
    }

    @Override
    public boolean lessThanOrEqualTo(final int value) {
        return this.listConditions.verify(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(final FluentWebElement input) {
                return conditionsGetter.apply(input).lessThanOrEqualTo(value);
            }
        });
    }

    @Override
    public boolean greaterThan(final int value) {
        return this.listConditions.verify(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(final FluentWebElement input) {
                return conditionsGetter.apply(input).greaterThan(value);
            }
        });
    }

    @Override
    public boolean greaterThanOrEqualTo(final int value) {
        return this.listConditions.verify(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(final FluentWebElement input) {
                return conditionsGetter.apply(input).greaterThanOrEqualTo(value);
            }
        });
    }
}
