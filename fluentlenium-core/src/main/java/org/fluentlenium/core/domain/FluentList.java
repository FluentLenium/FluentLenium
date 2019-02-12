package org.fluentlenium.core.domain;

import org.fluentlenium.core.action.FluentActions;
import org.fluentlenium.core.conditions.FluentListConditions;
import org.fluentlenium.core.hook.HookControl;
import org.fluentlenium.core.label.FluentLabel;
import org.fluentlenium.core.proxy.FluentProxyState;
import org.fluentlenium.core.search.SearchControl;
import org.fluentlenium.core.search.SearchFilter;
import org.fluentlenium.core.wait.FluentWaitElementList;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Wraps a list of {@link FluentWebElement}. It provides an enhanced API to control list of selenium elements.
 *
 * @param <E> type of element
 */
public interface FluentList<E extends FluentWebElement>
        extends List<E>, FluentActions<FluentList<E>, E>, FluentProxyState<FluentList<E>>, SearchControl<E>,
        HookControl<FluentList<E>>, FluentLabel<FluentList<E>> {

    /**
     * Retrieve the first element.
     *
     * @return first element
     */
    E first();

    /**
     * Retrieve the first element and checks is it exactly one element in the list
     *
     * @return first element
     */
    E single();

    /**
     * Retrieve the last element.
     *
     * @return last element
     */
    E last();

    /**
     * Retrieve an element at given index.
     *
     * @param index position of the element to retrieve
     * @return element at given index
     */
    E index(int index);

    /**
     * Creates a list of Selenium {@link WebElement} from this list
     *
     * @return list of selenium elements
     */
    List<WebElement> toElements();

    /**
     * Click on all elements on the list
     * Only the clickable elements are clicked
     */
    @Override
    FluentList<E> click();

    /**
     * double click on all elements on the list
     * Only the clickable elements are clicked
     */
    @Override
    FluentList<E> doubleClick();

    /**
     * context click on all elements on the list
     * Only the clickable elements are clicked
     */
    @Override
    FluentList<E> contextClick();

    /**
     * Fill  all elements on the list with the corresponding cell in the with table.
     * Only the visible elements are filled
     * If there is more elements on the list than in the with table, the last element of the table is repeated
     */
    @Override
    FluentList<E> write(String... with);

    /**
     * submit on all elements on the list
     * Only the visible elements are submitted
     */
    @Override
    FluentList<E> submit();

    /**
     * Return the value of elements on the list
     *
     * @return list of string values
     */
    List<String> values();

    /**
     * Return the id of elements on the list
     *
     * @return list of string values
     */
    List<String> ids();

    /**
     * Return a custom attribute of elements on the list
     *
     * @param attribute attribute name
     * @return list of string valuess
     */
    List<String> attributes(String attribute);

    /**
     * Return the name of elements on the list
     *
     * @return list of string values
     */
    List<String> names();

    /**
     * Return the Dimension of elements on the list
     *
     * @return list of Dimensions
     */
    List<Dimension> dimensions();

    /**
     * Return the tag name of elements on the list
     *
     * @return list of string values
     */
    List<String> tagNames();

    /**
     * Return the texts of list elements
     *
     * @return list of string values
     */
    List<String> texts();

    /**
     * Return the text contents of list elements
     *
     * @return list of string values
     */
    List<String> textContents();

    /**
     * find elements into the children with the corresponding filters
     *
     * @param selector element name
     * @param filters  set of filters
     * @return extended by FluentWebElement objects list
     */
    @Override
    FluentList<E> find(String selector, SearchFilter... filters);

    /**
     * find elements in the children with the corresponding filters
     *
     * @param filters set of filters
     * @return extended by FluentWebElement objects list
     */
    @Override
    FluentList<E> find(SearchFilter... filters);

    /**
     * Count elements without actually loading the lazy list.
     * <p>
     * This method ignore defined hooks.
     *
     * @return elements count
     */
    int count();

    /**
     * Get FluentList size
     */
    @Override
    int size();

    /**
     * Clear all elements on the list
     * <p>
     * Only the visible elements are cleared.
     *
     * @return extended by FluentWebElement object
     */
    FluentList<E> clearAll();

    /**
     * Clear all React elements on the list
     * <p>
     * Only the visible elements are cleared.
     *
     * @return extended by FluentWebElement object
     */
    FluentList<E> clearAllReactInputs();

    /**
     * Clear visible elements on the list
     */
    @Override
    void clear();

    /**
     * Calls {@link List#clear()} from underlying List implementation.
     *
     * @see List#clear()
     */
    void clearList();

    /**
     * Wrap all underlying elements in a componen..
     *
     * @param componentClass component class
     * @param <T>            type of component
     * @return fluent list of elements as components.
     */
    <T extends FluentWebElement> FluentList<T> as(Class<T> componentClass);

    /**
     * Build a condition object on this element list that will match if each underlying element match.
     *
     * @return a condition object
     */
    FluentListConditions each();

    /**
     * Build a condition object on this element list that will match if one or more underlying element match.
     *
     * @return a condition object
     */
    FluentListConditions one();

    /**
     * Build a wait object to wait for a condition of this element list.
     *
     * @return a wait object
     */
    FluentWaitElementList await();

    /**
     * Build a condition object on this element list that will match if each underlying element match,
     * automatically waiting for condition to be verified.
     *
     * @return a condition object
     */
    FluentListConditions awaitUntilEach();

    /**
     * Build a condition object on this element list that will match if one or more underlying element match,
     * automatically waiting for condition to be verified.
     *
     * @return a condition object
     */
    FluentListConditions awaitUntilOne();
}
