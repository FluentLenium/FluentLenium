package org.fluentlenium.core.filter.matcher;


import java.util.regex.Pattern;

public class EqualMatcher extends Matcher {

    public EqualMatcher(String value) {
        super(value);
    }

    public EqualMatcher(Pattern pattern) {
        super(pattern);
    }

    @Override
    public MatcherType getMatcherType() {
        return MatcherType.EQUAL;
    }

    @Override
    public boolean isSatisfiedBy(String o) {
        return CalculateService.equal(getPattern(), getValue(), o);
    }

}
