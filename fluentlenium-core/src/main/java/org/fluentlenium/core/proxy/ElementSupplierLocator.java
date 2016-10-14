package org.fluentlenium.core.proxy;

import com.google.common.base.Supplier;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    public ElementSupplierLocator(final Supplier<WebElement> elementSupplier) {
        this.elementSupplier = elementSupplier;
    }

    @Override
    public WebElement findElement() {
        return elementSupplier.get();
    }

    @Override
    public List<WebElement> findElements() {
        final WebElement webElement = elementSupplier.get();
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
