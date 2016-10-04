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

    private final Supplier<String> toStringSupplier;

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

    public BaseHook(final FluentControl control, final ComponentInstantiator instantiator,
            final Supplier<WebElement> elementSupplier, final Supplier<ElementLocator> locatorSupplier,
            final Supplier<String> toStringSupplier, final T options) {
        super(control);
        this.instantiator = instantiator;
        this.elementSupplier = elementSupplier;
        this.locatorSupplier = locatorSupplier;
        this.toStringSupplier = toStringSupplier;
        this.options = options;

        if (this.options == null) {
            this.options = newOptions(); // NOPMD ConstructorCallsOverridableMethod
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

    @Override
    public String toString() {
        return toStringSupplier.get();
    }
}
