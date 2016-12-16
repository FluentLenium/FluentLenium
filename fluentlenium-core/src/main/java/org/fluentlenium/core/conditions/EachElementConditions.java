package org.fluentlenium.core.conditions;

import java.util.function.Predicate;
import org.fluentlenium.core.FluentDriver;
import org.fluentlenium.core.domain.FluentWebElement;

import java.util.List;

/**
 * Conditions for list of elements, matching when each element matches.
 */
public class EachElementConditions extends AbstractFluentListConditions {
    /**
     * Creates a new element list conditions.
     *
     * @param elements underlying elements
     */
    public EachElementConditions(final List<? extends FluentWebElement> elements) {
        super(elements);
    }

    @Override
    public EachElementConditions not() {
        final EachElementConditions negatedConditions = new EachElementConditions(getElements());
        negatedConditions.setNegation(!isNegation());
        return negatedConditions;
    }

    @Override
    public boolean verify(Predicate<FluentWebElement> predicate, boolean defaultValue) {
        if (isNegation()) {
            predicate = predicate.negate();
            defaultValue = !defaultValue;
        }
        return buildEachElementPredicate(predicate, defaultValue).test(null);
    }

    /**
     * Build predicate for this condition.
     *
     * @param predicate    predicate
     * @param defaultValue default value if elements list is empty.
     * @return predicate
     */
    protected Predicate<FluentDriver> buildEachElementPredicate(final Predicate<FluentWebElement> predicate,
            final boolean defaultValue) {
        final Predicate<FluentDriver> untilPredicate = fluent -> {
            if (getElements().size() > 0) {
                for (final FluentWebElement element : getElements()) {
                    if (!predicate.test(element)) {
                        return false;
                    }
                }
                return true;
            }
            return defaultValue;
        };
        return untilPredicate;
    }
}
