package org.fluentlenium.core.proxy;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.Arrays;
import java.util.List;

public class LastElementLocator implements ElementLocator {
    private ElementLocator listLocator;

    public LastElementLocator(ElementLocator listLocator) {
        this.listLocator = listLocator;
    }

    @Override
    public WebElement findElement() {
        List<WebElement> elements = this.listLocator.findElements();
        try {
            return elements.get(elements.size() - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException("Can't find last element ", e);
        }
    }

    @Override
    public List<WebElement> findElements() {
        WebElement element = findElement();
        return Arrays.asList(element);
    }
}
