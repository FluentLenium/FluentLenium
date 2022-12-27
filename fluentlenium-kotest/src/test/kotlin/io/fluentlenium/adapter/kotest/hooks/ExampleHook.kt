package io.fluentlenium.adapter.kotest.hooks

import io.fluentlenium.core.FluentControl
import io.fluentlenium.core.components.ComponentInstantiator
import io.fluentlenium.core.hook.BaseHook
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
