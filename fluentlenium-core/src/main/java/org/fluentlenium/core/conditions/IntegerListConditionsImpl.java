package org.fluentlenium.core.conditions;

import java.util.function.Function;
import java.util.function.Predicate;
import org.fluentlenium.core.domain.FluentWebElement;

import java.util.List;

/**
 * Conditions for list of integers.
 */
public class IntegerListConditionsImpl extends BaseObjectListConditions<Integer, IntegerConditions>
        implements IntegerConditions, ConditionsObject<List<Integer>> {
    /**
     * Creates a new list conditions
     *
     * @param conditions       list conditions
     * @param objectGetter     getter of the underlying object
     * @param conditionsGetter getter of the underlying conditions
     */
    public IntegerListConditionsImpl(final Conditions<FluentWebElement> conditions,
            final Function<FluentWebElement, Integer> objectGetter,
            final Function<FluentWebElement, IntegerConditions> conditionsGetter) {
        super(conditions, objectGetter, conditionsGetter);
    }

    /**
     * Creates a new list conditions, with default integer condition implementation
     *
     * @param conditions   list conditions
     * @param objectGetter getter of the underlying object
     */
    public IntegerListConditionsImpl(final Conditions<FluentWebElement> conditions,
            final Function<FluentWebElement, Integer> objectGetter) {
        this(conditions, objectGetter, input -> new IntegerConditionsImpl(objectGetter.apply(input)));
    }

    @Override
    public boolean verify(final Predicate<Integer> predicate) {
        return conditions.verify(input -> predicate.test(objectGetter.apply(input)));
    }

    @Override
    public IntegerListConditionsImpl not() {
        return new IntegerListConditionsImpl(conditions.not(), objectGetter, conditionsGetter);
    }

    @Override
    public boolean equalTo(final int value) {
        return this.conditions.verify(input -> conditionsGetter.apply(input).equalTo(value));
    }

    @Override
    public boolean lessThan(final int value) {
        return this.conditions.verify(input -> conditionsGetter.apply(input).lessThan(value));
    }

    @Override
    public boolean lessThanOrEqualTo(final int value) {
        return this.conditions.verify(input -> conditionsGetter.apply(input).lessThanOrEqualTo(value));
    }

    @Override
    public boolean greaterThan(final int value) {
        return this.conditions.verify(input -> conditionsGetter.apply(input).greaterThan(value));
    }

    @Override
    public boolean greaterThanOrEqualTo(final int value) {
        return this.conditions.verify(input -> conditionsGetter.apply(input).greaterThanOrEqualTo(value));
    }
}
