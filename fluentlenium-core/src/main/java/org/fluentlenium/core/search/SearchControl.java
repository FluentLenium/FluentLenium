package org.fluentlenium.core.search;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.By;

/**
 * Control interface to search for elements.
 *
 * @param <E> fluent web element type
 */
public interface SearchControl<E extends FluentWebElement> {
    //CHECKSTYLE.OFF: MethodName

    /**
     * Find list of elements with CSS selector and filters.
     *
     * @param selector CSS selector
     * @param filters  set of filters
     * @return list of element
     */
    FluentList<E> find(String selector, SearchFilter... filters);

    /**
     * Find list of elements with CSS selector and filters.
     *
     * @param selector CSS selector
     * @param filters  set of filters
     * @return list of element
     */
    FluentList<E> $(String selector, SearchFilter... filters);

    /**
     * Find first element with CSS selector and filters.
     *
     * @param selector CSS selector
     * @param filters  set of filters
     * @return element
     */
    E el(String selector, SearchFilter... filters);

    /**
     * Find list of elements with filters.
     *
     * @param filters set of filters in the current context
     * @return list of elements
     */
    FluentList<E> find(SearchFilter... filters);

    /**
     * Find list of elements with filters.
     *
     * @param filters set of filters in the current context
     * @return list of elements
     */
    FluentList<E> $(SearchFilter... filters);

    /**
     * Find first element with filters.
     *
     * @param filters set of filters in the current context
     * @return element
     */
    E el(SearchFilter... filters);

    /**
     * Find list of elements with Selenium locator and filters.
     *
     * @param locator elements locator
     * @param filters filters set
     * @return list of elements
     */
    FluentList<E> find(By locator, SearchFilter... filters);

    /**
     * Find list of elements with Selenium locator and filters.
     *
     * @param locator elements locator
     * @param filters filters set
     * @return list of elements
     */
    FluentList<E> $(By locator, SearchFilter... filters);

    /**
     * Find first element with Selenium locator and filters.
     *
     * @param locator elements locator
     * @param filters filters set
     * @return element
     */
    E el(By locator, SearchFilter... filters);
    //CHECKSTYLE.ON: MethodName
}
