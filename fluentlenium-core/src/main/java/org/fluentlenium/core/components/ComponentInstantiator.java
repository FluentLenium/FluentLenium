package org.fluentlenium.core.components;

import org.fluentlenium.core.domain.ComponentList;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Factory building new components.
 */
public interface ComponentInstantiator {
    /**
     * Create and register a new fluent element from the given element.
     *
     * @param element wrapped element
     * @return new instance of the component
     */
    FluentWebElement newFluent(WebElement element);

    /**
     * Create and register a new component from the given element.
     *
     * @param componentClass type of the component
     * @param element        wrapped element
     * @param <T>            type of the component
     * @return new instance of the component
     */
    <T> T newComponent(Class<T> componentClass, WebElement element);

    /**
     * create and register an empty fluent list.
     *
     * @return new list of fluent web element
     */
    FluentList<FluentWebElement> newFluentList();

    /**
     * create and register a new fluent list.
     *
     * @param elements list of elements
     * @return new list of fluent web element
     */
    FluentList<FluentWebElement> newFluentList(FluentWebElement... elements);

    /**
     * create and register a new fluent list.
     *
     * @param elements list of elements
     * @return new list of fluent web element
     */
    FluentList<FluentWebElement> newFluentList(List<FluentWebElement> elements);

    /**
     * create and register a new fluent list.
     *
     * @param elements list of elements
     * @return new list of fluent web element
     */
    FluentList<FluentWebElement> asFluentList(WebElement... elements);

    /**
     * create and register a new fluent list.
     *
     * @param elements list of elements
     * @return new list of fluent web element
     */
    FluentList<FluentWebElement> asFluentList(Iterable<WebElement> elements);

    /**
     * create and register a new fluent list.
     *
     * @param elements list of elements
     * @return new list of fluent web element
     */
    FluentList<FluentWebElement> asFluentList(List<WebElement> elements);

    /**
     * create and register an empty fluent list.
     *
     * @param componentClass class of the component
     * @param <T>            type of the component
     * @return new list of fluent web element
     */
    <T extends FluentWebElement> FluentList<T> newFluentList(Class<T> componentClass);

    /**
     * create and register a new fluent list.
     *
     * @param componentClass class of the component
     * @param elements       list of elements
     * @param <T>            type of the component
     * @return new list of fluent web element
     */
    <T extends FluentWebElement> FluentList<T> newFluentList(Class<T> componentClass, T... elements);

    /**
     * create and register a new fluent list.
     *
     * @param componentClass class of the component
     * @param elements       list of elements
     * @param <T>            type of the component
     * @return new list of fluent web element
     */
    <T extends FluentWebElement> FluentList<T> newFluentList(Class<T> componentClass, List<T> elements);

    /**
     * create and register a new fluent list.
     *
     * @param componentClass class of the component
     * @param elements       list of elements
     * @param <T>            type of the component
     * @return new list of fluent web element
     */
    <T extends FluentWebElement> FluentList<T> asFluentList(Class<T> componentClass, WebElement... elements);

    /**
     * create and register a new fluent list.
     *
     * @param componentClass class of the component
     * @param elements       list of elements
     * @param <T>            type of the component
     * @return new list of fluent web element
     */
    <T extends FluentWebElement> FluentList<T> asFluentList(Class<T> componentClass, Iterable<WebElement> elements);

    /**
     * create and register a new fluent list.
     *
     * @param componentClass class of the component
     * @param elements       list of elements
     * @param <T>            type of the component
     * @return new list of fluent web element
     */
    <T extends FluentWebElement> FluentList<T> asFluentList(Class<T> componentClass, List<WebElement> elements);

    /**
     * Create and register an empty list of component.
     *
     * @param componentClass type of the component
     * @param <T>            type of the component
     * @return new list of components
     */
    <T> ComponentList<T> newComponentList(Class<T> componentClass);

