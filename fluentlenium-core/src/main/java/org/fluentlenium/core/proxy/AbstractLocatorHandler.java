package org.fluentlenium.core.proxy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLocatorHandler {
    private List<ProxyElementListener> listeners = new ArrayList<>();

    public synchronized boolean addListener(ProxyElementListener listener) {
        return listeners.add(listener);
    }

    public synchronized boolean removeListener(ProxyElementListener listener) {
        return listeners.remove(listener);
    }

    protected void fireProxyElementFound(Object proxy, ElementLocator locator, WebElement element) {
        for (ProxyElementListener listener : listeners) {
            listener.proxyElementFound(proxy, locator, element);
        }
    }

    protected void fireProxyElementSearch(Object proxy, ElementLocator locator) {
        for (ProxyElementListener listener : listeners) {
            listener.proxyElementSearch(proxy, locator);
        }
    }
}
