package org.fluentlenium.core.conditions;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.fluentlenium.core.FluentDriver;
import org.fluentlenium.core.domain.FluentWebElement;

import java.util.List;

/**
 * Conditions implementation for list of elements, matching when each element matches.
 */
public class EachElementConditions extends AbstractFluentListConditions {
    public EachElementConditions(final List<? extends FluentWebElement> elements) {
        super(elements);
    }

    @Override
    public EachElementConditions not() {
        final EachElementConditions negatedConditions = new EachElementConditions(getElements());
        negatedConditions.setNegation(!isNegation());
        return negatedConditions;
    }

    public boolean verify(Predicate<FluentWebElement> predicate, boolean defaultValue) {
        if (isNegation()) {
            predicate = Predicates.not(predicate);
            defaultValue = !defaultValue;
        }
        return buildEachElementPredicate(predicate, defaultValue).apply(null);
    }

    protected Predicate<FluentDriver> buildEachElementPredicate(final Predicate<FluentWebElement> predicate,
            final boolean defaultValue) {
        final Predicate<FluentDriver> untilPredicate = new Predicate<FluentDriver>() {
            public boolean apply(final FluentDriver fluent) {
                if (getElements().size() > 0) {
                    for (final FluentWebElement element : getElements()) {
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
