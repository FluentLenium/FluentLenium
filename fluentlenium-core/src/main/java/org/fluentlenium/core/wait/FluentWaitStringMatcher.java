package org.fluentlenium.core.wait;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import org.fluentlenium.core.conditions.StringConditions;

import java.util.regex.Pattern;

public class FluentWaitStringMatcher extends AbstractFluentWaitConditionsMatcher<String, StringConditions> implements StringConditions {
    protected FluentWaitStringMatcher(AbstractWaitElementMatcher matcher, Supplier<StringConditions> stringConditionsSupplier, Supplier<StringConditions> messageBuilderSupplier) {
        super(matcher, stringConditionsSupplier, messageBuilderSupplier);
    }

    @Override
    public FluentWaitStringMatcher not() {
        return new FluentWaitStringMatcher((AbstractWaitElementMatcher) matcher.not(), conditionsSupplier, messageBuilderSupplier);
    }

    @Override
    public boolean contains(final CharSequence s) {
        matcher.until(matcher.wait, conditions(), messageBuilder(), new Function<StringConditions, Boolean>() {
            @Override
            public Boolean apply(StringConditions input) {
                return input.contains(s);
            }
        });
        return true;
    }

    @Override
    public boolean startsWith(final String prefix) {
        matcher.until(matcher.wait, conditions(), messageBuilder(), new Function<StringConditions, Boolean>() {
            @Override
            public Boolean apply(StringConditions input) {
                return input.startsWith(prefix);
            }
        });
        return true;
    }

    @Override
    public boolean endsWith(final String suffix) {
        matcher.until(matcher.wait, conditions(), messageBuilder(), new Function<StringConditions, Boolean>() {
            @Override
            public Boolean apply(StringConditions input) {
                return input.endsWith(suffix);
            }
        });
        return true;
    }

    @Override
    public boolean equals(final String anotherString) {
        matcher.until(matcher.wait, conditions(), messageBuilder(), new Function<StringConditions, Boolean>() {
            @Override
            public Boolean apply(StringConditions input) {
                return input.equals(anotherString);
            }
        });
        return true;
    }

    @Override
    public boolean equalsIgnoreCase(final String anotherString) {
        matcher.until(matcher.wait, conditions(), messageBuilder(), new Function<StringConditions, Boolean>() {
            @Override
            public Boolean apply(StringConditions input) {
                return input.equalsIgnoreCase(anotherString);
            }
        });
        return true;
    }

    @Override
    public boolean matches(final String regex) {
        matcher.until(matcher.wait, conditions(), messageBuilder(), new Function<StringConditions, Boolean>() {
            @Override
            public Boolean apply(StringConditions input) {
                return input.matches(regex);
            }
        });
        return true;
    }

    @Override
    public boolean matches(final Pattern pattern) {
        matcher.until(matcher.wait, conditions(), messageBuilder(), new Function<StringConditions, Boolean>() {
            @Override
            public Boolean apply(StringConditions input) {
                return input.matches(pattern);
            }
        });
        return true;
    }
}
