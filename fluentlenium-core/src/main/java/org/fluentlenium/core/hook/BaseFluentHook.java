package org.fluentlenium.core.hook;

import com.google.common.base.Supplier;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

/**
 * Base hook supporting {@link FluentWebElement} API.
 * <p>
 * You should extends this class to implements your own hook.
 *
 * @param <T>
 * @see #getFluentWebElement()
 */
public class BaseFluentHook<T> extends BaseHook<T> {
    private FluentWebElement fluentWebElement;

    /**
     * create a new base fluent hook.
     *
     * @param control          control interface
     * @param instantiator     component instantiator
     * @param elementSupplier  element supplier
     * @param locatorSupplier  element locator supplier
     * @param toStringSupplier element toString supplier
     * @param options          hook options
     */
    public BaseFluentHook(final FluentControl control, final ComponentInstantiator instantiator,
            final Supplier<WebElement> elementSupplier, final Supplier<ElementLocator> locatorSupplier,
            final Supplier<String> toStringSupplier, final T options) {
        super(control, instantiator, elementSupplier, locatorSupplier, toStringSupplier, options);
    }

    /**
     * Get the underlying element as a FluentWebElement.
     *
     * @return underlying element as a FluentWebElement
     */
    public FluentWebElement getFluentWebElement() {
        final WebElement element = getElement();
        if (fluentWebElement == null || element != fluentWebElement.getElement()) {
            fluentWebElement = getInstantiator().newComponent(FluentWebElement.class, element);
        }
        return fluentWebElement;
    }
}
