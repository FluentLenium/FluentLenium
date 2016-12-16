package org.fluentlenium.core.proxy;

import org.fluentlenium.utils.SupplierOfInstance;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;

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

    @Override
    public WebElement getWrappedElement() {
        return findElement();
    }
}
