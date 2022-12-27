package io.fluentlenium.core.conditions;

import java.util.List;
import java.util.function.Predicate;

import io.fluentlenium.core.FluentDriver;
import io.fluentlenium.core.domain.FluentWebElement;

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
    protected Predicate<FluentDriver> buildAtLeastOnePredicate(Predicate<FluentWebElement> predicate, boolean defaultValue) {
        Predicate<FluentDriver> untilPredicate = fluent -> {
            if (!getElements().isEmpty()) {
                for (FluentWebElement element : getElements()) {
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
