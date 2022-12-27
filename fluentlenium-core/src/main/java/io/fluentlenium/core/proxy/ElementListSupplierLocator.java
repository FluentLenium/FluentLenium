package io.fluentlenium.core.proxy;

import io.fluentlenium.utils.SupplierOfInstance;import io.fluentlenium.utils.SupplierOfInstance;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.List;
import java.util.function.Supplier;

/**
 * Element locator implemented by a {@link Supplier} of list of {@link WebElement}.
 */
public class ElementListSupplierLocator implements ElementLocator {
    private final Supplier<List<WebElement>> elementsSupplier;

    /**
     * Creates a new element list supplier locator
     *
     * @param elements element list instance
     */
    public ElementListSupplierLocator(List<WebElement> elements) {
        elementsSupplier = new SupplierOfInstance<>(elements);
    }

    /**
     * Creates a new element list supplier locator
     *
     * @param elementsSupplier element list supplier
     */
    public ElementListSupplierLocator(Supplier<List<WebElement>> elementsSupplier) {
        this.elementsSupplier = elementsSupplier;
    }

    @Override
    public WebElement findElement() {
        List<WebElement> webElements = elementsSupplier.get();
        if (webElements != null && !webElements.isEmpty()) {
            return webElements.iterator().next();
        }
        return null;
    }

    @Override
    public List<WebElement> findElements() {
        return elementsSupplier.get();
    }

    @Override
    public String toString() {
        return String.valueOf(elementsSupplier);
    }
}
