package org.fluentlenium.core.wait;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentListImpl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.search.Search;
import org.openqa.selenium.NoSuchElementException;

/**
 * Base Matcher for waiting on single element.
 */
public class FluentWaitElementMatcher extends AbstractWaitElementMatcher {

    static final String ELEMENT = "Element";

    private final FluentWebElement untilElement;

    public FluentWaitElementMatcher(Search search, FluentWait fluentWait, FluentWebElement untilElement) {
        super(search, fluentWait, ELEMENT + " " + String.valueOf(untilElement));
        this.untilElement = untilElement;
    }

    @Override
    FluentList<FluentWebElement> find() {
        FluentListImpl<FluentWebElement> elements = new FluentListImpl<>();
        if (untilElement != null) {
            try {
                untilElement.getTagName();
                elements.add(untilElement);
            } catch (NoSuchElementException e) {
                // untilElement is a proxy element and is not found.
            }
        }
        return elements;
    }
}
