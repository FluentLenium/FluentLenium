package org.fluentlenium.core.conditions;


import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.fluentlenium.core.FluentDriver;
import org.fluentlenium.core.context.FluentThread;
import org.fluentlenium.core.domain.FluentWebElement;

import java.util.List;

/**
 * Conditions implementation for list of elements, matching when at least one element of the list matches.
 */
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

    protected Predicate<FluentDriver> buildAtLeastOnePredicate(final Predicate<FluentWebElement> predicate, final boolean defaultValue) {
        Predicate<FluentDriver> untilPredicate = new com.google.common.base.Predicate<FluentDriver>() {
            public boolean apply(FluentDriver fluent) {
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
