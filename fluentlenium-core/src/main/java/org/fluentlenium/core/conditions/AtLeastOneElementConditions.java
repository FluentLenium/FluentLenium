package org.fluentlenium.core.conditions;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.fluentlenium.core.FluentDriver;
import org.fluentlenium.core.domain.FluentWebElement;

import java.util.List;

/**
 * Conditions for list of elements, matching when at least one element of the list matches.
 */
public class AtLeastOneElementConditions extends AbstractFluentListConditions {
    /**
     * Creates a new condition.
     *
     * @param elements underlying elements
     */
    public AtLeastOneElementConditions(List<? extends FluentWebElement> elements) {
        super(elements);
    }

    @Override
    public AtLeastOneElementConditions not() {
        AtLeastOneElementConditions negatedConditions = new AtLeastOneElementConditions(getElements());
        negatedConditions.setNegation(!isNegation());
        return negatedConditions;
    }

    @Override
    public boolean verify(Predicate<FluentWebElement> predicate, boolean defaultValue) {
        if (isNegation()) {
            predicate = Predicates.not(predicate);
            defaultValue = !defaultValue;
        }
        return buildAtLeastOnePredicate(predicate, defaultValue).apply(null);
    }

    /**
     * Build predicate for this condition.
     *
     * @param predicate    predicate
     * @param defaultValue default value if elements list is empty.
     * @return predicate
     */
    protected Predicate<FluentDriver> buildAtLeastOnePredicate(final Predicate<FluentWebElement> predicate,
            final boolean defaultValue) {
        Predicate<FluentDriver> untilPredicate = new Predicate<FluentDriver>() {
            @Override
            public boolean apply(FluentDriver fluent) {
                if (!getElements().isEmpty()) {
                    for (FluentWebElement element : getElements()) {
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
