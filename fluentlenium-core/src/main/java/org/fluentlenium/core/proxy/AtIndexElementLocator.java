package org.fluentlenium.core.proxy;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AtIndexElementLocator implements ElementLocator {
    private final int index;
    private ElementLocator listLocator;

    public AtIndexElementLocator(ElementLocator listLocator, int index) {
        this.listLocator = listLocator;
        this.index = index;
    }

    @Override
    public WebElement findElement() {
        List<WebElement> elements = this.listLocator.findElements();
        try {
            return elements.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw noSuchElement(e);
        }
    }

    protected NoSuchElementException noSuchElement(IndexOutOfBoundsException e) {
        return new NoSuchElementException("Can't find element at index " + this.index, e);
    }

    @Override
    public List<WebElement> findElements() {
        WebElement element = findElement();
        return Arrays.asList(element);
    }
}
