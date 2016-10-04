package org.fluentlenium.core.proxy;

import com.google.common.base.Suppliers;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;

/**
 * {@link org.openqa.selenium.support.pagefactory.ElementLocator} for an already found {@link WebElement} instance.
 */
public class ElementInstanceLocator extends ElementSupplierLocator implements WrapsElement {

    public ElementInstanceLocator(final WebElement element) {
        super(Suppliers.ofInstance(element));
    }

    @Override
    public WebElement getWrappedElement() {
        return findElement();
    }

    @Override
    public String toString() {
        return getWrappedElement().toString();
    }
}
