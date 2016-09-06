package org.fluentlenium.core.proxy;

import com.google.common.base.Supplier;
import lombok.experimental.UtilityClass;
import org.fluentlenium.core.domain.WrapsElements;
import org.fluentlenium.core.hook.HookChainBuilder;
import org.fluentlenium.core.hook.HookDefinition;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * Utility class to create proxies of WebElement, Component, FluentList and List of Components based on their locators.
 */
@UtilityClass
public class LocatorProxies {
    public LocatorHandler getLocatorHandler(Object proxy) {
        while (proxy instanceof WrapsElements && !Proxy.isProxyClass(proxy.getClass())) {
            proxy = ((WrapsElements) proxy).getWrappedElements();
        }
        if (proxy != null && Proxy.isProxyClass(proxy.getClass())) {
            InvocationHandler proxyHandler = Proxy.getInvocationHandler(proxy);
            if (proxyHandler instanceof LocatorHandler) {
                return ((LocatorHandler) proxyHandler);
            }
        }
        return null;
    }

    public <T> T getLocatorResult(T proxy) {
        LocatorHandler<?> componentHandler = getLocatorHandler(proxy);
        if (componentHandler != null) {
            return (T) componentHandler.getLocatorResult();
        }
        return proxy;
    }

    public void reset(Object proxy) {
        LocatorHandler handler = getLocatorHandler(proxy);
        if (handler != null) {
            handler.reset();
        }
    }

    public boolean isPresent(Object proxy) {
        LocatorHandler locatorHandler = getLocatorHandler(proxy);
        if (locatorHandler == null) return true;
        return locatorHandler.isPresent();
    }

    public void now(Object proxy) {
        LocatorHandler<?> handler = getLocatorHandler(proxy);
        if (handler != null) {
            handler.now();
        }
    }

    public boolean isLoaded(Object proxy) {
        LocatorHandler handler = getLocatorHandler(proxy);
        if (handler != null) {
            return handler.isLoaded();
        }
        return true;
    }

    public boolean addProxyListener(Object proxy, ProxyElementListener listener) {
        if (Proxy.isProxyClass(proxy.getClass())) {
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(proxy);
            if (invocationHandler instanceof LocatorHandler) {
                return ((LocatorHandler) invocationHandler).addListener(listener);
            }
        }
        return false;
    }

    public boolean removeProxyListener(Object proxy, ProxyElementListener listener) {
        if (Proxy.isProxyClass(proxy.getClass())) {
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(proxy);
            if (invocationHandler instanceof LocatorHandler) {
                return ((LocatorHandler) invocationHandler).removeListener(listener);
            }
        }
        return false;
    }

    public WebElement createWebElement(final WebElement element) {
        return createWebElement(new ElementInstanceLocator(element));
    }

    public WebElement createWebElement(Supplier<WebElement> elementSupplier) {
        return createWebElement(new ElementSupplierLocator(elementSupplier));
    }

    public WebElement createWebElement(ElementLocator locator) {
        final ComponentHandler handler = new ComponentHandler(locator);
        WebElement proxy = (WebElement) Proxy.newProxyInstance(locator.getClass().getClassLoader(), new Class[]{WebElement.class, Locatable.class, WrapsElement.class}, handler);
        handler.setProxy(proxy);
        return proxy;
    }

    public List<WebElement> createWebElementList(final List<WebElement> elements) {
        return createWebElementList(new ElementListInstanceLocator(elements));
    }

    public List<WebElement> createWebElementList(Supplier<List<WebElement>> elementsSupplier) {
        return createWebElementList(new ElementListSupplierLocator(elementsSupplier));
    }

    public List<WebElement> createWebElementList(ElementLocator locator) {
        final ListHandler handler = new ListHandler(locator);
        List<WebElement> proxy = (List<WebElement>) Proxy.newProxyInstance(locator.getClass().getClassLoader(), new Class[]{List.class, WrapsElements.class}, handler);
        handler.setProxy(proxy);
        return proxy;
    }

    public void setHooks(Object proxy, HookChainBuilder hookChainBuilder, List<HookDefinition<?>> hookDefinitions) {
        LocatorHandler<?> componentHandler = getLocatorHandler(proxy);
        componentHandler.setHooks(hookChainBuilder, hookDefinitions);
    }

    public WebElement first(List<WebElement> proxy) {
        LocatorHandler locatorHandler = getLocatorHandler(proxy);
        return createWebElement(new FirstElementLocator(locatorHandler.getLocator()));
    }

    public WebElement last(List<WebElement> proxy) {
        LocatorHandler locatorHandler = getLocatorHandler(proxy);
        return createWebElement(new LastElementLocator(locatorHandler.getLocator()));
    }

    public WebElement index(List<WebElement> proxy, int index) {
        LocatorHandler locatorHandler = getLocatorHandler(proxy);
        return createWebElement(new AtIndexElementLocator(locatorHandler.getLocator(), index));
    }
}
