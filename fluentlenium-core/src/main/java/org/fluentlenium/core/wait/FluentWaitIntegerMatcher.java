package org.fluentlenium.core.wait;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import org.fluentlenium.core.conditions.IntegerConditions;


/**
 * Matcher used for integers like elements size
 */
public class FluentWaitIntegerMatcher extends AbstractFluentWaitConditionsMatcher<Integer, IntegerConditions> implements IntegerConditions {
    protected FluentWaitIntegerMatcher(AbstractWaitElementMatcher matcher, Supplier<IntegerConditions> conditionsSupplier, Supplier<IntegerConditions> messageBuilderSupplier) {
        super(matcher, conditionsSupplier, messageBuilderSupplier);
    }

    @Override
    public FluentWaitIntegerMatcher not() {
        return new FluentWaitIntegerMatcher((AbstractWaitElementMatcher) matcher.not(), conditionsSupplier, messageBuilderSupplier);
    }

    @Override
    public boolean equalTo(final int value) {
        matcher.until(matcher.wait, conditions(), messageBuilder(), new Function<IntegerConditions, Boolean>() {
            @Override
            public Boolean apply(IntegerConditions input) {
                return input.equalTo(value);
            }
        });
        return true;
    }

    @Override
    public boolean lessThan(final int value) {
        matcher.until(matcher.wait, conditions(), messageBuilder(), new Function<IntegerConditions, Boolean>() {
            @Override
            public Boolean apply(IntegerConditions input) {
                return input.lessThan(value);
            }
        });
        return true;
    }

    @Override
    public boolean lessThanOrEqualTo(final int value) {
        matcher.until(matcher.wait, conditions(), messageBuilder(), new Function<IntegerConditions, Boolean>() {
            @Override
            public Boolean apply(IntegerConditions input) {
                return input.lessThanOrEqualTo(value);
            }
        });
        return true;
    }

    @Override
    public boolean greaterThan(final int value) {
        matcher.until(matcher.wait, conditions(), messageBuilder(), new Function<IntegerConditions, Boolean>() {
            @Override
            public Boolean apply(IntegerConditions input) {
                return input.greaterThan(value);
            }
        });
        return true;
    }

    @Override
    public boolean greaterThanOrEqualTo(final int value) {
        matcher.until(matcher.wait, conditions(), messageBuilder(), new Function<IntegerConditions, Boolean>() {
            @Override
            public Boolean apply(IntegerConditions input) {
                return input.greaterThanOrEqualTo(value);
            }
        });
        return true;
    }

    @Override
    public boolean verify(final Predicate<Integer> predicate) {
        matcher.until(matcher.wait, conditions(), messageBuilder(), new Function<IntegerConditions, Boolean>() {
            @Override
            public Boolean apply(IntegerConditions input) {
                return input.verify(predicate);
            }
        });
        return true;
    }
}
