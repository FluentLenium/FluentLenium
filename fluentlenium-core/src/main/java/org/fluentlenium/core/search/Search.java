package org.fluentlenium.core.search;

import com.google.common.base.Supplier;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentListImpl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.proxy.LocatorProxies;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Perform element searchs in a given context.
 */
public class Search implements SearchControl<FluentWebElement> {
    private final SearchContext searchContext;
    private final ComponentInstantiator instantiator;

    /**
     * Creates a new search object.
     *
     * @param context      search context
     * @param instantiator component instantiator
     */
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
    public FluentList<FluentWebElement> find(final String selector, final SearchFilter... filters) {
        final StringBuilder stringBuilder = new StringBuilder(selector);
        final List<SearchFilter> postFilterSelector = new ArrayList<>();
        if (filters != null && filters.length > 0) {
            for (final SearchFilter filter : filters) {
                if (filter.isCssFilterSupported()) {
                    stringBuilder.append(filter.getCssFilter());
                } else {
                    postFilterSelector.add(filter);
                }
            }
        }
        final List<WebElement> select = selectList(stringBuilder.toString());
        final FluentListImpl fluentList = instantiator.asComponentList(FluentListImpl.class, FluentWebElement.class, select);
        if (postFilterSelector.isEmpty()) {
            return fluentList;
        }

        final List<WebElement> postFilteredElements = LocatorProxies.createWebElementList(new Supplier<List<WebElement>>() {
            @Override
            public List<WebElement> get() {
                Collection<FluentWebElement> postFiltered = fluentList;
                for (final SearchFilter filter : postFilterSelector) {
                    postFiltered = filter.applyFilter(postFiltered);
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
    public FluentList<FluentWebElement> find(final SearchFilter... filters) {
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
    public FluentList<FluentWebElement> find(final By locator, final SearchFilter... filters) {
        final List<WebElement> select = selectList(locator);

        final FluentListImpl fluentList = instantiator.asComponentList(FluentListImpl.class, FluentWebElement.class, select);
        if (filters.length == 0) {
            return fluentList;
        }

        final List<WebElement> postFilteredElements = LocatorProxies.createWebElementList(new Supplier<List<WebElement>>() {
            @Override
            public List<WebElement> get() {
                Collection<FluentWebElement> postFiltered = fluentList;
                for (final SearchFilter filter : filters) {
                    postFiltered = filter.applyFilter(postFiltered);
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
    public FluentList<FluentWebElement> $(final String selector, final SearchFilter... filters) {
        return find(selector, filters);
    }

    @Override
    public FluentWebElement el(final String selector, final SearchFilter... filters) {
        return find(selector, filters).first();
    }

    @Override
    public FluentList<FluentWebElement> $(final SearchFilter... filters) {
        return find(filters);
    }

    @Override
    public FluentWebElement el(final SearchFilter... filters) {
        return find(filters).first();
    }

    @Override
    public FluentList<FluentWebElement> $(final By locator, final SearchFilter... filters) {
        return find(locator, filters);
    }

    @Override
    public FluentWebElement el(final By locator, final SearchFilter... filters) {
        return find(locator, filters).first();
    }

}
