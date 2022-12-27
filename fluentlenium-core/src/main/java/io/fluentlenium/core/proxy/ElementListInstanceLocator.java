package io.fluentlenium.core.proxy;

import io.fluentlenium.core.domain.WrapsElements;
import io.fluentlenium.utils.SupplierOfInstance;
import io.fluentlenium.core.domain.WrapsElements;
import io.fluentlenium.utils.SupplierOfInstance;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * {@link org.openqa.selenium.support.pagefactory.ElementLocator} for an already found list of {@link WebElement} instance.
 */
public class ElementListInstanceLocator extends ElementListSupplierLocator implements WrapsElements {

    /**
     * Creates a new element list instance locator
     *
     * @param elements element list instance
     */
    public ElementListInstanceLocator(List<WebElement> elements) {
        super(new SupplierOfInstance<>(elements));
    }

    @Override
    public List<WebElement> getWrappedElements() {
        return findElements();
    }
}
