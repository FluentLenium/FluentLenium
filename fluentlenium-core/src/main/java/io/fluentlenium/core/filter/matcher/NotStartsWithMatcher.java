package io.fluentlenium.core.filter.matcher;

import java.util.regex.Pattern;

/**
 * Matcher checking that actual doesn't start with input value.
 */
public class NotStartsWithMatcher extends AbstractMatcher {

    /**
     * Creates a not starts with matcher.
     *
     * @param value input value
     */
    public NotStartsWithMatcher(String value) {
        super(value);
    }

    /**
     * Creates a not starts with matcher.
     *
     * @param value input value
     */
    public NotStartsWithMatcher(Pattern value) {
        super(value);
    }

    @Override
    public MatcherType getMatcherType() {
        return MatcherType.NOT_STARTS_WITH;
    }

    @Override
    public boolean isSatisfiedBy(String currentValue) {
        return !CalculateService.startsWith(getPattern(), getValue(), currentValue);
    }
}
