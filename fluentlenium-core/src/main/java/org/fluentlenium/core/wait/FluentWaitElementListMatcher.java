package org.fluentlenium.core.wait;

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

    public FluentWaitElementListMatcher(Search search, FluentWait fluentWait, List<? extends FluentWebElement> untilElements) {
        super(search, fluentWait, ELEMENTS + " " + String.valueOf(untilElements));
        this.untilElements = untilElements;
    }

    @Override
    FluentList<FluentWebElement> find() {
        FluentListImpl<FluentWebElement> elements = new FluentListImpl<>();
        if (elements != null) {
            for (FluentWebElement untilElement : this.untilElements) {
                try {
                    untilElement.getTagName();
                    elements.add(untilElement);
                } catch (NoSuchElementException e) {
                    // untilElement is a proxy element and is not found.
                }
            }

        }
        return elements;
    }
}
