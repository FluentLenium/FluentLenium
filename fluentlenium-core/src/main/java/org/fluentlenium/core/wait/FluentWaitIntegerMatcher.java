package org.fluentlenium.core.wait;

import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import org.fluentlenium.core.FluentDriver;
import org.fluentlenium.core.conditions.IntegerConditions;

import static org.fluentlenium.core.wait.FluentWaitMessages.equalToMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.greaterThanMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.greaterThanOrEqualToMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isPredicateNotVerifiedMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isPredicateVerifiedMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.lessThanMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.lessThanOrEqualToMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.notEqualToMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.notGreaterThanMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.notGreaterThanOrEqualToMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.notLessThanMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.notLessThanOrEqualToMessage;


/**
 * Matcher used for integers like elements size
 */
public class FluentWaitIntegerMatcher implements IntegerConditions {
    private final AbstractWaitElementMatcher matcher;
    private final Supplier<IntegerConditions> conditionsSupplier;

    protected FluentWaitIntegerMatcher(AbstractWaitElementMatcher matcher, Supplier<IntegerConditions> conditionsSupplier) {
        this.matcher = matcher;
        this.conditionsSupplier = conditionsSupplier;
    }

    protected IntegerConditions hasSize() {
        IntegerConditions conditions = this.conditionsSupplier.get();
        if (matcher.negation) {
            conditions = conditions.not();
        }
        return conditions;
    }

    @Override
    public boolean equalTo(final int value) {
        matcher.until(matcher.wait, new Predicate<FluentDriver>() {
            @Override
            public boolean apply(FluentDriver input) {
                return hasSize().equalTo(value);
            }
        }, matcher.negation ? notEqualToMessage(matcher.selectionName, value) : equalToMessage(matcher.selectionName, value));
        return true;
    }

    @Override
    public boolean lessThan(final int value) {
        matcher.until(matcher.wait, new Predicate<FluentDriver>() {
            @Override
            public boolean apply(FluentDriver input) {
                return hasSize().lessThan(value);
            }
        }, matcher.negation ? notLessThanMessage(matcher.selectionName, value) : lessThanMessage(matcher.selectionName, value));
        return true;
    }

    @Override
    public boolean lessThanOrEqualTo(final int value) {
        matcher.until(matcher.wait, new Predicate<FluentDriver>() {
            @Override
            public boolean apply(FluentDriver input) {
                return hasSize().lessThanOrEqualTo(value);
            }
        }, matcher.negation ? notLessThanOrEqualToMessage(matcher.selectionName, value) : lessThanOrEqualToMessage(matcher.selectionName, value));
        return true;
    }

    @Override
    public boolean greaterThan(final int value) {
        matcher.until(matcher.wait, new Predicate<FluentDriver>() {
            @Override
            public boolean apply(FluentDriver input) {
                return hasSize().greaterThan(value);
            }
        }, matcher.negation ? notGreaterThanMessage(matcher.selectionName, value) : greaterThanMessage(matcher.selectionName, value));
        return true;
    }

    @Override
    public boolean greaterThanOrEqualTo(final int value) {
        matcher.until(matcher.wait, new Predicate<FluentDriver>() {
            @Override
            public boolean apply(FluentDriver input) {
                return hasSize().greaterThanOrEqualTo(value);
            }
        }, matcher.negation ? notGreaterThanOrEqualToMessage(matcher.selectionName, value) : greaterThanOrEqualToMessage(matcher.selectionName, value));
        return true;
    }

    @Override
    public boolean isVerified(final Predicate<Integer> predicate) {
        matcher.until(matcher.wait, new Predicate<FluentDriver>() {
            @Override
            public boolean apply(FluentDriver input) {
                return hasSize().isVerified(predicate);
            }
        }, matcher.negation ? isPredicateNotVerifiedMessage(matcher.selectionName) : isPredicateVerifiedMessage(matcher.selectionName));
        return true;
    }

    @Override
    public FluentWaitIntegerMatcher not() {
        return new FluentWaitIntegerMatcher((AbstractWaitElementMatcher) matcher.not(), conditionsSupplier);
    }
}
