package org.fluentlenium.core.filter.matcher;

import java.util.regex.Pattern;

public class NotContainsMatcher extends AbstractMatcher {

    public NotContainsMatcher(final String value) {
        super(value);
    }

    public NotContainsMatcher(final Pattern pattern) {
        super(pattern);
    }

    @Override
    public MatcherType getMatcherType() {
        return MatcherType.NOT_CONTAINS;
    }

    @Override
    public boolean isSatisfiedBy(final String o) {
        return !CalculateService.contains(getPattern(), getValue(), o);
    }

}
