package org.fluentlenium.core.filter;

import com.google.common.base.Predicate;
import org.fluentlenium.core.domain.FluentWebElement;

/**
 * Filter a FluentWebElement collection to return only the elements with the same text
 */
public class AttributeFilterPredicate implements Predicate<FluentWebElement> {
    private final AttributeFilter filter;

    /**
     * Creates a new Attribute Filter Predicated, from an attribute filter
     *
     * @param filter attribute filter
     */
    public AttributeFilterPredicate(AttributeFilter filter) {
        this.filter = filter;
    }

    @Override
    public boolean apply(FluentWebElement element) {

        String attribute = getAttributeValue(element);
        return filter != null && filter.getMatcher().isSatisfiedBy(attribute);
    }

    private String getAttributeValue(FluentWebElement element) {
        return "text".equalsIgnoreCase(filter.getAttribut()) ? element.text() : element.attribute(filter.getAttribut());
    }

}
