package org.fluentlenium.core.filter.matcher;

/**
 * Matcher types actually actually supported by the framework.
 */
public enum MatcherType {
    /**
     * Contains.
     */
    CONTAINS("*"), /**
     * Starts with.
     */
    STARTS_WITH("^"), /**
     * Ends with.
     */
    ENDS_WITH("$"), /**
     * Contains word.
     */
    CONTAINS_WORD("~"), /**
     * Equal.
     */
    EQUALS(""), /**
     * Not contains.
     */
    NOT_CONTAINS(null), /**
     * Not starts with.
     */
    NOT_STARTS_WITH(null), /**
     * Not ends with.
     */
    NOT_ENDS_WITH(null);

    private final String cssRepresentation;

    /**
     * Creates a new matcher type enum value.
     *
     * @param cssRepresentation css representation.
     */
    MatcherType(final String cssRepresentation) {
        this.cssRepresentation = cssRepresentation;
    }

    /**
     * Return the css representation of the matcher
     *
     * @return CSS representation
     */
    public String getCssRepresentation() {
        return cssRepresentation;
    }
}
