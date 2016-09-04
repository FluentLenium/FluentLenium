package org.fluentlenium.core.domain;

import lombok.experimental.Delegate;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ComponentList<T> implements List<T>, WrapsElements {
    protected final Class<T> componentClass;

    @Delegate
    protected final List<T> list;
    protected final ComponentInstantiator instantiator;
    protected final FluentControl fluentControl;
    protected List<WebElement> proxy;

    public ComponentList(Class<T> componentClass, List<T> list, FluentControl fluentControl, ComponentInstantiator instantiator) {
        super();
        this.componentClass = componentClass;
        this.list = list;
        this.fluentControl = fluentControl;
        this.instantiator = instantiator;

        if (this.list instanceof WrapsElements) {
            setProxy(((WrapsElements) this.list).getWrappedElements());
        }
    }

    public void setProxy(List<WebElement> proxy) {
        this.proxy = proxy;
    }

    @Override
    public List<WebElement> getWrappedElements() {
        return this.proxy;
    }
}
