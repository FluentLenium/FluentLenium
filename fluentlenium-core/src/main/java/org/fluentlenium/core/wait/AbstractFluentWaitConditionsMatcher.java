package org.fluentlenium.core.wait;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import org.fluentlenium.core.conditions.Conditions;

public abstract class AbstractFluentWaitConditionsMatcher<T, C extends Conditions<T>> {
    protected final AbstractWaitElementMatcher matcher;
    protected final Supplier<C> conditionsSupplier;
    protected final Supplier<C> messageBuilderSupplier;

    protected AbstractFluentWaitConditionsMatcher(AbstractWaitElementMatcher matcher,
                                                  Supplier<C> conditionsSupplier,
                                                  Supplier<C> messageBuilderSupplier) {
        this.matcher = matcher;
        this.conditionsSupplier = conditionsSupplier;
        this.messageBuilderSupplier = messageBuilderSupplier;
    }

    protected C conditions() {
        return conditions(false);
    }

    protected C conditions(boolean ignoreNot) {
        C conditions = conditionsSupplier.get();
        return applyNegation(conditions, ignoreNot);
    }

    protected C applyNegation(C conditions, boolean ignoreNot) {
        if (!ignoreNot && matcher.negation) return (C) conditions.not();
        return conditions;
    }

    protected C messageBuilder() {
        return messageBuilder(false);
    }

    protected C messageBuilder(boolean ignoreNot) {
        C messageBuilder = messageBuilderSupplier.get();
        return applyNegation(messageBuilder, ignoreNot);
    }

    public boolean verify(final Predicate<T> predicate) {
        matcher.until(matcher.wait, conditions(), messageBuilder(), new Function<C, Boolean>() {
            @Override
            public Boolean apply(C input) {
                return input.verify(predicate);
            }
        });
        return true;
    }
}
