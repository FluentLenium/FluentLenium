package org.fluentlenium.core.conditions;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import org.fluentlenium.core.domain.FluentWebElement;

/**
 * Base condition for list of objects
 *
 * @param <T> type of underlying object in the list
 * @param <C> type of conditions
 */
public class BaseObjectListConditions<T, C extends Conditions<T>> {
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
    public BaseObjectListConditions(final Conditions<FluentWebElement> conditions,
            final Function<FluentWebElement, T> objectGetter, final Function<FluentWebElement, C> conditionsGetter) {
        this.conditions = conditions;
        this.objectGetter = objectGetter;
        this.conditionsGetter = conditionsGetter;
    }

    /**
     * Verify the predicate against the condition.
     *
     * @param predicate predicate
     * @return true if the predicate is verified
     */
    public boolean verify(final Predicate<T> predicate) {
        return this.conditions.verify(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(final FluentWebElement input) {
                return predicate.apply(objectGetter.apply(input));
            }
        });
    }
}
