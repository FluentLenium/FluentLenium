package org.fluentlenium.core.proxy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

public interface ProxyElementListener {
    void proxyElementSearch(WebElement proxy, ElementLocator locator);
    void proxyElementFound(WebElement proxy, ElementLocator locator, WebElement element);
}
