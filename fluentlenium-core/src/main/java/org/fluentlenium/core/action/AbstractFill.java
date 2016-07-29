package org.fluentlenium.core.action;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.search.SearchControl;
import org.openqa.selenium.By;

public class AbstractFill {
    private SearchControl<? extends FluentWebElement> search;
    private String cssSelector;
    private Filter[] filters;
    private By bySelector;
    private FluentList<FluentWebElement> fluentList;

    public AbstractFill(SearchControl<? extends FluentWebElement> search, String cssSelector, Filter... filters) {
        this.search = search;
        this.cssSelector = cssSelector;
        this.filters = filters;
    }

    public AbstractFill(SearchControl<? extends FluentWebElement> search, By bySelector, Filter... filters) {
        this.search = search;
        this.bySelector = bySelector;
        this.filters = filters;
    }

    public AbstractFill(SearchControl<? extends FluentWebElement> search, Filter... filters) {
        this.search = search;
        this.cssSelector = "*";
        this.filters = filters;
    }

    public AbstractFill(FluentList<FluentWebElement> list, Filter... filters) {
        this.filters = filters.clone();
        this.fluentList = list;
    }

    public AbstractFill(FluentWebElement element, Filter... filters) {
        this(element.asList(), filters);
    }

    protected FluentList<FluentWebElement> findElements() {
        if (fluentList != null) {
            return fluentList;
        } else if (cssSelector != null) {
            return (FluentList<FluentWebElement>) search.find(cssSelector, filters);
        } else {
            return (FluentList<FluentWebElement>) search.find(bySelector, filters);
        }
    }
}
