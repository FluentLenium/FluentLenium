package org.fluentlenium.core.domain;

import org.fluentlenium.core.action.FluentDefaultActions;
import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.search.SearchActions;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Constructor;
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
     * Return the text of the first element on the list
     *
     * @return text of the first element on the list
     */
    String getText();

    /**
     * find elements into the children with the corresponding filters
     *
     * @param name    element name
     * @param filters set of filters
     * @return extended by FluentWebElement objects list
     */
    @Override
    FluentList<E> find(String name, Filter... filters);

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
     * @param name    element name
     * @param number  set of filters
     * @param filters set of filters
     * @return extended by FluentWebElement object
     */
    @Override
    E find(String name, Integer number, Filter... filters);

    /**
     * find element in the children with the corresponding filters at the position indicated by the number
     *
     * @param number  element name
     * @param filters set of filters
     * @return extended by FluentWebElement object
     */
    @Override
    E find(Integer number, Filter... filters);

    /**
     * find elements into the children with the corresponding filters at the first position
     *
     * @param name    element name
     * @param filters set of filters
     * @return extended by FluentWebElement object
     */
    @Override
    E findFirst(String name, Filter... filters);

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
     * Only the visible elements are filled
     *
     * @return extended by FluentWebElement object
     */
    FluentList<E> clearAll();

    /**
     * Wrap all underlying elements in a componen..
     *
     * @param componentClass component class
     * @param <T>            type of component
     * @return fluent list of elements as components.
     */
    public <T extends FluentWebElement> FluentList<T> as(Class<T> componentClass);

    /**
     * Clear all elements on the list
     * Only the visible elements are filled
     */
    void clear();
}
