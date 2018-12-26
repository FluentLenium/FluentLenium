package org.fluentlenium.core.domain;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.components.LazyComponents;
import org.fluentlenium.core.components.LazyComponentsListener;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * List of Component.
 *
 * @param <T> type of component
 * @see Component
 */
public class ComponentList<T> extends DelegatingList<T> implements WrapsElements, LazyComponents {
    protected final Class<T> componentClass;

    protected final ComponentInstantiator instantiator;
    protected final FluentControl control;
    protected List<WebElement> proxy;

    private LazyComponents lazyComponents = new NotLazyComponents(); // NOPMD UnusedPrivateField

    public LazyComponents getLazyComponents() {
        return lazyComponents;
    }

    /**
     * Creates a new list of components
     *
     * @param componentClass component class
     * @param list           underlying list of components
     * @param control        control interface
     * @param instantiator   component instantiator
     */
    public ComponentList(Class<T> componentClass, List<T> list, FluentControl control,
            ComponentInstantiator instantiator) {
        super(list);
        if (list instanceof LazyComponents) {
            lazyComponents = (LazyComponents) list;
        }
        this.componentClass = componentClass;
        this.control = control;
        this.instantiator = instantiator;

        if (this.list instanceof WrapsElements) {
            proxy = ((WrapsElements) this.list).getWrappedElements(); // NOPMD ConstructorCallsOverridableMethod
        }
    }

    @Override
    public List<WebElement> getWrappedElements() {
        return proxy;
    }

    public boolean isLazy() {
        return getLazyComponents().isLazy();
    }

    public boolean addLazyComponentsListener(LazyComponentsListener listener) {
        return getLazyComponents().addLazyComponentsListener(listener);
    }

    public boolean isLazyInitialized() {
        return getLazyComponents().isLazyInitialized();
    }

    public boolean removeLazyComponentsListener(LazyComponentsListener listener) {
        return getLazyComponents().removeLazyComponentsListener(listener);
    }
}
