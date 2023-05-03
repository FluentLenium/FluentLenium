package io.fluentlenium.core.proxy;

import io.fluentlenium.core.domain.WrapsElements;
import io.fluentlenium.core.hook.HookChainBuilder;
import io.fluentlenium.core.hook.HookDefinition;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.function.Supplier;

/**
 * Utility class to create proxies of WebElement, Component, FluentList and List of Components based on their locators.
 */
public final class LocatorProxies {
    private LocatorProxies() {
        // Utility class
    }

    /**
     * Build a NoSuchElementException using message provided by proxy.
     *
     * @param proxy proxy
     * @return NoSuchElementException No such element exception
     */
    public static NoSuchElementException noSuchElement(Object proxy) {
        LocatorHandler locatorHandler = getLocatorHandler(proxy);
        if (locatorHandler == null) {
            return new NoSuchElementException("No such element");
        }
        return locatorHandler.noSuchElement();
    }

    /**
     * Get the message context of given proxy.
     *
     * @param proxy proxy
     * @return message context
     */
    public static String getMessageContext(Object proxy) {
        LocatorHandler locatorHandler = getLocatorHandler(proxy);
        if (locatorHandler == null) {
            return "";
        }
        return locatorHandler.getMessageContext();
    }

    /**
     * Get the proxy locator handler.
     *
     * @param proxy proxy object
     * @return locator handler, or null if not found
     */
    public static LocatorHandler getLocatorHandler(Object proxy) {
        while (proxy instanceof WrapsElements && !Proxy.isProxyClass(proxy.getClass())) {
            proxy = ((WrapsElements) proxy).getWrappedElements();
        }
        if (proxy != null && Proxy.isProxyClass(proxy.getClass())) {
            InvocationHandler proxyHandler = Proxy.getInvocationHandler(proxy);
            if (proxyHandler instanceof LocatorHandler) {
                return (LocatorHandler) proxyHandler;
            }
        }
        return null;
    }

    /**
     * Get the underlying result of a proxy, through locator handler.
     *
     * @param proxy proxy object
     * @param <T>   type of the result
     * @return result
     * @see LocatorHandler#getLocatorResult()
     */
    public static <T> T getLocatorResult(T proxy) {
        LocatorHandler<?> componentHandler = getLocatorHandler(proxy);
        if (componentHandler != null) {
            return (T) componentHandler.getLocatorResult();
        }
        return proxy;
    }

    /**
     * Reset the proxy locator handler.
     *
     * @param proxy proxy object
     * @see LocatorHandler#reset()
     */
    public static void reset(Object proxy) {
        LocatorHandler handler = getLocatorHandler(proxy);
        if (handler != null) {
            handler.reset();
        }
    }

    /**
     * Check if the proxy element is present.
     *
     * @param proxy proxy object
     * @return true if result is present, false otherwise
     * @see LocatorHandler#present()
     */
    public static boolean present(Object proxy) {
        LocatorHandler locatorHandler = getLocatorHandler(proxy);
        if (locatorHandler == null) {
            return true;
        }
        return locatorHandler.present();
    }

    /**
     * If result is not loaded, load result immediatly. If it's already loaded, it has no effect.
     *
     * @param proxy proxy object
     * @see LocatorHandler#now()
     */
    public static void now(Object proxy) {
        LocatorHandler<?> handler = getLocatorHandler(proxy);
        if (handler != null) {
            handler.now();
        }
    }

    /**
     * Check if this proxy has loaded it's result.
     *
     * @param proxy proxy object
     * @return true if the result is loaded, false otherwise
     */
    public static boolean loaded(Object proxy) {
        LocatorHandler handler = getLocatorHandler(proxy);
        if (handler != null) {
            return handler.loaded();
        }
        return true;
    }

    /**
     * Add a proxy listener for this proxy.
     *
     * @param proxy    proxy object
     * @param listener listener to add, which will be notified when result is searched and found
     * @return true if the listener was added, false otherwise
     */
    public static boolean addProxyListener(Object proxy, ProxyElementListener listener) {
        if (Proxy.isProxyClass(proxy.getClass())) {
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(proxy);
            if (invocationHandler instanceof LocatorHandler) {
                return ((LocatorHandler) invocationHandler).addListener(listener);
            }
        }
        return false;
    }

