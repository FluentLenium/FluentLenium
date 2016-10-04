package org.fluentlenium.core.filter.matcher;

import java.util.regex.Pattern;

public class NotStartsWithMatcher extends AbstractMacher {

    public NotStartsWithMatcher(String value) {
        super(value);
    }

    public NotStartsWithMatcher(Pattern value) {
        super(value);
    }

    @Override
    public MatcherType getMatcherType() {
        return MatcherType.NOT_START_WITH;
    }

    @Override
    public boolean isSatisfiedBy(String o) {
        return !CalculateService.startsWith(getPattern(), getValue(), o);
    }

}
