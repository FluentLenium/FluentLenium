package org.fluentlenium.core.search;

import static java.util.stream.Collectors.toList;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Control interface to search for elements.
 *
 * @param <E> fluent web element type
 */
public interface SearchControl<E extends FluentWebElement> {
    //CHECKSTYLE.OFF: MethodName

    /**
     * Wrap raw selenium elements into a list of elements.
     * <p>
     * Synonym for {@link #$(List)}.
     *
     * @param rawElements raw selenium elements
     * @return list of element
     */
    FluentList<E> find(List<WebElement> rawElements);

    /**
     * Wrap raw selenium elements into a list of elements.
     *
     * @param rawElements raw selenium elements
     * @return list of element
     */
    default FluentList<E> $(List<WebElement> rawElements) {
        return find(rawElements);
    }

    /**
     * Wrap the underlying raw selenium elements of {@link FluentWebElement}s into a list of elements.
     * <p>
     * Note: the method name is {@code $$} to avoid signature collision with {@link #$(List)}.
     *
     * @param fluentWebElements fluent web elements
     * @return list of element
     */
    default <W extends FluentWebElement> FluentList<E> $$(List<W> fluentWebElements) {
        return find(fluentWebElements.stream().map(FluentWebElement::getWrappedElement).collect(toList()));
    }

    /**
     * Find list of elements with CSS selector and filters.
     * <p>
     * Synonym for {@link #$(String, SearchFilter...)}.
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
    default FluentList<E> $(String selector, SearchFilter... filters) {
        return find(selector, filters);
    }

    /**
     * Wrap existing raw selenium element into an element.
     *
     * @param rawElement raw selenium element
     * @return element
     */
    E el(WebElement rawElement);

    /**
     * Wrap the underlying raw selenium element of an existing {@link FluentWebElement} into an element.
     *
     * @param fluentWebElement a fluent web element
     * @return element
     */
    default <W extends FluentWebElement> E el(W fluentWebElement) {
        return el(fluentWebElement.getWrappedElement());
    }

    /**
     * Find first element with CSS selector and filters.
     *
     * @param selector CSS selector
     * @param filters  set of filters
     * @return element
     */
    default E el(String selector, SearchFilter... filters) {
        return find(selector, filters).first();
    }

    /**
     * Find list of elements with filters.
     * <p>
     * Synonym for {@link #$(SearchFilter...)}.
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
    default FluentList<E> $(SearchFilter... filters) {
        return find(filters);
    }

    /**
     * Find first element with filters.
     *
     * @param filters set of filters in the current context
     * @return element
     */
    default E el(SearchFilter... filters) {
        return find(filters).first();
    }

    /**
     * Find list of elements with Selenium locator and filters.
     * <p>
     * Synonym for {@link #$(By, SearchFilter...)}.
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
    default FluentList<E> $(By locator, SearchFilter... filters) {
        return find(locator, filters);
    }

    /**
     * Find first element with Selenium locator and filters.
     *
     * @param locator elements locator
     * @param filters filters set
     * @return element
     */
    default E el(By locator, SearchFilter... filters) {
        return find(locator, filters).first();
    }
    //CHECKSTYLE.ON: MethodName
}
