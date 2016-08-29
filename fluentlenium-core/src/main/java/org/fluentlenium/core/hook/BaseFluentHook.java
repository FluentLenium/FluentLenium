package org.fluentlenium.core.hook;

import com.google.common.base.Supplier;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

public class BaseFluentHook<T> extends BaseHook<T> {
    private FluentWebElement fluentWebElement;

    public BaseFluentHook(FluentControl fluentControl, ComponentInstantiator instantiator, Supplier<WebElement> elementSupplier, Supplier<ElementLocator> locatorSupplier, T options) {
        super(fluentControl, instantiator, elementSupplier, locatorSupplier, options);
    }

    public FluentWebElement getFluentWebElement() {
        WebElement element = getElement();
        if (fluentWebElement == null || element != fluentWebElement.getElement()) {
            fluentWebElement = getInstantiator().newComponent(FluentWebElement.class, element);
        }
        return fluentWebElement;
    }
}
