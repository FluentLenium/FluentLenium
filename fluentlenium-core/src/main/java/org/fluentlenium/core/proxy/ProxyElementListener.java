package org.fluentlenium.core.proxy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.List;

/**
 * Proxy element listener.
 */
public interface ProxyElementListener {
    /**
     * Invoked when proxy element search is starting.
     *
     * @param proxy   proxy
     * @param locator element locator
     */
    void proxyElementSearch(Object proxy, ElementLocator locator);

    /**
     * Invoked when proxy element search is over and elements were found.
     *
     * @param proxy    proxy
     * @param locator  element locator
     * @param elements found elements
     */
    void proxyElementFound(Object proxy, ElementLocator locator, List<WebElement> elements);
}
