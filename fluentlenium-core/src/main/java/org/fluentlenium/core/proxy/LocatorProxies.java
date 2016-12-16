package org.fluentlenium.core.proxy;

import java.util.function.Supplier;
import org.fluentlenium.core.domain.WrapsElements;
import org.fluentlenium.core.hook.HookChainBuilder;
import org.fluentlenium.core.hook.HookDefinition;
import org.openqa.selenium.NoSuchElementException;
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
    public static NoSuchElementException noSuchElement(final Object proxy) {
        final LocatorHandler locatorHandler = getLocatorHandler(proxy);
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
    public static String getMessageContext(final Object proxy) {
        final LocatorHandler locatorHandler = getLocatorHandler(proxy);
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
            final InvocationHandler proxyHandler = Proxy.getInvocationHandler(proxy);
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
    public static <T> T getLocatorResult(final T proxy) {
        final LocatorHandler<?> componentHandler = getLocatorHandler(proxy);
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
    public static void reset(final Object proxy) {
        final LocatorHandler handler = getLocatorHandler(proxy);
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
    public static boolean present(final Object proxy) {
        final LocatorHandler locatorHandler = getLocatorHandler(proxy);
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
    public static void now(final Object proxy) {
        final LocatorHandler<?> handler = getLocatorHandler(proxy);
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
    public static boolean loaded(final Object proxy) {
        final LocatorHandler handler = getLocatorHandler(proxy);
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
    public static boolean addProxyListener(final Object proxy, final ProxyElementListener listener) {
        if (Proxy.isProxyClass(proxy.getClass())) {
            final InvocationHandler invocationHandler = Proxy.getInvocationHandler(proxy);
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
    public static boolean removeProxyListener(final Object proxy, final ProxyElementListener listener) {
        if (Proxy.isProxyClass(proxy.getClass())) {
            final InvocationHandler invocationHandler = Proxy.getInvocationHandler(proxy);
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
    public static WebElement createWebElement(final WebElement element) {
        return createWebElement(new ElementInstanceLocator(element));
    }

    /**
     * Create a WebElement proxy from an element supplier.
     *
     * @param elementSupplier element supplier
     * @return proxy
     */
    public static WebElement createWebElement(final Supplier<WebElement> elementSupplier) {
        return createWebElement(new ElementSupplierLocator(elementSupplier));
    }

    /**
     * Create a WebElement proxy from an element locator.
     *
     * @param locator element locator
     * @return proxy
     */
    public static WebElement createWebElement(final ElementLocator locator) {
        final ComponentHandler handler = new ComponentHandler(locator);
        final WebElement proxy = (WebElement) Proxy.newProxyInstance(locator.getClass().getClassLoader(),
                new Class[] {WebElement.class, Locatable.class, WrapsElement.class}, handler);
        handler.setProxy(proxy);
        return proxy;
    }

    /**
     * Create a list of WebElement proxies from an existing element list.
     *
     * @param elements existing element list
     * @return proxy
     */
    public static List<WebElement> createWebElementList(final List<WebElement> elements) {
        return createWebElementList(new ElementListInstanceLocator(elements));
    }

    /**
     * Create a list of WebElement proxies from a supplier of element list.
     *
     * @param elementsSupplier supplier of element list
     * @return proxy
     */
    public static List<WebElement> createWebElementList(final Supplier<List<WebElement>> elementsSupplier) {
        return createWebElementList(new ElementListSupplierLocator(elementsSupplier));
    }

    /**
     * Create a list of WebElement proxies from a locator of element list.
     *
     * @param locator locator of element list
     * @return proxy
     */
    public static List<WebElement> createWebElementList(final ElementLocator locator) {
        final ListHandler handler = new ListHandler(locator);
        final List<WebElement> proxy = (List<WebElement>) Proxy
                .newProxyInstance(locator.getClass().getClassLoader(), new Class[] {List.class, WrapsElements.class}, handler);
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
    public static void setHooks(final Object proxy, final HookChainBuilder hookChainBuilder,
            final List<HookDefinition<?>> hookDefinitions) {
        final LocatorHandler<?> componentHandler = getLocatorHandler(proxy);
        componentHandler.setHooks(hookChainBuilder, hookDefinitions);
    }

    /**
     * Creates a proxy element matching the first element of the list.
     *
     * @param proxy list of element
     * @return proxy element matching the first element
     */
    public static WebElement first(final List<WebElement> proxy) {
        final LocatorHandler locatorHandler = getLocatorHandler(proxy);
        return createWebElement(new FirstElementLocator(locatorHandler.getLocator()));
    }

    /**
     * Creates a proxy element matching the last element of the list.
     *
     * @param proxy list of element
     * @return proxy element matching the last element
     */
    public static WebElement last(final List<WebElement> proxy) {
        final LocatorHandler locatorHandler = getLocatorHandler(proxy);
        return createWebElement(new LastElementLocator(locatorHandler.getLocator()));
    }

    /**
     * Creates a proxy element matching the n-th element of the list.
     *
     * @param proxy list of element
     * @param index index to match
     * @return proxy element matching the n-th element
     */
    public static WebElement index(final List<WebElement> proxy, final int index) {
        final LocatorHandler locatorHandler = getLocatorHandler(proxy);
        return createWebElement(new AtIndexElementLocator(locatorHandler.getLocator(), index));
    }
}
