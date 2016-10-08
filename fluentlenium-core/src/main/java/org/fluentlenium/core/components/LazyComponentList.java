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
    public LazyComponentList(final ComponentInstantiator instantiator, final Class<T> componentClass,
            final List<WebElement> elements) {
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
        final List<T> components = new ArrayList<>();
        final Map<WebElement, T> componentMap = new LinkedHashMap<>();
        for (final WebElement element : this.elements) {
            final T component = this.instantiator.newComponent(componentClass, element);
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
    protected void fireLazyComponentsInitialized(final Map<WebElement, T> componentMap) {
        for (final LazyComponentsListener<T> listener : lazyComponentsListeners) {
            listener.lazyComponentsInitialized(componentMap);
        }
    }

    @Override
    public boolean addLazyComponentsListener(final LazyComponentsListener<T> listener) {
        return lazyComponentsListeners.add(listener);
    }

    @Override
    public boolean removeLazyComponentsListener(final LazyComponentsListener<T> listener) {
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
        return this.elements;
    }

    @Override
    public String toString() {
        return isLazyInitialized() ? list.toString() : this.elements.toString();
    }
}