    /**
     * Removes a proxy element listener.
     *
     * @param proxy    proxy object
     * @param listener listener to remove
     * @return true if the listener was removed, false otherwise
     */
    public static boolean removeProxyListener(Object proxy, ProxyElementListener listener) {
        if (Proxy.isProxyClass(proxy.getClass())) {
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(proxy);
            if (invocationHandler instanceof LocatorHandler) {
                return ((LocatorHandler) invocationHandler).removeListener(listener);
            }
        }
        return false;
    }

    /**
     * Create a WebElement proxy from an existing element.
     *
     * @param element existing element
     * @return proxy
     */
    public static WebElement createWebElement(WebElement element) {
        return createWebElement(new ElementInstanceLocator(element));
    }

    /**
     * Create a WebElement proxy from an element supplier.
     *
     * @param elementSupplier element supplier
     * @return proxy
     */
    public static WebElement createWebElement(Supplier<WebElement> elementSupplier) {
        return createWebElement(new ElementSupplierLocator(elementSupplier));
    }

    /**
     * Create a WebElement proxy from an element locator.
     *
     * @param locator element locator
     * @return proxy
     */
    public static WebElement createWebElement(ElementLocator locator) {
        ComponentHandler handler = new ComponentHandler(locator);
        WebElement proxy = (WebElement) Proxy.newProxyInstance(locator.getClass().getClassLoader(),
                new Class[]{WebElement.class, Locatable.class, WrapsElement.class}, handler);
        handler.setProxy(proxy);
        return proxy;
    }

    /**
     * Create a list of WebElement proxies from an existing element list.
     *
     * @param elements existing element list
     * @return proxy
     */
    public static List<WebElement> createWebElementList(List<WebElement> elements) {
        return createWebElementList(new ElementListInstanceLocator(elements));
    }

    /**
     * Create a list of WebElement proxies from a supplier of element list.
     *
     * @param elementsSupplier supplier of element list
     * @return proxy
     */
    public static List<WebElement> createWebElementList(Supplier<List<WebElement>> elementsSupplier) {
        return createWebElementList(new ElementListSupplierLocator(elementsSupplier));
    }

    /**
     * Create a list of WebElement proxies from a locator of element list.
     *
     * @param locator locator of element list
     * @return proxy
     */
    public static List<WebElement> createWebElementList(ElementLocator locator) {
        ListHandler handler = new ListHandler(locator);
        List<WebElement> proxy = (List<WebElement>) Proxy
                .newProxyInstance(locator.getClass().getClassLoader(), new Class[]{List.class, WrapsElements.class}, handler);
        handler.setProxy(proxy);
        return proxy;
    }

    /**
     * Apply this hook list.
     *
     * @param proxy            proxy object
     * @param hookChainBuilder hook chain builder
     * @param hookDefinitions  hook definitions
     */
    public static void setHooks(Object proxy, HookChainBuilder hookChainBuilder, List<HookDefinition<?>> hookDefinitions) {
        LocatorHandler<?> componentHandler = getLocatorHandler(proxy);
        componentHandler.setHooks(hookChainBuilder, hookDefinitions);
    }

    /**
     * Creates a proxy element matching the first element of the list.
     *
     * @param proxy list of element
     * @return proxy element matching the first element
     */
    public static WebElement first(List<WebElement> proxy) {
        LocatorHandler locatorHandler = getLocatorHandler(proxy);
        return createWebElement(new FirstElementLocator(locatorHandler.getLocator()));
    }

    /**
     * Creates a proxy element matching the last element of the list.
     *
     * @param proxy list of element
     * @return proxy element matching the last element
     */
    public static WebElement last(List<WebElement> proxy) {
        LocatorHandler locatorHandler = getLocatorHandler(proxy);
        return createWebElement(new LastElementLocator(locatorHandler.getLocator()));
    }

    /**
     * Creates a proxy element matching the n-th element of the list.
     *
     * @param proxy list of element
     * @param index index to match
     * @return proxy element matching the n-th element
     */
    public static WebElement index(List<WebElement> proxy, int index) {
        LocatorHandler locatorHandler = getLocatorHandler(proxy);
        return createWebElement(new AtIndexElementLocator(locatorHandler.getLocator(), index));
    }
}
