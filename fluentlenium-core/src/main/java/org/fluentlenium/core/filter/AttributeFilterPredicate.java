package org.fluentlenium.core.filter;

import org.fluentlenium.core.domain.FluentWebElement;
import java.util.function.Predicate;

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
    AttributeFilterPredicate(AttributeFilter filter) {
        this.filter = filter;
    }

    @Override
    public boolean test(FluentWebElement element) {
        String attribute = getAttributeValue(element);
        return filter.getMatcher().isSatisfiedBy(attribute);
    }

    private String getAttributeValue(FluentWebElement element) {
        return "text".equalsIgnoreCase(filter.getAttribut()) ? element.text() : element.attribute(filter.getAttribut());
    }

}
