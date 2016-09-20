package org.fluentlenium.core.wait;

import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.conditions.StringConditions;

import java.util.regex.Pattern;

import static org.fluentlenium.core.wait.FluentWaitMessages.hasNotTextMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.hasTextMessage;

public class FluentWaitStringMatcher extends AbstractFluentWaitConditionsMatcher<String, StringConditions> implements StringConditions {
    protected FluentWaitStringMatcher(AbstractWaitElementMatcher matcher, Supplier<StringConditions> stringConditionsSupplier) {
        super(matcher, stringConditionsSupplier);
    }

    @Override
    public FluentWaitStringMatcher not() {
        return new FluentWaitStringMatcher((AbstractWaitElementMatcher) matcher.not(), conditionsSupplier);
    }

    @Override
    public boolean contains(final CharSequence s) {
        matcher.until(matcher.wait, new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return conditions().contains(s);
            }
        }, matcher.negation ? hasNotTextMessage(matcher.selectionName, s) : hasTextMessage(matcher.selectionName, s));
        return true;
    }

    @Override
    public boolean startsWith(final String prefix) {
        matcher.until(matcher.wait, new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return conditions().startsWith(prefix);
            }
        }, matcher.negation ? hasNotTextMessage(matcher.selectionName, prefix) : hasTextMessage(matcher.selectionName, prefix));
        return true;
    }

    @Override
    public boolean endsWith(final String suffix) {
        matcher.until(matcher.wait, new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return conditions().endsWith(suffix);
            }
        }, matcher.negation ? hasNotTextMessage(matcher.selectionName, suffix) : hasTextMessage(matcher.selectionName, suffix));
        return true;
    }

    @Override
    public boolean equals(final String anotherString) {
        matcher.until(matcher.wait, new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return conditions().equals(anotherString);
            }
        }, matcher.negation ? hasNotTextMessage(matcher.selectionName, anotherString) : hasTextMessage(matcher.selectionName, anotherString));
        return true;
    }

    @Override
    public boolean equalsIgnoreCase(final String anotherString) {
        matcher.until(matcher.wait, new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return conditions().equals(anotherString);
            }
        }, matcher.negation ? hasNotTextMessage(matcher.selectionName, anotherString) : hasTextMessage(matcher.selectionName, anotherString));
        return true;
    }

    @Override
    public boolean matches(final String regex) {
        matcher.until(matcher.wait, new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return conditions().matches(regex);
            }
        }, matcher.negation ? hasNotTextMessage(matcher.selectionName, regex) : hasTextMessage(matcher.selectionName, regex));
        return true;
    }

    @Override
    public boolean matches(final Pattern pattern) {
        matcher.until(matcher.wait, new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return conditions().matches(pattern);
            }
        }, matcher.negation ? hasNotTextMessage(matcher.selectionName, pattern.toString()) : hasTextMessage(matcher.selectionName, pattern.toString()));
        return true;
    }
}
