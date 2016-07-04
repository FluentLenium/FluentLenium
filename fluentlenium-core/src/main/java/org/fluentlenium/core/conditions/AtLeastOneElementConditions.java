package org.fluentlenium.core.conditions;


import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.fluentlenium.core.Fluent;
import org.fluentlenium.core.FluentThread;
import org.fluentlenium.core.domain.FluentWebElement;

import java.util.List;

public class AtLeastOneElementConditions extends AbstractFluentListConditions {
    public AtLeastOneElementConditions(List<? extends FluentWebElement> elements) {
        super(elements);
    }

    @Override
    public AtLeastOneElementConditions not() {
        AtLeastOneElementConditions negatedConditions = new AtLeastOneElementConditions(elements);
        negatedConditions.negation = !negation;
        return negatedConditions;
    }

    @Override
    public boolean isVerified(Predicate<FluentWebElement> predicate, boolean defaultValue) {
        if (negation) {
            predicate = Predicates.not(predicate);
            defaultValue = !defaultValue;
        }
        return buildAtLeastOnePredicate(predicate, defaultValue).apply(FluentThread.get());
    }

    protected Predicate<Fluent> buildAtLeastOnePredicate(final Predicate<FluentWebElement> predicate, final boolean defaultValue) {
        Predicate<Fluent> untilPredicate = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                if (elements.size() > 0) {
                    for (FluentWebElement element : elements) {
                        if (predicate.apply(element)) {
                            return true;
                        }
                    }
                    return false;
                }
                return defaultValue;
            }
        };
        return untilPredicate;
    }
}
