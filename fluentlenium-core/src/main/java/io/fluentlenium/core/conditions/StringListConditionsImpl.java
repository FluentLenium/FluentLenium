package io.fluentlenium.core.conditions;

import io.fluentlenium.core.domain.FluentWebElement;

import java.util.function.Function;
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
    public StringListConditionsImpl(Conditions<FluentWebElement> conditions, Function<FluentWebElement, String> objectGetter,
                                    Function<FluentWebElement, StringConditions> conditionsGetter) {
        super(conditions, objectGetter, conditionsGetter);
    }

    @Override
    public StringListConditionsImpl not() {
        return new StringListConditionsImpl(conditions.not(), objectGetter, conditionsGetter);
    }

    @Override
    public boolean contains(CharSequence charSequence) {
        return conditions.verify(input -> conditionsGetter.apply(input).contains(charSequence));
    }

    @Override
    public boolean startsWith(String prefix) {
        return conditions.verify(input -> conditionsGetter.apply(input).startsWith(prefix));
    }

    @Override
    public boolean endsWith(String suffix) {
        return conditions.verify(input -> conditionsGetter.apply(input).endsWith(suffix));
    }

    @Override
    public boolean equalTo(String anotherString) {
        return conditions.verify(input -> conditionsGetter.apply(input).equalTo(anotherString));
    }

    @Override
    public boolean equalToIgnoreCase(String anotherString) {
        return conditions.verify(input -> conditionsGetter.apply(input).equalToIgnoreCase(anotherString));
    }

    @Override
    public boolean matches(String regex) {
        return conditions.verify(input -> conditionsGetter.apply(input).matches(regex));
    }

    @Override
    public boolean matches(Pattern pattern) {
        return conditions.verify(input -> conditionsGetter.apply(input).matches(pattern));
    }
}
