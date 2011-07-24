package fr.java.freelance.fluentlenium.filter;

import com.google.common.base.Predicate;
import com.sun.istack.internal.Nullable;
import fr.java.freelance.fluentlenium.domain.FluentWebElement;

/**
 * Filter a FluentWebElement collection to return only the elements with the same text
 */
public class FilterPredicate implements Predicate<FluentWebElement> {
    private final Filter filter;

    public FilterPredicate(Filter text) {
        this.filter = text;
    }

    public boolean apply(@Nullable FluentWebElement webElementCustom) {

        String attribute = returnTextIfTextAttributeElseAttributeValue(webElementCustom);
        if (filter.getMatcher().isSatisfiedBy(attribute)) {
            return true;
        }
        return false;
    }

    private String returnTextIfTextAttributeElseAttributeValue(FluentWebElement webElementCustom) {
        return (filter.getAttribut().equalsIgnoreCase("text")) ? webElementCustom.getText() : webElementCustom.getAttrbibute(filter.getAttribut());
    }

}
