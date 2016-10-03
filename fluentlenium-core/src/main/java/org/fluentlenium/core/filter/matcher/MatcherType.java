package org.fluentlenium.core.filter.matcher;

/**
 * Different fluentlenium.integration. actually supported by the framework.
 * PreFilter are fluentlenium.integration. than are supported by WebDriver as CSS Selector
 * PostFilter are used after the webdriver selection to fluentlenium.integration. the collection
 */
public enum MatcherType {
    CONTAINS("*"), START_WITH("^"), END_WITH("$"), CONTAINS_WORD("~"), EQUAL(""), NOT_CONTAINS(null), NOT_START_WITH(
            null), NOT_END_WITH(null);

    private String cssRepresentations;

    MatcherType(String cssRepresentations) {
        this.cssRepresentations = cssRepresentations;
    }

    /**
     * Return the css representations of the matcher
     *
     * @return CSS representations
     */
    public String getCssRepresentations() {
        return cssRepresentations;
    }
}
