package io.fluentlenium.core.filter;

import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.core.filter.matcher.AbstractMatcher;
import io.fluentlenium.core.filter.matcher.EqualMatcher;
import io.fluentlenium.core.search.SearchFilter;

import java.util.Collection;
import java.util.Optional;
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
    public String getAttribute() {
        return attributeName;
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
        stringBuilder.append("with ").append(getAttribute());

        Optional.ofNullable(matcher)
            .map(AbstractMatcher::toString)
            .ifPresent(matcherRepr -> stringBuilder.append(' ').append(matcherRepr));

        return stringBuilder.append(' ').append('"').append(matcher.getValue()).append('"').toString();
    }

    @Override
    public String getCssFilter() {
        String matcherAttribute = Optional.ofNullable(matcher).map(AbstractMatcher::getMatcherSymbol).orElse("");
        return "[" + getAttribute() + matcherAttribute + "=\"" + matcher.getValue() + "\"]";
    }

    @Override
    public boolean isCssFilterSupported() {
        return matcher != null
            && matcher.isCssFilterSupported()
            && !"text".equalsIgnoreCase(getAttribute())
            && !"textContent".equalsIgnoreCase(getAttribute());
    }

    @Override
    public Collection<FluentWebElement> applyFilter(Collection<FluentWebElement> elements) {
        return elements.stream().filter(new AttributeFilterPredicate(this)).collect(Collectors.toList());
    }
}
