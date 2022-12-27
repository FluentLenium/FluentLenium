package io.fluentlenium.core;

import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.core.search.Search;
import io.fluentlenium.core.search.SearchControl;
import io.fluentlenium.core.search.SearchFilter;
import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.core.search.Search;
import io.fluentlenium.core.search.SearchFilter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Abstract {@link SearchControl} implemetation for {@link FluentDriver}.
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
