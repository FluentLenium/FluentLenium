package org.fluentlenium.core.inject;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.ArrayList;
import java.util.List;

public class ElementLocatorSearchContext implements SearchContext {
    private final ElementLocator locator;

    public ElementLocatorSearchContext(ElementLocator locator) {
        this.locator = locator;
    }

    @Override
    public List<WebElement> findElements(By by) {
        List<WebElement> elements = new ArrayList<>();

        List<WebElement> baseElements = locator.findElements();

        for (WebElement element : baseElements) {
            elements.addAll(element.findElements(by));
        }

        return elements;
    }

    @Override
    public WebElement findElement(By by) {
        return locator.findElement().findElement(by);
    }
}
