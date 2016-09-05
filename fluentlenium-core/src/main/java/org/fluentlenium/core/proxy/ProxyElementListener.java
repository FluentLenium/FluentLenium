package org.fluentlenium.core.proxy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.List;

public interface ProxyElementListener {
    void proxyElementSearch(Object proxy, ElementLocator locator);

    void proxyElementFound(Object proxy, ElementLocator locator, List<WebElement> elements);
}
