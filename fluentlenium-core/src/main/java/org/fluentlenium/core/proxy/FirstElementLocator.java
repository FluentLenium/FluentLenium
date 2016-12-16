package org.fluentlenium.core.proxy;

import org.openqa.selenium.support.pagefactory.ElementLocator;

/**
 * {@link ElementLocator} retrieving the first element from another locator.
 */
public class FirstElementLocator extends AtIndexElementLocator {
    /**
     * Creates a new first element locator.
     *
     * @param listLocator element list locator
     */
    public FirstElementLocator(ElementLocator listLocator) {
        super(listLocator, 0);
    }

    @Override
    public String toString() {
        return listLocator.toString() + " (first)";
    }
}
