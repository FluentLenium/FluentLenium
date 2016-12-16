package org.fluentlenium.core.filter.matcher;

import java.util.regex.Pattern;

/**
 * Matcher checking that actual doesn't contain input value.
 */
public class NotContainsMatcher extends AbstractMatcher {

    /**
     * Creates a not contains matcher.
     *
     * @param value input value
     */
    public NotContainsMatcher(String value) {
        super(value);
    }

    /**
     * Creates a not contains matcher.
     *
     * @param value input value
     */
    public NotContainsMatcher(Pattern value) {
        super(value);
    }

    @Override
    public MatcherType getMatcherType() {
        return MatcherType.NOT_CONTAINS;
    }

    @Override
    public boolean isSatisfiedBy(String obj) {
        return !CalculateService.contains(getPattern(), getValue(), obj);
    }

}
