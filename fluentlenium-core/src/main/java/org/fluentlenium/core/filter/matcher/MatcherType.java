package org.fluentlenium.core.filter.matcher;

/**
 * Matcher types actually actually supported by the framework.
 */
public enum MatcherType {

    /**
     * Contains.
     */
    CONTAINS("contains", "*"),
    /**
     * Starts with.
     */
    STARTS_WITH("starts with", "^"),
    /**
     * Ends with.
     */
    ENDS_WITH("ends with", "$"),
    /**
     * Contains word.
     */
    CONTAINS_WORD("contains", "~"),
    /**
     * Equal.
     */
    EQUALS("equals to", ""),
    /**
     * Not contains.
     */
    NOT_CONTAINS("doesn't contain", null),
    /**
     * Not starts with.
     */
    NOT_STARTS_WITH("doesn't start with", null),
    /**
     * Not ends with.
     */
    NOT_ENDS_WITH("doesn't end with", null);

    private final String label;
    private final String cssRepresentation;

    /**
     * Creates a new matcher type enum value.
     *
     * @param label             label used in error message
     * @param cssRepresentation css representation.
     */
    MatcherType(String label, String cssRepresentation) {
        this.label = label;
        this.cssRepresentation = cssRepresentation;
    }

    /**
     * Get the label of this matcher type
     *
     * @return label
     */
    public String getLabel() {
        return label;
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
