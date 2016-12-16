package org.fluentlenium.core.conditions;

import org.fluentlenium.core.domain.FluentWebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

/**
 * Base condition for list of objects
 *
 * @param <T> type of underlying object in the list
 * @param <C> type of conditions
 */
public class BaseObjectListConditions<T, C extends Conditions<T>> implements ConditionsObject<List<T>> {
    protected Conditions<FluentWebElement> conditions;
    protected final Function<FluentWebElement, T> objectGetter;
    protected final Function<FluentWebElement, C> conditionsGetter;

    /**
     * Creates a new list conditions
     *
     * @param conditions       object conditions
     * @param objectGetter     getter of the underlying object
     * @param conditionsGetter getter of the underlying object conditions
     */
    public BaseObjectListConditions(Conditions<FluentWebElement> conditions, Function<FluentWebElement, T> objectGetter,
            Function<FluentWebElement, C> conditionsGetter) {
        this.conditions = conditions;
        this.objectGetter = objectGetter;
        this.conditionsGetter = conditionsGetter;
    }

    @Override
    public List<T> getActualObject() {
        if (conditions instanceof ListConditionsElements) {
            List<? extends FluentWebElement> elements = ((ListConditionsElements) conditions).getActualElements();
            return elements.stream().map(objectGetter).collect(toList());
        }
        return new ArrayList<>();
    }

    /**
     * Verify the predicate against the condition.
     *
     * @param predicate predicate
     * @return true if the predicate is verified
     */
    public boolean verify(Predicate<T> predicate) {
        return conditions.verify(input -> predicate.test(objectGetter.apply(input)));
    }
}
