package org.fluentlenium.core.conditions;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import org.fluentlenium.core.domain.FluentWebElement;

import java.util.regex.Pattern;

/**
 * Conditions implement for string lists of elements.
 */
public class StringListConditionsImpl extends AbstractObjectListConditions<String, StringConditions> implements StringConditions {
    public StringListConditionsImpl(Conditions<FluentWebElement> listConditions,
                                    Function<FluentWebElement, String> stringGetter,
                                    Function<FluentWebElement, StringConditions> conditionsGetter) {
        super(listConditions, stringGetter, conditionsGetter);
    }

    @Override
    public StringListConditionsImpl not() {
        return new StringListConditionsImpl(this.conditions.not(), objectGetter, conditionsGetter);
    }

    @Override
    public boolean contains(final CharSequence s) {
        return this.conditions.isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return conditionsGetter.apply(input).contains(s);
            }
        });
    }

    @Override
    public boolean startsWith(final String prefix) {
        return this.conditions.isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return conditionsGetter.apply(input).startsWith(prefix);
            }
        });
    }

    @Override
    public boolean endsWith(final String suffix) {
        return this.conditions.isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return conditionsGetter.apply(input).endsWith(suffix);
            }
        });
    }

    @Override
    public boolean equals(final String anotherString) {
        return this.conditions.isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return conditionsGetter.apply(input).equals(anotherString);
            }
        });
    }

    @Override
    public boolean equalsIgnoreCase(final String anotherString) {
        return this.conditions.isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return conditionsGetter.apply(input).equalsIgnoreCase(anotherString);
            }
        });
    }

    @Override
    public boolean matches(final String regex) {
        return this.conditions.isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return conditionsGetter.apply(input).matches(regex);
            }
        });
    }

    @Override
    public boolean matches(final Pattern pattern) {
        return this.conditions.isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return conditionsGetter.apply(input).matches(pattern);
            }
        });
    }
}
