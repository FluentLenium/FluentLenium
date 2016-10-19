package org.fluentlenium.core.proxy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * {@link ElementLocator} retrieving the last element from another locator.
 */
public class LastElementLocator implements ElementLocator {
    private final ElementLocator listLocator;

    /**
     * Creates a new last element locator.
     *
     * @param listLocator element list locator
     */
    public LastElementLocator(final ElementLocator listLocator) {
        this.listLocator = listLocator;
    }

    private WebElement findElementImpl() {
        final List<WebElement> elements = this.listLocator.findElements();
        if (elements.isEmpty()) {
            return null;
        }
        return elements.get(elements.size() - 1);
    }

    @Override
    public WebElement findElement() {
        final WebElement element = findElementImpl();
        if (element == null) {
            throw ElementLocatorUtils.noSuchElementException(String.valueOf("Element " + this));
        }
        return element;
    }

    @Override
    public List<WebElement> findElements() {
        final WebElement element = findElementImpl();
        if (element == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(element);
    }

    @Override
    public String toString() {
        return listLocator.toString() + " (last)";
    }
}
