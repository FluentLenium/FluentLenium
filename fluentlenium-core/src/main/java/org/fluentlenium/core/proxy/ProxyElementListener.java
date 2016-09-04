package org.fluentlenium.core.proxy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

public interface ProxyElementListener {
    void proxyElementSearch(Object proxy, ElementLocator locator);
    void proxyElementFound(Object proxy, ElementLocator locator, WebElement element);
}
