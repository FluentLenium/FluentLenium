package org.fluentlenium.core.filter.matcher;

/**
 * Matcher checking that actual contains word from input value.
 */
public class ContainsWordMatcher extends AbstractMatcher {

    /**
     * Creates a contains word matcher.
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
    public boolean isSatisfiedBy(String currentValue) {
        return CalculateService.contains(getPattern(), getValue(), currentValue);
    }
}
