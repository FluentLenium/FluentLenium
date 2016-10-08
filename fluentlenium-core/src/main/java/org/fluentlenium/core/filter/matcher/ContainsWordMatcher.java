package org.fluentlenium.core.filter.matcher;

public class ContainsWordMatcher extends AbstractMatcher {

    public ContainsWordMatcher(final String value) {
        super(value);
    }

    @Override
    public MatcherType getMatcherType() {
        return MatcherType.CONTAINS_WORD;
    }

    @Override
    public boolean isSatisfiedBy(final String obj) {
        return CalculateService.contains(getPattern(), getValue(), obj);
    }

}
