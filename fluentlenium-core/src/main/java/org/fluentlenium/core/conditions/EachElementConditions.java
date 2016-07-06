package org.fluentlenium.core.conditions;


import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.fluentlenium.core.Fluent;
import org.fluentlenium.core.FluentThread;
import org.fluentlenium.core.domain.FluentWebElement;

import java.util.List;

/**
 * Conditions implementation for list of elements, matching when each element matches.
 */
public class EachElementConditions extends AbstractFluentListConditions {
    public EachElementConditions(List<? extends FluentWebElement> elements) {
        super(elements);
    }

    @Override
    public EachElementConditions not() {
        EachElementConditions negatedConditions = new EachElementConditions(elements);
        negatedConditions.negation = !negation;
        return negatedConditions;
    }

    public boolean isVerified(Predicate<FluentWebElement> predicate, boolean defaultValue) {
        if (negation) {
            predicate = Predicates.not(predicate);
            defaultValue = !defaultValue;
        }
        return buildEachElementPredicate(predicate, defaultValue).apply(FluentThread.get());
    }

    protected Predicate<Fluent> buildEachElementPredicate(final Predicate<FluentWebElement> predicate, final boolean defaultValue) {
        Predicate<Fluent> untilPredicate = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                if (elements.size() > 0) {
                    for (FluentWebElement element : elements) {
                        if (!predicate.apply(element)) {
                            return false;
                        }
                    }
                    return true;
                }
                return defaultValue;
            }
        };
        return untilPredicate;
    }
}
