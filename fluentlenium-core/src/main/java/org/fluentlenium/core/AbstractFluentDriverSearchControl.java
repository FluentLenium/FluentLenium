package org.fluentlenium.core;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.search.Search;
import org.fluentlenium.core.search.SearchFilter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Abstract {@link org.fluentlenium.core.search.SearchControl} implemetation for {@link FluentDriver}.
 */
abstract class AbstractFluentDriverSearchControl extends AbstractFluentDriverComponentInstantiator {

    protected AbstractFluentDriverSearchControl(FluentControl adapter) {
        super(adapter);
    }

    /**
     * Return the {@link Search} required for this class.
     *
     * @return the Search object
     */
    protected abstract Search getSearch();

    @Override
    public FluentList<FluentWebElement> find(String selector, SearchFilter... filters) {
        return getSearch().find(selector, filters);
    }

    @Override
    public FluentList<FluentWebElement> find(By locator, SearchFilter... filters) {
        return getSearch().find(locator, filters);
    }

    @Override
    public FluentList<FluentWebElement> find(SearchFilter... filters) {
        return getSearch().find(filters);
    }

    @Override
    public FluentList<FluentWebElement> find(List<WebElement> rawElements) {
        return getSearch().find(rawElements);
    }

    @Override
    public FluentList<FluentWebElement> $(List<WebElement> rawElements) {
        return getSearch().$(rawElements);
    }

    @Override
    public FluentWebElement el(WebElement rawElement) {
        return getSearch().el(rawElement);
    }
}
