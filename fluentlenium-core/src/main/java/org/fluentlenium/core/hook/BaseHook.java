package org.fluentlenium.core.hook;

import com.google.common.base.Supplier;
import lombok.experimental.Delegate;
import org.fluentlenium.core.DefaultFluentContainer;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

public class BaseHook<T> extends DefaultFluentContainer implements FluentHook<T> {
    private final ComponentInstantiator instantiator;

    private final Supplier<ElementLocator> locatorSupplier;

    private T options;

    private final Supplier<WebElement> elementSupplier;

    @Delegate
    public final WebElement getElement() {
        return elementSupplier.get();
    }

    @Override
    public WebElement getWrappedElement() {
        return getElement();
    }

    @Delegate
    public final ElementLocator getElementLocator() {
        return locatorSupplier.get();
    }

    public BaseHook(FluentControl fluentControl, ComponentInstantiator instantiator, Supplier<WebElement> elementSupplier, Supplier<ElementLocator> locatorSupplier, T options) {
        super(fluentControl);
        this.instantiator = instantiator;
        this.elementSupplier = elementSupplier;
        this.locatorSupplier = locatorSupplier;
        this.options = options;

        if (this.options == null) {
            this.options = newOptions();
        }
    }

    protected T newOptions() {
        return null;
    }

    public ComponentInstantiator getInstantiator() {
        return instantiator;
    }

    @Override
    public T getOptions() {
        return options;
    }
}
