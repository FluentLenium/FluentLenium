package org.fluentlenium.core.filter;

import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.filter.matcher.AbstractMatcher;
import org.fluentlenium.core.filter.matcher.EqualMatcher;
import org.fluentlenium.core.search.SearchFilter;

import java.util.Collection;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Search filter based on attribute value.
 */
public class AttributeFilter implements SearchFilter {
    private final String attributeName;
    private final AbstractMatcher matcher;

    /**
     * Construct a filter with an attribute name and an associated value
     *
     * @param attributeName attribute name
     * @param value         value to filter
     */
    public AttributeFilter(String attributeName, String value) {
        this.attributeName = attributeName;
        matcher = new EqualMatcher(value);
    }

    /**
     * Construct a filter with a custom attribute and an associated matcher
     *
     * @param customAttribute custom attribute name
     * @param matcher         matcher
     */
    public AttributeFilter(String customAttribute, AbstractMatcher matcher) {
        attributeName = customAttribute;
        this.matcher = matcher;
    }

    /**
     * Get the attribute name (lower case).
     *
     * @return attribute name (lower case)
     */
    public String getAttribut() {
        return attributeName.toLowerCase(Locale.ENGLISH);
    }

    /**
     * Get the matcher of this filter
     *
     * @return matcher
     */
    public AbstractMatcher getMatcher() {
        return matcher;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("with ").append(getAttribut());

        String matcherRepr = matcher == null ? null : matcher.toString();
        if (matcherRepr != null) {
            stringBuilder.append(' ').append(matcherRepr).append(' ');
        }

        stringBuilder.append('"').append(matcher.getValue()).append('"');

        return stringBuilder.toString();
    }

    @Override
    public String getCssFilter() {
        String matcherAttribute = matcher == null ? null : matcher.getMatcherSymbol();
        if (matcherAttribute == null) {
            matcherAttribute = "";
        }
        return "[" + getAttribut() + matcherAttribute + "=\"" + matcher.getValue() + "\"]";
    }

    @Override
    public boolean isCssFilterSupported() {
        return matcher != null && matcher.isCssFilterSupported() && !"text".equalsIgnoreCase(getAttribut()) && !"textContent"
                .equalsIgnoreCase(getAttribut());
    }

    @Override
    public Collection<FluentWebElement> applyFilter(Collection<FluentWebElement> elements) {
        return elements.stream().filter(new AttributeFilterPredicate(this)).collect(Collectors.toList());
    }
}
