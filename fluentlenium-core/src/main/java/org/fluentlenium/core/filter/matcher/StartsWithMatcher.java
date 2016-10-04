package org.fluentlenium.core.filter.matcher;

import java.util.regex.Pattern;

public class StartsWithMatcher extends Matcher {
    /**
     * Constructor using a string as a value
     *
     * @param value as string for class constructor
     */
    public StartsWithMatcher(String value) {
        super(value);
    }

    /**
     * Constructor using a pattern as a value
     *
     * @param value as pattern for class constructor
     */
    public StartsWithMatcher(Pattern value) {
        super(value);
    }

    @Override
    public MatcherType getMatcherType() {
        return MatcherType.START_WITH;
    }

    @Override
    public boolean isSatisfiedBy(String o) {
        return CalculateService.startsWith(getPattern(), getValue(), o);
    }

}
