package org.fluentlenium.core.conditions;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import org.fluentlenium.core.domain.FluentWebElement;

public class AbstractObjectListConditions<T, C extends Conditions<T>> {
    protected Conditions<FluentWebElement> conditions;
    protected final Function<FluentWebElement, T> objectGetter;
    protected final Function<FluentWebElement, C> conditionsGetter;

    public AbstractObjectListConditions(Conditions<FluentWebElement> conditions,
                                        Function<FluentWebElement, T> objectGetter,
                                        Function<FluentWebElement, C> conditionsGetter) {
        this.conditions = conditions;
        this.objectGetter = objectGetter;
        this.conditionsGetter = conditionsGetter;
    }

    public boolean verify(final Predicate<T> predicate) {
        return this.conditions.verify(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return predicate.apply(objectGetter.apply(input));
            }
        });
    }
}
