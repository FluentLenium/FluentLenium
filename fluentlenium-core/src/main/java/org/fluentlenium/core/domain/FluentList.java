package org.fluentlenium.core.domain;

import org.fluentlenium.core.action.FluentDefaultActions;
import org.fluentlenium.core.conditions.FluentListConditions;
import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.search.SearchActions;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 *
 */
public interface FluentList<E extends FluentWebElement> extends List<E>, FluentDefaultActions<FluentList>, SearchActions {

    /**
     * Return the first element of the list
     * If none, return NoSuchElementException
     *
     * @return FluentWebElement based element
     * @throws org.openqa.selenium.NoSuchElementException when element not found
     */
    E first();

    /**
     * Return the last element of the list.
     * If none, return NoSuchElementException
     *
     * @return last element
     * @throws org.openqa.selenium.NoSuchElementException when element not found
     */
    E last();

    /**
     * Creates a list of Selenium {@link WebElement} from this list
     *
     * @return list of selenium elements
     */
    List<WebElement> toElements();

    /**
     * Click on all elements on the list
     * Only the visible elements are filled
     */
    @Override
    FluentList click();

    /**
     * Fill  all elements on the list with the corresponding cell in the with table.
     * Only the visible elements are filled
     * If there is more elements on the list than in the with table, the last element of the table is repeated
     */
    @Override
    FluentList text(String... with);

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
    List<String> getValues();

    /**
     * Return the id of elements on the list
     *
     * @return list of string values
     */
    List<String> getIds();

    /**
     * Return a custom attribute of elements on the list
     *
     * @param attribute attribute name
     * @return list of string values
     */
    List<String> getAttributes(String attribute);

    /**
     * Return the name of elements on the list
     *
     * @return list of string values
     */
    List<String> getNames();

    /**
     * Return the tag name of elements on the list
     *
     * @return list of string values
     */
    List<String> getTagNames();

    /**
     * Return the texts of list elements
     *
     * @return list of string values
     */
    List<String> getTexts();

    /**
     * Return the text contents of list elements
     *
     * @return list of string values
     */
    List<String> getTextContents();

    /**
     * Return the value of the first element in the list
     *
     * @return string value
     */
    String getValue();

    /**
     * Return the id of the first element on the list
     *
     * @return id of first element as string
     */
    String getId();

    /**
     * Return a custom attribute of the first element on the list
     *
     * @param attribute attribute name
     * @return custom attribute name for the first element
     */
    String getAttribute(String attribute);

    /**
     * Return the name of the first element on the list
     *
     * @return name of the first element
     */
    String getName();

    /**
     * Return the tag name of the first element on the list
     *
     * @return tag name of the first element
     */
    String getTagName();

    /**
     * Return the text of the first element on the list
     *
     * @return text of the first element on the list
     */
    String getText();


    /**
     * Return the text content of the first element on the list
     *
     * @return text content of the first element on the list
     */
    String getTextContent();

    /**
     * find elements into the children with the corresponding filters
     *
     * @param selector element name
     * @param filters  set of filters
     * @return extended by FluentWebElement objects list
     */
    @Override
    FluentList<E> find(String selector, Filter... filters);

    /**
     * find elements in the children with the corresponding filters
     *
     * @param filters set of filters
     * @return extended by FluentWebElement objects list
     */
    @Override
    FluentList<E> find(Filter... filters);

    /**
     * find elements into the children with the corresponding filters at the position indicated by the number
     *
     * @param selector element name
     * @param number   set of filters
     * @param filters  set of filters
     * @return extended by FluentWebElement object
     */
    @Override
    E find(String selector, Integer number, Filter... filters);

    /**
     * find element in the children with the corresponding filters at the position indicated by the number
     *
     * @param index   element name
     * @param filters set of filters
     * @return extended by FluentWebElement object
     */
    @Override
    E find(Integer index, Filter... filters);

    /**
     * find elements into the children with the corresponding filters at the first position
     *
     * @param selector element name
     * @param filters  set of filters
     * @return extended by FluentWebElement object
     */
    @Override
    E findFirst(String selector, Filter... filters);

    /**
     * find element in the children with the corresponding filters at the first position
     *
     * @param filters set of filters
     * @return extended by FluentWebElement object
     */
    @Override
    E findFirst(Filter... filters);

    /**
     * Clear all elements on the list
     * <p>
     * Only the visible elements are cleared.
     *
     * @return extended by FluentWebElement object
     */
    FluentList<E> clearAll();

    /**
     * Clear all elements on the list
     * <p>
     * Only the visible elements are cleared.
     *
     * @return extended by FluentWebElement object
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
     * Get a condition object on this element list that will match if each underlying element match.
     *
     * @return
     */
    FluentListConditions each();

    /**
     * Get a condition object on this element list that will match if one or more underlying element match.
     *
     * @return
     */
    FluentListConditions one();
}
