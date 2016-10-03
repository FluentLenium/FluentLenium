package org.fluentlenium.core.filter.matcher;

import java.util.regex.Pattern;

public class NotContainsMatcher extends Matcher {

    public NotContainsMatcher(String value) {
        super(value);
    }

    public NotContainsMatcher(Pattern pattern) {
        super(pattern);
    }

    @Override
    public MatcherType getMatcherType() {
        return MatcherType.NOT_CONTAINS;
    }

    @Override
    public boolean isSatisfiedBy(String o) {
        return !CalculateService.contains(getPattern(), getValue(), o);
    }

}
