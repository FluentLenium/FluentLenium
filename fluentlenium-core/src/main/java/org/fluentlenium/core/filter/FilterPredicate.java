package org.fluentlenium.core.filter;

import com.google.common.base.Predicate;
import org.fluentlenium.core.domain.FluentWebElement;

/**
 * Filter a FluentWebElement collection to return only the elements with the same text
 */
public class FilterPredicate implements Predicate<FluentWebElement> {
    private final Filter filter;

    public FilterPredicate(final Filter text) {
        this.filter = text;
    }

    public boolean apply(final FluentWebElement webElementCustom) {

        final String attribute = returnTextIfTextAttributeElseAttributeValue(webElementCustom);
        return filter != null && filter.getMatcher().isSatisfiedBy(attribute);
    }

    private String returnTextIfTextAttributeElseAttributeValue(final FluentWebElement webElementCustom) {
        return "text".equalsIgnoreCase(filter.getAttribut())
                ? webElementCustom.text()
                : webElementCustom.attribute(filter.getAttribut());
    }

}
