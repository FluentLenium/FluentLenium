package org.fluentlenium.core.components;

import lombok.Getter;
import lombok.experimental.Delegate;
import org.fluentlenium.core.domain.WrapsElements;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A list of component that lazy initialize from it's related list of elements.
 *
 * @param <T> type of component.
 */
public class LazyComponentList<T> implements List<T>, WrapsElements, LazyComponents<T> {
    private final ComponentInstantiator instantiator;
    private final Class<T> componentClass;

    private final List<WebElement> elements;

    private final List<LazyComponentsListener<T>> lazyComponentsListeners = new ArrayList<>();

    @Delegate
    @Getter(lazy = true)
    private final List<T> list = transformList();

    /**
     * Creates a new lazy component list.
     *
     * @param instantiator   component instantiator
     * @param componentClass component class
     * @param elements       underlying element list
     */
    public LazyComponentList(ComponentInstantiator instantiator, Class<T> componentClass, List<WebElement> elements) {
        this.componentClass = componentClass;
        this.instantiator = instantiator;
        this.elements = elements;
    }

    /**
     * Transform the actual list into components.
     *
     * @return transformed list
     */
    protected List<T> transformList() {
        List<T> components = new ArrayList<>();
        Map<WebElement, T> componentMap = new LinkedHashMap<>();
        for (WebElement element : elements) {
            T component = instantiator.newComponent(componentClass, element);
            components.add(component);
            componentMap.put(element, component);
        }
        fireLazyComponentsInitialized(componentMap);
        return components;
    }

    /**
     * First lazy components initialized event.
     *
     * @param componentMap components
     */
    protected void fireLazyComponentsInitialized(Map<WebElement, T> componentMap) {
        for (LazyComponentsListener<T> listener : lazyComponentsListeners) {
            listener.lazyComponentsInitialized(componentMap);
        }
    }

    @Override
    public boolean addLazyComponentsListener(LazyComponentsListener<T> listener) {
        return lazyComponentsListeners.add(listener);
    }

    @Override
    public boolean removeLazyComponentsListener(LazyComponentsListener<T> listener) {
        return lazyComponentsListeners.remove(listener);
    }

    @Override
    public boolean isLazy() {
        return true;
    }

    @Override
    public boolean isLazyInitialized() {
        return list == null;
    }

    @Override
    public List<WebElement> getWrappedElements() {
        return elements;
    }

    @Override
    public String toString() {
        return isLazyInitialized() ? list.toString() : elements.toString();
    }
}
