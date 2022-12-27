package io.fluentlenium.adapter.kotest.hooks

import io.fluentlenium.core.FluentControl
import io.fluentlenium.core.components.ComponentInstantiator
import io.fluentlenium.core.hook.BaseHook
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.pagefactory.ElementLocator
import java.util.function.Supplier

class ExampleHook(
    control: _root_ide_package_.io.fluentlenium.core.FluentControl,
    instantiator: _root_ide_package_.io.fluentlenium.core.components.ComponentInstantiator,
    elementSupplier: Supplier<WebElement>,
    locatorSupplier: Supplier<ElementLocator>,
    toStringSupplier: Supplier<String>,
    options: ExampleHookOptions
) :
    _root_ide_package_.io.fluentlenium.core.hook.BaseHook<ExampleHookOptions>(control, instantiator, elementSupplier, locatorSupplier, toStringSupplier, options) {
    override fun click() {
        super.click()
        clickCount++
    }

    companion object {
        var clickCount = 0
    }
}
