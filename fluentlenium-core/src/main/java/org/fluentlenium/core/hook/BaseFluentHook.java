package org.fluentlenium.core.hook;

import com.google.common.base.Supplier;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

public class BaseFluentHook<T> extends BaseHook<T> {
    private FluentWebElement fluentWebElement;

    public BaseFluentHook(final FluentControl fluentControl, final ComponentInstantiator instantiator,
            final Supplier<WebElement> elementSupplier, final Supplier<ElementLocator> locatorSupplier,
            final Supplier<String> toStringSupplier, final T options) {
        super(fluentControl, instantiator, elementSupplier, locatorSupplier, toStringSupplier, options);
    }

    public FluentWebElement getFluentWebElement() {
        final WebElement element = getElement();
        if (fluentWebElement == null || element != fluentWebElement.getElement()) {
            fluentWebElement = getInstantiator().newComponent(FluentWebElement.class, element);
        }
        return fluentWebElement;
    }
}
