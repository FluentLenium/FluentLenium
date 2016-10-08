package org.fluentlenium.core.domain;

import lombok.experimental.Delegate;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.components.LazyComponents;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ComponentList<T> extends DelegatingList<T> implements WrapsElements, LazyComponents {
    protected final Class<T> componentClass;

    protected final ComponentInstantiator instantiator;
    protected final FluentControl fluentControl;
    protected List<WebElement> proxy;

    @Delegate
    private LazyComponents lazyComponents = new NotLazyComponents(); // NOPMD UnusedPrivateField

    public ComponentList(final Class<T> componentClass, final List<T> list, final FluentControl fluentControl,
            final ComponentInstantiator instantiator) {
        super(list);
        if (list instanceof LazyComponents) {
            lazyComponents = (LazyComponents) list;
        }
        this.componentClass = componentClass;
        this.fluentControl = fluentControl;
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
