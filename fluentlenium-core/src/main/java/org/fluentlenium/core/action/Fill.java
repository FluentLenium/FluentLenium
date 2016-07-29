package org.fluentlenium.core.action;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.search.SearchControl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Fill extends AbstractFill {
    public Fill(SearchControl<? extends FluentWebElement> search, String cssSelector, Filter... filters) {
        super(search, cssSelector, filters);
    }

    public Fill(SearchControl<? extends FluentWebElement> search, By bySelector, Filter... filters) {
        super(search, bySelector, filters);
    }

    public Fill(SearchControl<? extends FluentWebElement> search, Filter... filters) {
        super(search, filters);
    }

    public Fill(FluentList<FluentWebElement> list, Filter... filters) {
        super(list, filters);
    }

    public Fill(FluentWebElement element, Filter... filters) {
        super(element, filters);
    }

    /**
     * Set the values params as text for the fluentList or search a new list with the css selector and filters and add the values param on it
     *
     * @param textValues value to search
     * @return fill constructor
     */
    public Fill with(String... textValues) {
        findElements().text(textValues);
        return this;
    }

    public Fill withText
            (String... textValues) {
        return with(textValues);
    }
}
