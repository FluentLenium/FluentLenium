package io.fluentlenium.core.proxy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * Element locator implemented by a {@link Supplier} of {@link WebElement}.
 */
public class ElementSupplierLocator implements ElementLocator {
    private final Supplier<WebElement> elementSupplier;

    /**
     * Creates a new element supplier locator.
     *
     * @param elementSupplier element supplier
     */
    public ElementSupplierLocator(Supplier<WebElement> elementSupplier) {
        this.elementSupplier = elementSupplier;
    }

    @Override
    public WebElement findElement() {
        return elementSupplier.get();
    }

    @Override
    public List<WebElement> findElements() {
        WebElement webElement = elementSupplier.get();
        if (webElement == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(webElement);
    }

    @Override
    public String toString() {
        return String.valueOf(elementSupplier);
    }
}
