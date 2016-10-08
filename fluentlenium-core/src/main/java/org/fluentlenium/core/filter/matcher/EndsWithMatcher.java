package org.fluentlenium.core.filter.matcher;

import java.util.regex.Pattern;

public class EndsWithMatcher extends AbstractMatcher {

    public EndsWithMatcher(final String value) {
        super(value);
    }

    public EndsWithMatcher(final Pattern value) {
        super(value);
    }

    @Override
    public MatcherType getMatcherType() {
        return MatcherType.END_WITH;
    }

    @Override
    public boolean isSatisfiedBy(final String obj) {
        return CalculateService.endsWith(getPattern(), getValue(), obj);
    }

}
