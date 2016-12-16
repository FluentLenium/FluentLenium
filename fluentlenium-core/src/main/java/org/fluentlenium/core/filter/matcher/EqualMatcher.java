package org.fluentlenium.core.filter.matcher;

import java.util.regex.Pattern;

/**
 * Matcher checking that actual is equal to input value.
 */
public class EqualMatcher extends AbstractMatcher {

    /**
     * Creates a equal matcher.
     *
     * @param value input value
     */
    public EqualMatcher(String value) {
        super(value);
    }

    /**
     * Creates a equal matcher.
     *
     * @param value input value
     */
    public EqualMatcher(Pattern value) {
        super(value);
    }

    @Override
    public MatcherType getMatcherType() {
        return MatcherType.EQUALS;
    }

    @Override
    public boolean isSatisfiedBy(String obj) {
        return CalculateService.equal(getPattern(), getValue(), obj);
    }

}
