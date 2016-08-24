package org.fluentlenium.core.proxy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.Arrays;
import java.util.List;

public class InstanceElementLocator implements ElementLocator, WrapsElement {
    private final WebElement element;

    public InstanceElementLocator(WebElement element) {
        this.element = element;
    }

    @Override
    public WebElement findElement() {
        return this.element;
    }

    @Override
    public List<WebElement> findElements() {
        return Arrays.asList(findElement());
    }

    @Override
    public WebElement getWrappedElement() {
        return this.element;
    }
}
