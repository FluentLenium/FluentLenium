package org.fluentlenium.core.proxy;

import org.fluentlenium.core.domain.ElementUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * {@link ElementLocator} retrieving a particular index element from another locator.
 */
public class AtIndexElementLocator implements ElementLocator {
    private final int index;
    protected ElementLocator listLocator;

    /**
     * Creates a new at-index element locator.
     *
     * @param listLocator element list locator
     * @param index       index to retrieve
     */
    public AtIndexElementLocator(final ElementLocator listLocator, final int index) {
        this.listLocator = listLocator;
        this.index = index;
    }

    private WebElement findElementImpl() {
        final List<WebElement> elements = this.listLocator.findElements();
        if (index >= elements.size()) {
            return null;
        }
        return elements.get(index);
    }

    @Override
    public WebElement findElement() {
        final WebElement element = findElementImpl();
        if (element == null) {
            throw ElementUtils.noSuchElementException(String.valueOf("Element " + this));
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
        return listLocator.toString() + " (index=" + index + ")";
    }
}
