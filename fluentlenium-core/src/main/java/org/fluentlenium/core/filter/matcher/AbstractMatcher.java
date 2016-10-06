package org.fluentlenium.core.filter.matcher;

import java.util.regex.Pattern;

public abstract class AbstractMatcher {
    private String value;
    private Pattern pattern;

    protected AbstractMatcher(final String value) {
        this.value = value;
    }

    protected AbstractMatcher(final Pattern value) {
        this.pattern = value;
    }

    /**
     * return the given value
     *
     * @return value of matcher
     */
    public String getValue() {
        return value;
    }

    /**
     * Return the matcher symbol
     *
     * @return matcher symbol
     */
    public String getMatcherSymbol() {
        return getMatcherType() == null ? null : getMatcherType().getCssRepresentations();
    }

    public final boolean isCssFilterSupported() {
        return pattern == null && getMatcherSymbol() != null;
    }

    /**
     * return the pattern
     *
     * @return pattern
     */
    protected Pattern getPattern() {
        return pattern;
    }

    /**
     * Return the matcher type
     *
     * @return matcher type
     */
    protected abstract MatcherType getMatcherType();

    /**
     * Check if the matcher is matched given the value
     *
     * @param value define the object of check name
     * @return boolean value for isSatisfiedBy
     */
    public abstract boolean isSatisfiedBy(String value);

}
