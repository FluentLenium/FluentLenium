package org.fluentlenium.core.hook;

import com.google.common.base.Supplier;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.List;

public interface HookChainBuilder {
    List<FluentHook> build(Supplier<WebElement> elementSupplier, Supplier<ElementLocator> locator, Supplier<String> toStringSupplier, List<HookDefinition<?>> hooks);
}
