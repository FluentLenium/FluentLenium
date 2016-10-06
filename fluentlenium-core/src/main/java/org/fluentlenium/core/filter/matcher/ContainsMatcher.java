package org.fluentlenium.core.filter.matcher;

import java.util.regex.Pattern;

public class ContainsMatcher extends AbstractMatcher {

    public ContainsMatcher(final String value) {
        super(value);
    }

    public ContainsMatcher(final Pattern value) {
        super(value);
    }

    @Override
    public MatcherType getMatcherType() {
        return MatcherType.CONTAINS;
    }

    @Override
    public boolean isSatisfiedBy(final String o) {
        return CalculateService.contains(getPattern(), getValue(), o);
    }

}
