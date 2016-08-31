package org.fluentlenium.core.proxy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.Arrays;
import java.util.List;

public class ElementLocatorAdapter implements ElementLocator, WrapsElement {
    private final WebElement element;

    public ElementLocatorAdapter(WebElement element) {
        this.element = element;
    }

    @Override
    public WebElement getWrappedElement() {
        return this.element;
    }

    @Override
    public WebElement findElement() {
        return element;
    }

    @Override
    public List<WebElement> findElements() {
        return Arrays.asList(element);
    }
}
