package org.fluentlenium.core.proxy;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.pagefactory.ElementLocator;

public class FirstElementLocator extends AtIndexElementLocator {
    public FirstElementLocator(ElementLocator listLocator) {
        super(listLocator, 0);
    }

    protected NoSuchElementException noSuchElement(IndexOutOfBoundsException e) {
        return new NoSuchElementException("Can't find first element ", e);
    }
}
