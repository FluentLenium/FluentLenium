package org.fluentlenium.core.search;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.filter.Filter;
import org.openqa.selenium.By;

public interface SearchControl<E extends FluentWebElement> {
    //CHECKSTYLE.OFF: MethodName
    /**
     * Find list of elements with CSS selector and filters.
     *
     * @param selector CSS selector
     * @param filters  set of filters
     * @return list of element
     */
    FluentList<E> find(String selector, Filter... filters);

    /**
     * Find list of elements with CSS selector and filters.
     *
     * @param selector CSS selector
     * @param filters  set of filters
     * @return list of element
     */
    FluentList<E> $(String selector, Filter... filters);

    /**
     * Find first element with CSS selector and filters.
     *
     * @param selector CSS selector
     * @param filters  set of filters
     * @return element
     */
    E el(String selector, Filter... filters);

    /**
     * Find list of elements with filters.
     *
     * @param filters set of filters in the current context
     * @return list of elements
     */
    FluentList<E> find(Filter... filters);

    /**
     * Find list of elements with filters.
     *
     * @param filters set of filters in the current context
     * @return list of elements
     */
    FluentList<E> $(Filter... filters);

    /**
     * Find first element with filters.
     *
     * @param filters set of filters in the current context
     * @return element
     */
    E el(Filter... filters);

    /**
     * Find list of elements with Selenium locator and filters.
     *
     * @param locator elements locator
     * @param filters filters set
     * @return list of elements
     */
    FluentList<E> find(By locator, Filter... filters);

    /**
     * Find list of elements with Selenium locator and filters.
     *
     * @param locator elements locator
     * @param filters filters set
     * @return list of elements
     */
    FluentList<E> $(By locator, Filter... filters);

    /**
     * Find first element with Selenium locator and filters.
     *
     * @param locator elements locator
     * @param filters filters set
     * @return element
     */
    E el(By locator, Filter... filters);
    //CHECKSTYLE.ON: MethodName
}
