package io.fluentlenium.core.hook;

import io.fluentlenium.core.FluentControl;
import io.fluentlenium.core.components.ComponentInstantiator;
import io.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.function.Supplier;

/**
 * Base hook supporting {@link FluentWebElement} API.
 * <p>
 * You should extends this class to implements your own hook.
 *
 * @param <T> object
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
    public BaseFluentHook(FluentControl control, ComponentInstantiator instantiator, Supplier<WebElement> elementSupplier,
                          Supplier<ElementLocator> locatorSupplier, Supplier<String> toStringSupplier, T options) {
        super(control, instantiator, elementSupplier, locatorSupplier, toStringSupplier, options);
    }

    /**
     * Get the underlying element as a FluentWebElement.
     *
     * @return underlying element as a FluentWebElement
     */
    public FluentWebElement getFluentWebElement() {
        WebElement element = getElement();
        if (fluentWebElement == null || element != fluentWebElement.getElement()) {
            fluentWebElement = getInstantiator().newComponent(FluentWebElement.class, element);
        }
        return fluentWebElement;
    }
}
