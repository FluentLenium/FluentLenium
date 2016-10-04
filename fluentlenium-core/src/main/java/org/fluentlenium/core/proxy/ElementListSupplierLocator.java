package org.fluentlenium.core.proxy;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.List;

/**
 * Element locator implemented by a {@link Supplier} of list of {@link WebElement}.
 */
public class ElementListSupplierLocator implements ElementLocator {
    private final Supplier<List<WebElement>> elementsSupplier;

    public ElementListSupplierLocator(final List<WebElement> elementsSupplier) {
        this.elementsSupplier = Suppliers.ofInstance(elementsSupplier);
    }

    public ElementListSupplierLocator(final Supplier<List<WebElement>> elementsSupplier) {
        this.elementsSupplier = elementsSupplier;
    }

    @Override
    public WebElement findElement() {
        final List<WebElement> webElements = elementsSupplier.get();
        if (webElements != null && !webElements.isEmpty()) {
            return webElements.iterator().next();
        }
        return null;
    }

    @Override
    public List<WebElement> findElements() {
        return this.elementsSupplier.get();
    }
}
