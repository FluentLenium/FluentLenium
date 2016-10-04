package org.fluentlenium.core.search;

import com.google.common.base.Supplier;
import com.google.common.collect.Collections2;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentListImpl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.filter.FilterPredicate;
import org.fluentlenium.core.proxy.LocatorProxies;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Perform FluentLenium search in given context.
 */
public class Search implements SearchControl<FluentWebElement> {
    private final SearchContext searchContext;
    private final ComponentInstantiator instantiator;

    public Search(final SearchContext context, final ComponentInstantiator instantiator) {
        this.searchContext = context;
        this.instantiator = instantiator;
    }

    /**
     * Central methods to find elements on the page. Can provide some filters. Able to use css1, css2, css3, see WebDriver
     * restrictions
     *
     * @param selector elements name to find
     * @param filters  filters set
     * @return fluent list of fluent web elements
     */
    @Override
    public FluentList<FluentWebElement> find(final String selector, final Filter... filters) {
        final StringBuilder sb = new StringBuilder(selector);
        final List<Filter> postFilterSelector = new ArrayList<>();
        if (filters != null && filters.length > 0) {
            for (final Filter filter : filters) {
                if (filter.isPreFilter()) {
                    sb.append(filter.toString());
                } else {
                    postFilterSelector.add(filter);
                }
            }
        }
        final List<WebElement> select = selectList(sb.toString());
        final FluentListImpl fluentList = instantiator.asComponentList(FluentListImpl.class, FluentWebElement.class, select);
        if (postFilterSelector.isEmpty()) {
            return fluentList;
        }

        Collection<FluentWebElement> postFiltered = fluentList;
        for (final Filter filter : postFilterSelector) {
            postFiltered = Collections2.filter(postFiltered, new FilterPredicate(filter));
        }

        final List<WebElement> postFilteredElements = LocatorProxies.createWebElementList(new Supplier<List<WebElement>>() {
            @Override
            public List<WebElement> get() {
                Collection<FluentWebElement> postFiltered = fluentList;
                for (final Filter filter : postFilterSelector) {
                    postFiltered = Collections2.filter(postFiltered, new FilterPredicate(filter));
                }

                final ArrayList<WebElement> webElements = new ArrayList<>();
                for (final FluentWebElement element : postFiltered) {
                    webElements.add(element.getElement());
                }

                return webElements;
            }
        });

        return instantiator.asComponentList(FluentListImpl.class, FluentWebElement.class, postFilteredElements);
    }

    private ElementLocator locator(final By by) {
        return new ElementLocator() {
            @Override
            public WebElement findElement() {
                return searchContext.findElement(by);
            }

            @Override
            public List<WebElement> findElements() {
                return searchContext.findElements(by);
            }

            @Override
            public String toString() {
                return by.toString();
            }
        };
    }

    private List<WebElement> selectList(final String cssSelector) {
        return selectList(By.cssSelector(cssSelector));
    }

    private List<WebElement> selectList(final By locator) {
        return LocatorProxies.createWebElementList(locator(locator));
    }

    /**
     * Central methods to find elements on the page with filters.
     *
     * @param filters filters set
     * @return fluent list of fluent web elements
     */
    @Override
    public FluentList<FluentWebElement> find(final Filter... filters) {
        if (filters == null || filters.length == 0) {
            throw new IllegalArgumentException("cssSelector or filter is required");
        }
        return find("*", filters);
    }

    /**
     * Central methods to find elements on the page. Can provide some filters. Able to use css1, css2, css3, see WebDriver
     * restrictions
     *
     * @param locator elements locator
     * @param filters filters set
     * @return fluent list of fluent web elements
     */
    @Override
    public FluentList<FluentWebElement> find(final By locator, final Filter... filters) {
        final List<WebElement> select = selectList(locator);

        final FluentListImpl fluentList = instantiator.asComponentList(FluentListImpl.class, FluentWebElement.class, select);
        if (filters.length == 0) {
            return fluentList;
        }

        final List<WebElement> postFilteredElements = LocatorProxies.createWebElementList(new Supplier<List<WebElement>>() {
            @Override
            public List<WebElement> get() {
                Collection<FluentWebElement> postFiltered = fluentList;
                for (final Filter selector : filters) {
                    postFiltered = Collections2.filter(postFiltered, new FilterPredicate(selector));
                }

                final List<WebElement> webElements = new ArrayList<>();
                for (final FluentWebElement element : postFiltered) {
                    webElements.add(element.getElement());
                }

                return webElements;
            }
        });

        return instantiator.asComponentList(FluentListImpl.class, FluentWebElement.class, postFilteredElements);
    }

    @Override
    public FluentList<FluentWebElement> $(final String selector, final Filter... filters) {
        return find(selector, filters);
    }

    @Override
    public FluentWebElement el(final String selector, final Filter... filters) {
        return find(selector, filters).first();
    }

    @Override
    public FluentList<FluentWebElement> $(final Filter... filters) {
        return find(filters);
    }

    @Override
    public FluentWebElement el(final Filter... filters) {
        return find(filters).first();
    }

    @Override
    public FluentList<FluentWebElement> $(final By locator, final Filter... filters) {
        return find(locator, filters);
    }

    @Override
    public FluentWebElement el(final By locator, final Filter... filters) {
        return find(locator, filters).first();
    }

}
