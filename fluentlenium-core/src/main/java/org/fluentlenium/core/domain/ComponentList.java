package org.fluentlenium.core.domain;

import lombok.experimental.Delegate;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.components.LazyComponents;
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

    @Delegate
    private LazyComponents lazyComponents = new NotLazyComponents(); // NOPMD UnusedPrivateField

    /**
     * Creates a new list of components
     *
     * @param componentClass component class
     * @param list           underlying list of components
     * @param control        control interface
     * @param instantiator   component instantiator
     */
    public ComponentList(final Class<T> componentClass, final List<T> list, final FluentControl control,
            final ComponentInstantiator instantiator) {
        super(list);
        if (list instanceof LazyComponents) {
            lazyComponents = (LazyComponents) list;
        }
        this.componentClass = componentClass;
        this.control = control;
        this.instantiator = instantiator;

        if (this.list instanceof WrapsElements) {
            this.proxy = ((WrapsElements) this.list).getWrappedElements(); // NOPMD ConstructorCallsOverridableMethod
        }
    }

    @Override
    public List<WebElement> getWrappedElements() {
        return this.proxy;
    }
}
