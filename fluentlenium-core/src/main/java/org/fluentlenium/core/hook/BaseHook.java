package org.fluentlenium.core.hook;

import com.google.common.base.Supplier;
import lombok.experimental.Delegate;
import org.fluentlenium.core.DefaultFluentContainer;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

/**
 * Base hook implementation.
 * <p>
 * You should extends this class to implement your own hook.
 *
 * @param <T> type of options for the hook
 */
public class BaseHook<T> extends DefaultFluentContainer implements FluentHook<T> {
    private final ComponentInstantiator instantiator;

    private final Supplier<ElementLocator> locatorSupplier;

    private final Supplier<String> toStringSupplier;

    private T options;

    private final Supplier<WebElement> elementSupplier;

    /**
     * Get the underlying element of the hook.
     * <p>
     * Can be another hook, or a real element.
     *
     * @return underlying element
     */
    @Delegate
    public final WebElement getElement() {
        return elementSupplier.get();
    }

    @Override
    public WebElement getWrappedElement() {
        return getElement();
    }

    /**
     * Get the underlying element locator of the hook.
     *
     * @return underlying element locator
     */
    @Delegate
    public final ElementLocator getElementLocator() {
        return locatorSupplier.get();
    }

    /**
     * Creates a new base hook.
     *
     * @param control          control interface
     * @param instantiator     component instantiator
     * @param elementSupplier  element supplier
     * @param locatorSupplier  element locator supplier
     * @param toStringSupplier element toString supplier
     * @param options          hook options
     */
    public BaseHook(FluentControl control, ComponentInstantiator instantiator,
            Supplier<WebElement> elementSupplier, Supplier<ElementLocator> locatorSupplier,
            Supplier<String> toStringSupplier, T options) {
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

    /**
     * Builds default options.
     *
     * @return default options
     */
    protected T newOptions() {
        return null;
    }

    /**
     * Get the component instantiator.
     *
     * @return component instantiator
     */
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
