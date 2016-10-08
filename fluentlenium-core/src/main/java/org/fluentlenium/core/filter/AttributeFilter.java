package org.fluentlenium.core.filter;

import com.google.common.collect.Collections2;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.filter.matcher.AbstractMatcher;
import org.fluentlenium.core.filter.matcher.EqualMatcher;
import org.fluentlenium.core.search.SearchFilter;

import java.util.Collection;
import java.util.Locale;

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
    public AttributeFilter(final String attributeName, final String value) {
        this.attributeName = attributeName;
        this.matcher = new EqualMatcher(value);
    }

    /**
     * Construct a filter with a custom attribute and an associated matcher
     *
     * @param customAttribute custom attribute name
     * @param matcher         matcher
     */
    public AttributeFilter(final String customAttribute, final AbstractMatcher matcher) {
        this.attributeName = customAttribute;
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
        return getCssFilter();
    }

    @Override
    public String getCssFilter() {
        String matcherAttribute = matcher == null ? null : matcher.getMatcherSymbol();
        if (matcherAttribute == null) {
            matcherAttribute = "";
        }
        return "[" + attributeName.toLowerCase(Locale.ENGLISH) + matcherAttribute + "=\"" + matcher.getValue() + "\"]";
    }

    @Override
    public boolean isCssFilterSupported() {
        return matcher != null && matcher.isCssFilterSupported() && !"text".equalsIgnoreCase(getAttribut()) && !"textContent"
                .equalsIgnoreCase(getAttribut());
    }

    @Override
    public Collection<FluentWebElement> applyFilter(final Collection<FluentWebElement> elements) {
        return Collections2.filter(elements, new AttributeFilterPredicate(this));
    }
}
