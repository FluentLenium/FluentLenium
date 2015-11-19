package org.fluentlenium.core.filter;

import org.fluentlenium.core.filter.matcher.EqualMatcher;
import org.fluentlenium.core.filter.matcher.Matcher;


public class Filter {
    private final String attribut;
    private final Matcher matcher;

    /**
     * Construct a filter with a type and an associated value
     *
     * @param filterType filter type
     * @param value      value to filter
     */
    public Filter(FilterType filterType, String value) {
        this.attribut = filterType.name();
        this.matcher = new EqualMatcher(value);
    }

    /**
     * Construct a filter with a type and an associated matcher
     *
     * @param filterType filter type
     * @param matcher    matcher
     */
    public Filter(FilterType filterType, Matcher matcher) {
        this.attribut = filterType.name();
        this.matcher = matcher;
    }

    /**
     * Construct a filter with a custom attribute and an associated value
     *
     * @param customAttribute custom attribute name
     * @param value           custom attribute value
     */
    public Filter(String customAttribute, String value) {
        this.attribut = customAttribute;
        this.matcher = new EqualMatcher(value);
    }


    /**
     * Construct a filter with a custom attribute and an associated matcher
     *
     * @param customAttribute custom attribute name
     * @param matcher         matcher
     */
    public Filter(String customAttribute, Matcher matcher) {
        this.attribut = customAttribute;
        this.matcher = matcher;
    }

    public String getAttribut() {
        return attribut.toLowerCase();
    }

    public String toString() {
        String matcherAttribute = matcher != null ? matcher.getMatcherSymbol() : "";
        return "[" + attribut.toLowerCase() + matcherAttribute + "=\"" + matcher.getValue() + "\"]";
    }

    public Matcher getMatcher() {
        return matcher;
    }

    public boolean isPreFilter() {
        if ((matcher != null && matcher.isPreFilter()) && !FilterType.TEXT.name().equalsIgnoreCase(getAttribut())) {
            return true;
        }
        return false;
    }
}
