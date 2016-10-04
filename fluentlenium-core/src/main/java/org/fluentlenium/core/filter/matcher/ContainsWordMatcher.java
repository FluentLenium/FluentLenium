package org.fluentlenium.core.filter.matcher;

public class ContainsWordMatcher extends AbstractMacher {

    public ContainsWordMatcher(final String value) {
        super(value);
    }

    @Override
    public MatcherType getMatcherType() {
        return MatcherType.CONTAINS_WORD;
    }

    @Override
    public boolean isSatisfiedBy(final String o) {
        return CalculateService.contains(getPattern(), getValue(), o);
    }

}
