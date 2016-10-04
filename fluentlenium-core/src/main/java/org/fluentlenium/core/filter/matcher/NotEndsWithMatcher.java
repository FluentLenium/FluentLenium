package org.fluentlenium.core.filter.matcher;

import java.util.regex.Pattern;

public class NotEndsWithMatcher extends AbstractMacher {

    public NotEndsWithMatcher(final String value) {
        super(value);
    }

    public NotEndsWithMatcher(final Pattern value) {
        super(value);
    }

    @Override
    public MatcherType getMatcherType() {
        return MatcherType.NOT_END_WITH;
    }

    @Override
    public boolean isSatisfiedBy(final String o) {
        return !CalculateService.endsWith(getPattern(), getValue(), o);
    }

}
