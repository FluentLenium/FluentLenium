package io.fluentlenium.core.components;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import io.fluentlenium.core.domain.ListImpl;
import io.fluentlenium.core.domain.WrapsElements;
import org.openqa.selenium.WebElement;

/**
 * A list of component that lazy initialize from it's related list of elements.
 *
 * @param <T> type of component.
 */
public class LazyComponentList<T> extends ListImpl<T> implements List<T>, WrapsElements, LazyComponents<T> {
    private final ComponentInstantiator instantiator;
    private final Class<T> componentClass;

    private final List<WebElement> elements;

    private final List<LazyComponentsListener<T>> lazyComponentsListeners = new ArrayList<>();

    private final AtomicReference<Object> list = new AtomicReference<>();

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

    public List<T> getList() {
        Object value = this.list.get();
        if (value == null) {
            synchronized (this.list) {
                value = this.list.get();
                if (value == null) {
                    final List<T> actualValue = transformList();
                    value = actualValue == null ? this.list : actualValue;
                    this.list.set(value);
                }
            }
        }
        return (List<T>) (value == this.list ? null : value);
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
        return isLazyInitialized() ? getList().toString() : elements.toString();
    }

}
