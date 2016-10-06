package org.fluentlenium.core.filter;

import com.google.common.base.Predicate;
import org.fluentlenium.core.domain.FluentWebElement;

/**
 * Filter a FluentWebElement collection to return only the elements with the same text
 */
public class AttributeFilterPredicate implements Predicate<FluentWebElement> {
    private final AttributeFilter filter;

    public AttributeFilterPredicate(final AttributeFilter text) {
        this.filter = text;
    }

    public boolean apply(final FluentWebElement element) {

        final String attribute = getAttributeValue(element);
        return filter != null && filter.getMatcher().isSatisfiedBy(attribute);
    }

    private String getAttributeValue(final FluentWebElement element) {
        return "text".equalsIgnoreCase(filter.getAttribut())
                ? element.text()
                : element.attribute(filter.getAttribut());
    }

}
