package org.fluentlenium.core.filter.matcher;


import java.util.regex.Pattern;

public abstract class Matcher {
    private String value;
    private Pattern pattern;

    protected Matcher(final String value) {
        this.value = value;
    }

    protected Matcher(final Pattern value) {
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
        return getMatcherType() != null ? getMatcherType().getCssRepresentations() : null;
    }

    public final boolean isPreFilter() {
        if (pattern != null || null == getMatcherSymbol()) {
            return false;
        }
        return true;
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
