package io.fluentlenium.examples.hooks;

import io.fluentlenium.core.FluentControl;
import io.fluentlenium.core.components.ComponentInstantiator;
import io.fluentlenium.core.hook.BaseHook;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.function.Supplier;

public class ExampleHook extends BaseHook<ExampleHookOptions> {
    public ExampleHook(FluentControl control, ComponentInstantiator instantiator, Supplier<WebElement> elementSupplier,
                       Supplier<ElementLocator> locatorSupplier, Supplier<String> toStringSupplier, ExampleHookOptions options) {
        super(control, instantiator, elementSupplier, locatorSupplier, toStringSupplier, options);
    }

    @Override
    public void submit() {
        System.out.println(getOptions().getMessage() + ": before click!");
        super.submit();
        System.out.println(getOptions().getMessage() + "ExampleHook: after click!");
    }
}
