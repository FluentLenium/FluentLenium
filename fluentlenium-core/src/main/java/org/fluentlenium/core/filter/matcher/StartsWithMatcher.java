package org.fluentlenium.core.filter.matcher;

import java.util.regex.Pattern;

/**
 * Matcher checking that actual starts with input value.
 */
public class StartsWithMatcher extends AbstractMatcher {
    /**
     * Creates a starts with matcher.
     *
     * @param value input value
     */
    public StartsWithMatcher(String value) {
        super(value);
    }

    /**
     * Creates a starts with matcher.
     *
     * @param value input value
     */
    public StartsWithMatcher(Pattern value) {
        super(value);
    }

    @Override
    public MatcherType getMatcherType() {
        return MatcherType.STARTS_WITH;
    }

    @Override
    public boolean isSatisfiedBy(String currentValue) {
        return CalculateService.startsWith(getPattern(), getValue(), currentValue);
    }
}
