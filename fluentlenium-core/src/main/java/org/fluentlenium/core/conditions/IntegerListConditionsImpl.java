package org.fluentlenium.core.conditions;

import org.fluentlenium.core.domain.FluentWebElement;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

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
    public IntegerListConditionsImpl(Conditions<FluentWebElement> conditions, Function<FluentWebElement, Integer> objectGetter,
            Function<FluentWebElement, IntegerConditions> conditionsGetter) {
        super(conditions, objectGetter, conditionsGetter);
    }

    /**
     * Creates a new list conditions, with default integer condition implementation
     *
     * @param conditions   list conditions
     * @param objectGetter getter of the underlying object
     */
    public IntegerListConditionsImpl(Conditions<FluentWebElement> conditions, Function<FluentWebElement, Integer> objectGetter) {
        this(conditions, objectGetter, input -> new IntegerConditionsImpl(objectGetter.apply(input)));
    }

    @Override
    public boolean verify(Predicate<Integer> predicate) {
        return conditions.verify(input -> predicate.test(objectGetter.apply(input)));
    }

    @Override
    public IntegerListConditionsImpl not() {
        return new IntegerListConditionsImpl(conditions.not(), objectGetter, conditionsGetter);
    }

    @Override
    public boolean equalTo(int value) {
        return conditions.verify(input -> conditionsGetter.apply(input).equalTo(value));
    }

    @Override
    public boolean lessThan(int value) {
        return conditions.verify(input -> conditionsGetter.apply(input).lessThan(value));
    }

    @Override
    public boolean lessThanOrEqualTo(int value) {
        return conditions.verify(input -> conditionsGetter.apply(input).lessThanOrEqualTo(value));
    }

    @Override
    public boolean greaterThan(int value) {
        return conditions.verify(input -> conditionsGetter.apply(input).greaterThan(value));
    }

    @Override
    public boolean greaterThanOrEqualTo(int value) {
        return conditions.verify(input -> conditionsGetter.apply(input).greaterThanOrEqualTo(value));
    }
}
