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
    private ElementLocator listLocator;

    public LastElementLocator(ElementLocator listLocator) {
        this.listLocator = listLocator;
    }

    @Override
    public WebElement findElement() {
        List<WebElement> elements = this.listLocator.findElements();
        if (elements.size() == 0)
            return null;
        return elements.get(elements.size() - 1);
    }

    @Override
    public List<WebElement> findElements() {
        WebElement element = findElement();
        if (element == null)
            return Collections.emptyList();
        return Arrays.asList(element);
    }

    @Override
    public String toString() {
        return listLocator.toString() + " (last)";
    }
}
