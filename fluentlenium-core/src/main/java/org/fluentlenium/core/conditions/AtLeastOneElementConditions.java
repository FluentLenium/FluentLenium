package org.fluentlenium.core.conditions;

import java.util.function.Predicate;
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
    public AtLeastOneElementConditions(final List<? extends FluentWebElement> elements) {
        super(elements);
    }

    @Override
    public AtLeastOneElementConditions not() {
        final AtLeastOneElementConditions negatedConditions = new AtLeastOneElementConditions(getElements());
        negatedConditions.setNegation(!isNegation());
        return negatedConditions;
    }

    @Override
    public boolean verify(Predicate<FluentWebElement> predicate, boolean defaultValue) {
        if (isNegation()) {
            predicate = predicate.negate();
            defaultValue = !defaultValue;
        }
        return buildAtLeastOnePredicate(predicate, defaultValue).test(null);
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
        final Predicate<FluentDriver> untilPredicate = fluent -> {
            if (!getElements().isEmpty()) {
                for (final FluentWebElement element : getElements()) {
                    if (predicate.test(element)) {
                        return true;
                    }
                }
                return false;
            }
            return defaultValue;
        };
        return untilPredicate;
    }
}
