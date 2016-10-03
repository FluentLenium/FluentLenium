package org.fluentlenium.core.filter.matcher;

public class ContainsWordMatcher extends Matcher {

    public ContainsWordMatcher(String value) {
        super(value);
    }

    @Override
    public MatcherType getMatcherType() {
        return MatcherType.CONTAINS_WORD;
    }

    @Override
    public boolean isSatisfiedBy(String o) {
        return CalculateService.contains(getPattern(), getValue(), o);
    }

}
