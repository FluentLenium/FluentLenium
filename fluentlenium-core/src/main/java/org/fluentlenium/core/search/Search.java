package org.fluentlenium.core.search;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentListImpl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.filter.FilterPredicate;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Search implements SearchControl<FluentWebElement> {
    private final SearchContext searchContext;

    public Search(SearchContext context) {
        this.searchContext = context;
    }

    /**
     * Central methods to find elements on the page. Can provide some filters. Able to use css1, css2, css3, see WebDriver  restrictions
     *
     * @param name    elements name to find
     * @param filters filters set
     * @return fluent list of fluent web elements
     */
    @Override
    public FluentList<FluentWebElement> find(String name, final Filter... filters) {
        StringBuilder sb = new StringBuilder(name);
        List<Filter> postFilterSelector = new ArrayList<Filter>();
        if (filters != null && filters.length > 0) {
            for (Filter selector : filters) {
                if (selector.isPreFilter()) {
                    sb.append(selector.toString());
                } else {
                    postFilterSelector.add(selector);
                }
            }
        }
        Collection<FluentWebElement> postFiltered = select(sb.toString());
        for (Filter selector : postFilterSelector) {
            postFiltered = Collections2.filter(postFiltered, new FilterPredicate(selector));
        }

        return new FluentListImpl<FluentWebElement>(postFiltered);
    }

    private List<FluentWebElement> select(String cssSelector) {
        return Lists.transform(searchContext.findElements(By.cssSelector(cssSelector)),
                new Function<WebElement, FluentWebElement>() {
                    public FluentWebElement apply(WebElement webElement) {
                        return new FluentWebElement((webElement));
                    }
                });
    }

    private List<FluentWebElement> select(By locator) {
        return Lists.transform(searchContext.findElements(locator), new Function<WebElement, FluentWebElement>() {
            public FluentWebElement apply(WebElement webElement) {
                return new FluentWebElement((webElement));
            }
        });
    }

    /**
     * Central methods to find elements on the page with filters.
     *
     * @param filters filters set
     * @return fluent list of fluent web elements
     */
    @Override
    public FluentList<FluentWebElement> find(Filter... filters) {
        if (filters == null || filters.length == 0) {
            throw new IllegalArgumentException("cssSelector or filter is required");
        }
        return find("*", filters);
    }

    /**
     * Return the elements at the number position into the the lists corresponding to the cssSelector with it filters
     *
     * @param selector elements name to find
     * @param number   index of element in the list
     * @param filters  filters set
     * @return fluent web element
     */
    @Override
    public FluentWebElement find(String selector, Integer number, final Filter... filters) {
        List<FluentWebElement> listFiltered = find(selector, filters);
        if (number >= listFiltered.size()) {
            throw new NoSuchElementException(
                    "No such element with position: " + number + ". Number of elements available: " + listFiltered
                            .size() + ". Selector: " + selector + ".");
        }
        return listFiltered.get(number);
    }

    /**
     * Return the element at the number position in the lists corresponding to the filters
     *
     * @param index   index of element in container
     * @param filters filters set
     * @return fluent web element
     */
    @Override
    public FluentWebElement find(Integer index, Filter... filters) {
        if (filters == null || filters.length == 0) {
            throw new IllegalArgumentException("cssSelector or filter is required");
        }
        return find("*", index, filters);
    }

    /**
     * Central methods to find elements on the page. Can provide some filters. Able to use css1, css2, css3, see WebDriver  restrictions
     *
     * @param locator elements locator
     * @param filters filters set
     * @return fluent list of fluent web elements
     */
    @Override
    public FluentList<FluentWebElement> find(By locator, final Filter... filters) {
        List<FluentWebElement> preFiltered = select(locator);
        Collection<FluentWebElement> postFiltered = preFiltered;
        for (Filter selector : filters) {
            postFiltered = Collections2.filter(postFiltered, new FilterPredicate(selector));
        }

        return new FluentListImpl<>(postFiltered);
    }

    @Override
    public FluentList<FluentWebElement> $(String selector, Filter... filters) {
        return find(selector, filters);
    }

    @Override
    public FluentList<FluentWebElement> $(Filter... filters) {
        return find(filters);
    }

    @Override
    public FluentList<FluentWebElement> $(By locator, Filter... filters) {
        return find(locator, filters);
    }

    @Override
    public FluentWebElement $(Integer index, Filter... filters) {
        return find(index, filters);
    }

    /**
     * Return the elements at the number position into the the lists corresponding to the cssSelector with it filters
     *
     * @param locator elements locator
     * @param index   index of element in the list
     * @param filters filters set
     * @return fluent web element
     */
    @Override
    public FluentWebElement find(By locator, Integer index, final Filter... filters) {
        List<FluentWebElement> listFiltered = find(locator, filters);
        if (index >= listFiltered.size()) {
            throw new NoSuchElementException(
                    "No such element with position :" + index + ". Number of elements available :" + listFiltered
                            .size());
        }
        return listFiltered.get(index);
    }

    @Override
    public FluentWebElement $(String selector, Integer index, Filter... filters) {
        return find(selector, index, filters);
    }

    @Override
    public FluentWebElement $(By locator, Integer index, Filter... filters) {
        return find(locator, index, filters);
    }

    /**
     * Return the first elements corresponding to the name and the filters
     *
     * @param selector element name to find
     * @param filters  filters set
     * @return fluent web element
     */
    @Override
    public FluentWebElement findFirst(String selector, final Filter... filters) {
        FluentList fluentList = find(selector, filters);
        try {
            return fluentList.first();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Could not find element matching selector: " + selector + ".", e);
        }
    }

    /**
     * Return the first element corresponding to the filters.
     *
     * @param filters filters set
     * @return fluent web element
     */
    @Override
    public FluentWebElement findFirst(Filter... filters) {
        if (filters == null || filters.length == 0) {
            throw new IllegalArgumentException("cssSelector or filter is required");
        }
        return findFirst("*", filters);
    }

    /**
     * Return the first elements corresponding to the name and the filters
     *
     * @param locator element locator
     * @param filters filters set
     * @return fluent web element
     */
    @Override
    public FluentWebElement findFirst(By locator, final Filter... filters) {
        FluentList<FluentWebElement> fluentList = find(locator, filters);
        return fluentList.first();
    }
}
