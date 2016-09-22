package org.fluentlenium.core.wait;

import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.conditions.Conditions;

import static org.fluentlenium.core.wait.FluentWaitMessages.isPredicateNotVerifiedMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isPredicateVerifiedMessage;

public abstract class AbstractFluentWaitConditionsMatcher<T, C extends Conditions<T>> {
    protected final AbstractWaitElementMatcher matcher;
    protected final Supplier<C> conditionsSupplier;

    protected AbstractFluentWaitConditionsMatcher(AbstractWaitElementMatcher matcher, Supplier<C> stringConditionsSupplier) {
        this.matcher = matcher;
        this.conditionsSupplier = stringConditionsSupplier;
    }

    protected C conditions() {
        return conditions(false);
    }

    protected C conditions(boolean ignoreNot) {
        C conditions = conditionsSupplier.get();
        if (!ignoreNot && matcher.negation) {
            conditions = (C) conditions.not();
        }
        return conditions;
    }

    public boolean verify(final Predicate<T> predicate) {
        matcher.until(matcher.wait, new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return conditions().verify(predicate);
            }
        }, matcher.negation ? isPredicateNotVerifiedMessage(matcher.selectionName) : isPredicateVerifiedMessage(matcher.selectionName));
        return true;
    }
}
