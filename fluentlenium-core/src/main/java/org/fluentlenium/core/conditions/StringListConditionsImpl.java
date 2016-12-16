package org.fluentlenium.core.conditions;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import org.fluentlenium.core.domain.FluentWebElement;

import java.util.regex.Pattern;

/**
 * Conditions for list of string.
 */
public class StringListConditionsImpl extends BaseObjectListConditions<String, StringConditions> implements StringConditions {
    /**
     * Creates a new list of string conditions
     *
     * @param conditions       string conditions
     * @param objectGetter     getter of the underlying string
     * @param conditionsGetter getter of the underlying string conditions
     */
    public StringListConditionsImpl(Conditions<FluentWebElement> conditions,
            Function<FluentWebElement, String> objectGetter,
            Function<FluentWebElement, StringConditions> conditionsGetter) {
        super(conditions, objectGetter, conditionsGetter);
    }

    @Override
    public StringListConditionsImpl not() {
        return new StringListConditionsImpl(conditions.not(), objectGetter, conditionsGetter);
    }

    @Override
    public boolean contains(final CharSequence charSequence) {
        return conditions.verify(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return conditionsGetter.apply(input).contains(charSequence);
            }
        });
    }

    @Override
    public boolean startsWith(final String prefix) {
        return conditions.verify(new Predicate<FluentWebElement>() {

            @Override
            public boolean apply(FluentWebElement input) {
                return conditionsGetter.apply(input).startsWith(prefix);
            }
        });
    }

    @Override
    public boolean endsWith(final String suffix) {
        return conditions.verify(new Predicate<FluentWebElement>() {

            @Override
            public boolean apply(FluentWebElement input) {
                return conditionsGetter.apply(input).endsWith(suffix);
            }
        });
    }

    @Override
    public boolean equalTo(final String anotherString) {
        return conditions.verify(new Predicate<FluentWebElement>() {

            @Override
            public boolean apply(FluentWebElement input) {
                return conditionsGetter.apply(input).equalTo(anotherString);
            }
        });
    }

    @Override
    public boolean equalToIgnoreCase(final String anotherString) {
        return conditions.verify(new Predicate<FluentWebElement>() {

            @Override
            public boolean apply(FluentWebElement input) {
                return conditionsGetter.apply(input).equalToIgnoreCase(anotherString);
            }
        });
    }

    @Override
    public boolean matches(final String regex) {
        return conditions.verify(new Predicate<FluentWebElement>() {

            @Override
            public boolean apply(FluentWebElement input) {
                return conditionsGetter.apply(input).matches(regex);
            }
        });
    }

    @Override
    public boolean matches(final Pattern pattern) {
        return conditions.verify(new Predicate<FluentWebElement>() {

            @Override
            public boolean apply(FluentWebElement input) {
                return conditionsGetter.apply(input).matches(pattern);
            }
        });
    }
}
