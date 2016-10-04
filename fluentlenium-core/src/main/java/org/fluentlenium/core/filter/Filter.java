package org.fluentlenium.core.filter;

import org.fluentlenium.core.filter.matcher.AbstractMacher;
import org.fluentlenium.core.filter.matcher.EqualMatcher;

import java.util.Locale;

public class Filter {
    private final String attribut;
    private final AbstractMacher matcher;

    /**
     * Construct a filter with a type and an associated value
     *
     * @param filterType filter type
     * @param value      value to filter
     */
    public Filter(final FilterType filterType, final String value) {
        this.attribut = filterType.name();
        this.matcher = new EqualMatcher(value);
    }

    /**
     * Construct a filter with a type and an associated matcher
     *
     * @param filterType filter type
     * @param matcher    matcher
     */
    public Filter(final FilterType filterType, final AbstractMacher matcher) {
        this.attribut = filterType.name();
        this.matcher = matcher;
    }

    /**
     * Construct a filter with a custom attribute and an associated value
     *
     * @param customAttribute custom attribute name
     * @param value           custom attribute value
     */
    public Filter(final String customAttribute, final String value) {
        this.attribut = customAttribute;
        this.matcher = new EqualMatcher(value);
    }

    /**
     * Construct a filter with a custom attribute and an associated matcher
     *
     * @param customAttribute custom attribute name
     * @param matcher         matcher
     */
    public Filter(final String customAttribute, final AbstractMacher matcher) {
        this.attribut = customAttribute;
        this.matcher = matcher;
    }

    public String getAttribut() {
        return attribut.toLowerCase(Locale.ENGLISH);
    }

    public String toString() {
        String matcherAttribute = matcher == null ? null : matcher.getMatcherSymbol();
        if (matcherAttribute == null) {
            matcherAttribute = "";
        }
        return "[" + attribut.toLowerCase(Locale.ENGLISH) + matcherAttribute + "=\"" + matcher.getValue() + "\"]";
    }

    public AbstractMacher getMatcher() {
        return matcher;
    }

    public boolean isPreFilter() {
        return matcher != null && matcher.isPreFilter() && !FilterType.TEXT.name().equalsIgnoreCase(getAttribut());
    }
}
