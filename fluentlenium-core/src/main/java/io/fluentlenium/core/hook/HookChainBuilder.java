package io.fluentlenium.core.hook;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.List;
import java.util.function.Supplier;

/**
 * Builder of hook chains from element supplier, element locator supplier and hook definitions list.
 */
public interface HookChainBuilder {
    /**
     * Build the hook chain.
     *
     * @param elementSupplier  element supplier
     * @param locator          element locator supplier
     * @param toStringSupplier element toString supplier
     * @param hooks            list of hook definitions
     * @return hook chain
     */
    List<FluentHook> build(Supplier<WebElement> elementSupplier, Supplier<ElementLocator> locator,
            Supplier<String> toStringSupplier, List<HookDefinition<?>> hooks);
}
