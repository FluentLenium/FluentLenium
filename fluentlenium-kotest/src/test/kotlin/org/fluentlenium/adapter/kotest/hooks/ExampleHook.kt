package org.fluentlenium.adapter.kotest.hooks

import org.fluentlenium.core.FluentControl
import org.fluentlenium.core.components.ComponentInstantiator
import org.fluentlenium.core.hook.BaseHook
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.pagefactory.ElementLocator
import java.util.function.Supplier

class ExampleHook(
    control: FluentControl,
    instantiator: ComponentInstantiator,
    elementSupplier: Supplier<WebElement>,
    locatorSupplier: Supplier<ElementLocator>,
    toStringSupplier: Supplier<String>,
    options: ExampleHookOptions
) :
    BaseHook<ExampleHookOptions>(control, instantiator, elementSupplier, locatorSupplier, toStringSupplier, options) {
    override fun click() {
        super.click()
        clickCount++
    }

    companion object {
        var clickCount = 0
    }
}
