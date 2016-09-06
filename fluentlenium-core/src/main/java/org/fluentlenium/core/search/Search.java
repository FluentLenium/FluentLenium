package org.fluentlenium.core.search;

import com.google.common.base.Supplier;
import com.google.common.collect.Collections2;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentListImpl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.filter.FilterPredicate;
import org.fluentlenium.core.hook.HookChainBuilder;
import org.fluentlenium.core.proxy.LocatorProxies;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Search implements SearchControl<FluentWebElement> {
    private final SearchContext searchContext;
    private final ComponentInstantiator instantiator;
    private final HookChainBuilder hookChainBuilder;

    public Search(SearchContext context, ComponentInstantiator instantiator, HookChainBuilder hookChainBuilder) {
        this.searchContext = context;
        this.instantiator = instantiator;
        this.hookChainBuilder = hookChainBuilder;
    }

    public ComponentInstantiator getInstantiator() {
        return instantiator;
    }

    public HookChainBuilder getHookChainBuilder() {
        return hookChainBuilder;
    }

    /**
     * Central methods to find elements on the page. Can provide some filters. Able to use css1, css2, css3, see WebDriver  restrictions
     *
     * @param selector elements name to find
     * @param filters  filters set
     * @return fluent list of fluent web elements
     */
    @Override
    public FluentList<FluentWebElement> find(String selector, final Filter... filters) {
        StringBuilder sb = new StringBuilder(selector);
        final List<Filter> postFilterSelector = new ArrayList<>();
        if (filters != null && filters.length > 0) {
            for (Filter filter : filters) {
                if (filter.isPreFilter()) {
                    sb.append(filter.toString());
                } else {
                    postFilterSelector.add(filter);
                }
            }
        }
        List<WebElement> select = selectList(sb.toString());
        final FluentListImpl fluentList = instantiator.asComponentList(FluentListImpl.class, FluentWebElement.class, select);
        if (postFilterSelector.size() == 0) {
            return fluentList;
        }

        Collection<FluentWebElement> postFiltered = fluentList;
        for (Filter filter : postFilterSelector) {
            postFiltered = Collections2.filter(postFiltered, new FilterPredicate(filter));
        }

        List<WebElement> postFilteredElements = LocatorProxies.createWebElementList(new Supplier<List<WebElement>>() {
            @Override
            public List<WebElement> get() {
                Collection<FluentWebElement> postFiltered = fluentList;
                for (Filter filter : postFilterSelector) {
                    postFiltered = Collections2.filter(postFiltered, new FilterPredicate(filter));
                }

                ArrayList<WebElement> webElements = new ArrayList<>();
                for (FluentWebElement element : postFiltered) {
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
        };
    }

    private List<WebElement> selectList(final String cssSelector) {
        return selectList(By.cssSelector(cssSelector));
    }

    private List<WebElement> selectList(By locator) {
        return LocatorProxies.createWebElementList(locator(locator));
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
        return find(selector, filters).index(number);
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
        List<WebElement> select = selectList(locator);

        final FluentListImpl fluentList = instantiator.asComponentList(FluentListImpl.class, FluentWebElement.class, select);
        if (filters.length == 0) {
            return fluentList;
        }

        List<WebElement> postFilteredElements = LocatorProxies.createWebElementList(new Supplier<List<WebElement>>() {
            @Override
            public List<WebElement> get() {
                Collection<FluentWebElement> postFiltered = fluentList;
                for (Filter selector : filters) {
                    postFiltered = Collections2.filter(postFiltered, new FilterPredicate(selector));
                }

                List<WebElement> webElements = new ArrayList<>();
                for (FluentWebElement element : postFiltered) {
                    webElements.add(element.getElement());
                }

                return webElements;
            }
        });

        return instantiator.asComponentList(FluentListImpl.class, FluentWebElement.class, postFilteredElements);
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
        return find(locator, filters).index(index);
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
        return find(selector, filters).first();
    }

    /**
     * Return the first element corresponding to the filters.
     *
     * @param filters filters set
     * @return fluent web element
     */
    @Override
    public FluentWebElement findFirst(Filter... filters) {
        return find(filters).first();
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
        return find(locator, filters).first();
    }
}
