package org.fluentlenium.core.hook;

import com.google.common.base.Supplier;
import lombok.experimental.Delegate;
import org.fluentlenium.core.DefaultFluentContainer;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

public class BaseHook<T> extends DefaultFluentContainer implements FluentHook<T> {
    private final ComponentInstantiator instantiator;

    private final WebDriver webDriver;

    private final Supplier<ElementLocator> locatorSupplier;

    private T options;

    private final Supplier<WebElement> elementSupplier;

    @Delegate
    public final WebElement getElement() {
        return elementSupplier.get();
    }

    @Delegate
    public final ElementLocator getElementLocator() {
        return locatorSupplier.get();
    }

    public BaseHook(WebDriver webDriver, ComponentInstantiator instantiator, Supplier<WebElement> elementSupplier, Supplier<ElementLocator> locatorSupplier, T options) {
        this.webDriver = webDriver;
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

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public ComponentInstantiator getInstantiator() {
        return instantiator;
    }

    @Override
    public T getOptions() {
        return options;
    }
}
