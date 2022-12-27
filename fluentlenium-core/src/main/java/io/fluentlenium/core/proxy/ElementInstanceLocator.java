package io.fluentlenium.core.proxy;

import io.fluentlenium.utils.SupplierOfInstance;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;

/**
 * {@link org.openqa.selenium.support.pagefactory.ElementLocator} for an already found {@link WebElement} instance.
 */
public class ElementInstanceLocator extends ElementSupplierLocator implements WrapsElement {

    /**
     * Creates a new element instance locator
     *
     * @param element element instance
     */
    public ElementInstanceLocator(WebElement element) {
        super(new SupplierOfInstance<>(element));
    }

    public WebElement getWrappedElement() {
        return findElement();
    }
}
