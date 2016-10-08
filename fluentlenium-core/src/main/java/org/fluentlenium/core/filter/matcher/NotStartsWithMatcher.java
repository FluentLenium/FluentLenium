package org.fluentlenium.core.filter.matcher;

import java.util.regex.Pattern;

public class NotStartsWithMatcher extends AbstractMatcher {

    public NotStartsWithMatcher(final String value) {
        super(value);
    }

    public NotStartsWithMatcher(final Pattern value) {
        super(value);
    }

    @Override
    public MatcherType getMatcherType() {
        return MatcherType.NOT_START_WITH;
    }

    @Override
    public boolean isSatisfiedBy(final String obj) {
        return !CalculateService.startsWith(getPattern(), getValue(), obj);
    }

}
