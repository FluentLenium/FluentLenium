package org.fluentlenium.core.search;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.filter.Filter;
import org.openqa.selenium.By;

public interface SearchControl<E extends FluentWebElement> {
    /**
     * Find list of elements with CSS selector and filters.
     *
     * @param selector CSS selector
     * @param filters  set of filters
     * @return list of element
     */
    FluentList<E> find(String selector, Filter... filters);

    /**
     * @see #find(String, Filter...)
     */
    FluentList<E> $(String selector, Filter... filters);

    /**
     * Find list of elements with filters.
     *
     * @param filters set of filters in the current context
     * @return list of elements
     */
    FluentList<E> find(Filter... filters);

    /**
     * @see #find(Filter...)
     */
    FluentList<E> $(Filter... filters);

    /**
     * Find list of elements with Selenium locator and filters.
     *
     * @param locator elements locator
     * @param filters filters set
     * @return list of elements
     */
    FluentList<E> find(By locator, final Filter... filters);

    /**
     * @see #find(By, Filter...)
     */
    FluentList<E> $(By locator, final Filter... filters);

    /**
     * Find an element with CSS selector, position in the list and filters.
     *
     * @param selector CSS selector
     * @param index    index of the desired element
     * @param filters  set of filters in the current context
     * @return element
     * @deprecated use {@link #find(String, Filter...)} followed by {@link FluentList#index(int)} instead.
     */
    @Deprecated
    E find(String selector, Integer index, Filter... filters);

    /**
     * @see #find(String, Integer, Filter...)
     * @deprecated use {@link #find(String, Filter...)} followed by {@link FluentList#index(int)} instead.
     */
    @Deprecated
    E $(String selector, Integer index, Filter... filters);

    /**
     * Find an element with position in the list and filters.
     *
     * @param index   index of element from obtained list
     * @param filters set of filters in the current context
     * @return element
     * @deprecated use {@link #find(Filter...)} followed by {@link FluentList#index(int)} instead.
     */
    @Deprecated
    E find(Integer index, Filter... filters);

    /**
     * Find an element with position in the list and filters.
     *
     * @param index   index of element from obtained list
     * @param filters set of filters in the current context
     * @return element
     * @deprecated use {@link #find(Filter...)} followed by {@link FluentList#index(int)} instead.
     */
    @Deprecated
    E $(Integer index, Filter... filters);


    /**
     * Find an element with Selenium locator, position in the list and filters.
     *
     * @param locator elements locator
     * @param index   index of element in the list
     * @param filters filters set
     * @return element
     * @deprecated use {@link #find(By, Filter...)} followed by {@link FluentList#index(int)} instead.
     */
    @Deprecated
    E find(By locator, Integer index, final Filter... filters);


    /**
     * @see #find(By, Integer, Filter...)
     *
     * @deprecated use {@link #find(By, Filter...)} followed by {@link FluentList#index(int)} instead.
     */
    @Deprecated
    E $(By locator, Integer index, final Filter... filters);

    /**
     * Find an element with CSS selector and filters.
     *
     * @param selector
     * @param filters
     * @return
     *  @deprecated use {@link #find(String, Filter...)} followed by {@link FluentList#first()} instead.
     */
    @Deprecated
    E findFirst(String selector, Filter... filters);

    /**
     * Find an element with filters.
     *
     * @param filters
     * @return
     * @deprecated use {@link #find( Filter...)} followed by {@link FluentList#first()} instead.
     */
    @Deprecated
    E findFirst(Filter... filters);

    /**
     * Find an element with Selenium locator and filter.
     *
     * @param locator
     * @param filters
     * @return
     * @deprecated use {@link #find(By, Filter...)} followed by {@link FluentList#first()} instead.
     */
    @Deprecated
    E findFirst(By locator, final Filter... filters);
}