    /**
     * Create and register a new list of component from the given element iterable.
     *
     * @param componentClass type of the component
     * @param elements       elements
     * @param <T>            type of the component
     * @return new list of components
     */
    <T> ComponentList<T> asComponentList(Class<T> componentClass, WebElement... elements);

    /**
     * Create and register a new list of component from the given element iterable.
     *
     * @param componentClass type of the component
     * @param elements       elements
     * @param <T>            type of the component
     * @return new list of components
     */
    <T> ComponentList<T> asComponentList(Class<T> componentClass, Iterable<WebElement> elements);

    /**
     * Create and register a new list of component from the given element iterable.
     *
     * @param componentClass type of the component
     * @param elements       elements
     * @param <T>            type of the component
     * @return new list of components
     */
    <T> ComponentList<T> asComponentList(Class<T> componentClass, List<WebElement> elements);

    /**
     * Create and register a new list of component from the given component list.
     *
     * @param componentClass type of the component
     * @param componentsList components list
     * @param <T>            type of the component
     * @return new list of components
     */
    <T> ComponentList<T> newComponentList(Class<T> componentClass, T... componentsList);

    /**
     * Create and register a new list of component from the given component list.
     *
     * @param componentClass type of the component
     * @param componentsList components list
     * @param <T>            type of the component
     * @return new list of components
     */
    <T> ComponentList<T> newComponentList(Class<T> componentClass, List<T> componentsList);

    /**
     * Create and register an empty list of component.
     *
     * @param listClass      type of the list
     * @param componentClass type of the component
     * @param <L>            type of the list
     * @param <T>            type of the component
     * @return new list of components
     */
    <L extends List<T>, T> L newComponentList(Class<L> listClass, Class<T> componentClass);

    /**
     * Create and register a new list of component from the given element iterable.
     *
     * @param listClass      type of the list
     * @param componentClass type of the component
     * @param elements       elements
     * @param <L>            type of the list
     * @param <T>            type of the component
     * @return new list of components
     */
    <L extends List<T>, T> L asComponentList(Class<L> listClass, Class<T> componentClass, WebElement... elements);

    /**
     * Create and register a new list of component from the given element iterable.
     *
     * @param listClass      type of the list
     * @param componentClass type of the component
     * @param elements       elements
     * @param <L>            type of the list
     * @param <T>            type of the component
     * @return new list of components
     */
    <L extends List<T>, T> L asComponentList(Class<L> listClass, Class<T> componentClass, Iterable<WebElement> elements);

    /**
     * Create and register a new list of component from the given element iterable.
     *
     * @param listClass      type of the list
     * @param componentClass type of the component
     * @param elements       elements
     * @param <L>            type of the list
     * @param <T>            type of the component
     * @return new list of components
     */
    <L extends List<T>, T> L asComponentList(Class<L> listClass, Class<T> componentClass, List<WebElement> elements);

    /**
     * Create and register a new list of component from the given component list.
     *
     * @param listClass      type of the list
     * @param componentClass type of the component
     * @param componentsList components list
     * @param <L>            type of the list
     * @param <T>            type of the component
     * @return new list of components
     */
    <L extends List<T>, T> L newComponentList(Class<L> listClass, Class<T> componentClass, T... componentsList);

    /**
     * Create and register a new list of component from the given component list.
     *
     * @param listClass      type of the list
     * @param componentClass type of the component
     * @param componentsList components list
     * @param <L>            type of the list
     * @param <T>            type of the component
     * @return new list of components
     */
    <L extends List<T>, T> L newComponentList(Class<L> listClass, Class<T> componentClass, List<T> componentsList);

    /**
     * Check if this class is a component class.
     *
     * @param componentClass class to check
     * @return true if this class is a component class, false otherwise
     */
    boolean isComponentClass(Class<?> componentClass);

    /**
     * Check if this class is a component list class.
     *
     * @param componentListClass class to check
     * @return true if this class is a component list class, false otherwise
     */
    boolean isComponentListClass(Class<? extends List<?>> componentListClass);
}
