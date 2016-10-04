package org.fluentlenium.core.conditions;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import org.fluentlenium.core.domain.FluentWebElement;

public class BaseObjectListConditions<T, C extends Conditions<T>> {
    protected Conditions<FluentWebElement> conditions;
    protected final Function<FluentWebElement, T> objectGetter;
    protected final Function<FluentWebElement, C> conditionsGetter;

    public BaseObjectListConditions(final Conditions<FluentWebElement> conditions,
            final Function<FluentWebElement, T> objectGetter, final Function<FluentWebElement, C> conditionsGetter) {
        this.conditions = conditions;
        this.objectGetter = objectGetter;
        this.conditionsGetter = conditionsGetter;
    }

    public boolean verify(final Predicate<T> predicate) {
        return this.conditions.verify(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(final FluentWebElement input) {
                return predicate.apply(objectGetter.apply(input));
            }
        });
    }
}
