package org.fluentlenium.core.wait;

import org.fluentlenium.core.conditions.FluentConditions;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentListImpl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.search.Search;
import org.openqa.selenium.NoSuchElementException;

import java.util.List;

/**
 * Base Matcher for waiting on element list.
 */
public class FluentWaitElementListMatcher extends AbstractWaitElementListMatcher {

    static final String ELEMENTS = "Elements";

    private final List<? extends FluentWebElement> untilElements;

    protected FluentWaitElementListMatcher(Search search, FluentWait fluentWait, List<? extends FluentWebElement> untilElements) {
        super(search, fluentWait, ELEMENTS + " " + String.valueOf(untilElements));
        this.untilElements = untilElements;
    }

    @Override
    public FluentWaitElementListMatcher not() {
        FluentWaitElementListMatcher negatedConditions = new FluentWaitElementListMatcher(search, wait, untilElements);
        negatedConditions.negation = !negation;
        return negatedConditions;
    }

    @Override
    protected FluentList<FluentWebElement> find() {
        FluentListImpl<FluentWebElement> elements = new FluentListImpl<>(FluentWebElement.class, null);
        if (untilElements != null) {
            try {
                for (FluentWebElement untilElement : untilElements) {
                    elements.add(untilElement.now());
                }
            } catch (NoSuchElementException e) {
                // untilElement is a proxy element and is not found.
            }
        }
        return elements;
    }
}
