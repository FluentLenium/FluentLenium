package org.fluentlenium.core.filter.matcher;

import java.util.regex.Pattern;

/**
 * Abstract search matcher.
 */
public abstract class AbstractMatcher {
    private String value;
    private Pattern pattern;

    /**
     * Creates a new abstract search matcher.
     *
     * @param value string predicate
     */
    protected AbstractMatcher(String value) {
        this.value = value;
    }

    /**
     * Creates a new abstract search matcher.
     *
     * @param value regular expression predicate
     */
    protected AbstractMatcher(Pattern value) {
        pattern = value;
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
        return getMatcherType() == null ? null : getMatcherType().getCssRepresentation();
    }

    @Override
    public String toString() {
        return getMatcherType() == null ? String.valueOf(null) : getMatcherType().getLabel();
    }

    /**
     * Does this matcher supports CSS filtering.
     *
     * @return true if css filtering is supported, false otherwise
     */
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
