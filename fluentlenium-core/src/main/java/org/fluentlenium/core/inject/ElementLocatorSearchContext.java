package org.fluentlenium.core.inject;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.ArrayList;
import java.util.List;

public class ElementLocatorSearchContext implements SearchContext {
    private final ElementLocator locator;

    public ElementLocatorSearchContext(final ElementLocator locator) {
        this.locator = locator;
    }

    @Override
    public List<WebElement> findElements(final By by) {
        final List<WebElement> elements = new ArrayList<>();

        final List<WebElement> baseElements = locator.findElements();

        for (final WebElement element : baseElements) {
            elements.addAll(element.findElements(by));
        }

        return elements;
    }

    @Override
    public WebElement findElement(final By by) {
        return locator.findElement().findElement(by);
    }
}
