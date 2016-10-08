package org.fluentlenium.core.filter.matcher;

import java.util.regex.Pattern;

public class EqualMatcher extends AbstractMatcher {

    public EqualMatcher(final String value) {
        super(value);
    }

    public EqualMatcher(final Pattern pattern) {
        super(pattern);
    }

    @Override
    public MatcherType getMatcherType() {
        return MatcherType.EQUAL;
    }

    @Override
    public boolean isSatisfiedBy(final String obj) {
        return CalculateService.equal(getPattern(), getValue(), obj);
    }

}
