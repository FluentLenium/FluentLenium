package org.fluentlenium.core.filter.matcher;

import java.util.regex.Pattern;

public class ContainsMatcher extends AbstractMacher {

    public ContainsMatcher(String value) {
        super(value);
    }

    public ContainsMatcher(Pattern value) {
        super(value);
    }

    @Override
    public MatcherType getMatcherType() {
        return MatcherType.CONTAINS;
    }

    @Override
    public boolean isSatisfiedBy(String o) {
        return CalculateService.contains(getPattern(), getValue(), o);
    }

}
