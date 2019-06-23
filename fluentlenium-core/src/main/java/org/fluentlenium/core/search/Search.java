package org.fluentlenium.core.search;

import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.inject.FluentInjectControl;
import org.fluentlenium.core.proxy.LocatorProxies;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.Collections.singletonList;

/**
 * Perform element searchs in a given context.
 */
public class Search implements SearchControl<FluentWebElement> {
    private final SearchContext searchContext;
    private final Object container;
    private final ComponentInstantiator instantiator;
    private final FluentInjectControl injectControl;

    /**
     * Creates a new search object.
     *
     * @param context       search context
     * @param container     container
     * @param instantiator  component instantiator
     * @param injectControl inject control
     */
    public Search(SearchContext context, Object container, ComponentInstantiator instantiator,
            FluentInjectControl injectControl) {
        searchContext = context;
        this.container = container;
        this.instantiator = instantiator;
        this.injectControl = injectControl;
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
    public FluentList<FluentWebElement> find(String selector, SearchFilter... filters) {
        StringBuilder stringBuilder = new StringBuilder(selector);
        List<SearchFilter> postFilterSelector = new ArrayList<>();
        if (filters != null && filters.length > 0) {
            for (SearchFilter filter : filters) {
                if (filter.isCssFilterSupported()) {
                    stringBuilder.append(filter.getCssFilter());
                } else {
                    postFilterSelector.add(filter);
                }
            }
        }
        List<WebElement> select = selectList(stringBuilder.toString());
        FluentList<FluentWebElement> fluentList = instantiator.asFluentList(select);
        injectControl.injectComponent(fluentList, container, searchContext);
        if (postFilterSelector.isEmpty()) {
            return fluentList;
        }

        List<WebElement> postFilteredElements = LocatorProxies
                .createWebElementList(new AbstractSearchSupplier(postFilterSelector, select) {
                    @Override
                    public List<WebElement> get() {
                        Collection<FluentWebElement> postFiltered = fluentList;
                        for (SearchFilter filter : postFilterSelector) {
                            postFiltered = filter.applyFilter(postFiltered);
                        }

                        ArrayList<WebElement> webElements = new ArrayList<>();
                        for (FluentWebElement element : postFiltered) {
                            webElements.add(element.getElement());
                        }

                        return webElements;
                    }
                });

        FluentList<FluentWebElement> postFilteredList = instantiator.asFluentList(postFilteredElements);
        injectControl.injectComponent(postFilteredList, container, searchContext);
        return postFilteredList;
    }

    private ElementLocator locator(By by) {
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

    private List<WebElement> selectList(String cssSelector) {
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
    public FluentList<FluentWebElement> find(SearchFilter... filters) {
        if (filters == null || filters.length == 0) {
            throw new IllegalArgumentException("cssSelector or filter is required");
        }
        return find("*", filters);
    }

    @Override
    public FluentList<FluentWebElement> find(By locator, SearchFilter... filters) {
        List<WebElement> select = selectList(locator);

        FluentList<FluentWebElement> fluentList = instantiator.asFluentList(select);
        if (filters.length == 0) {
            return fluentList;
        }

        List<WebElement> postFilteredElements = LocatorProxies
                .createWebElementList(new AbstractSearchSupplier(Arrays.asList(filters), select) {
                    @Override
                    public List<WebElement> get() {
                        Collection<FluentWebElement> postFiltered = fluentList;
                        for (SearchFilter filter : filters) {
                            postFiltered = filter.applyFilter(postFiltered);
                        }

                        List<WebElement> webElements = new ArrayList<>();
                        for (FluentWebElement element : postFiltered) {
                            webElements.add(element.getElement());
                        }

                        return webElements;
                    }
                });

        return instantiator.asFluentList(postFilteredElements);
    }

    @Override
    public FluentList<FluentWebElement> find(List<WebElement> rawElements) {
        FluentList<FluentWebElement> postFilteredList = instantiator.asFluentList(rawElements);
        injectControl.injectComponent(postFilteredList, container, searchContext);
        return postFilteredList;
    }

    @Override
    public FluentWebElement el(WebElement rawElement) {
        return find(singletonList(rawElement)).first();
    }
}
