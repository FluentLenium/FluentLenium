package org.fluentlenium.core.filter.matcher;

/**
 * Matcher checking that actual contains word from input value.
 */
public class ContainsWordMatcher extends AbstractMatcher {

    /**
     * Creates a not contains word matcher.
     *
     * @param value input value
     */
    public ContainsWordMatcher(String value) {
        super(value);
    }

    @Override
    public MatcherType getMatcherType() {
        return MatcherType.CONTAINS_WORD;
    }

    @Override
    public boolean isSatisfiedBy(String obj) {
        return CalculateService.contains(getPattern(), getValue(), obj);
    }

}
