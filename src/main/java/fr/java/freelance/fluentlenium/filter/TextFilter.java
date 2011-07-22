package fr.java.freelance.fluentlenium.filter;

import com.google.common.base.Predicate;
import com.sun.istack.internal.Nullable;
import fr.java.freelance.fluentlenium.domain.FluentWebElement;

/**
 * Filter a FluentWebElement collection to return only the elements with the same text
 */
public class TextFilter implements Predicate<FluentWebElement> {
    private final String text;

    public TextFilter(String text) {
        this.text = text;
    }

    public boolean apply(@Nullable FluentWebElement webElementCustom) {
        if (text.equals(webElementCustom.getText())) {
            return true;
        }
        return false;
    }

}
