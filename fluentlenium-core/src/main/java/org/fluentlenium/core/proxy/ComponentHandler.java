package org.fluentlenium.core.proxy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class ComponentHandler implements InvocationHandler {
    private static final Method EQUALS = getMethod(Object.class, "equals", Object.class);
    private static final Method HASH_CODE = getMethod(Object.class, "hashCode");
    private static final Method TO_STRING = getMethod(Object.class, "toString");
    private static final Method GET_WRAPPED_ELEMENT = getMethod(WrapsElement.class, "getWrappedElement");

    private static Method getMethod(Class<?> declaringClass, String name, Class... types) {
        try {
            return declaringClass.getMethod(name, types);
        }
        catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private final ElementLocator locator;

    private WebElement element;

    private List<ProxyElementListener> listeners = new ArrayList<>();

    public ComponentHandler(ElementLocator locator) {
        this.locator = locator;
    }

    public ElementLocator getLocator() {
        return locator;
    }

    public synchronized boolean addListener(ProxyElementListener listener) {
        return listeners.add(listener);
    }

    public synchronized boolean removeListener(ProxyElementListener listener) {
        return listeners.remove(listener);
    }

    protected void fireProxyElementFound(WebElement proxy, ElementLocator locator, WebElement element) {
        for (ProxyElementListener listener : listeners) {
            listener.proxyElementFound(proxy, locator, element);
        }
    }

    protected void fireProxyElementSearch(WebElement proxy, ElementLocator locator) {
        for (ProxyElementListener listener : listeners) {
            listener.proxyElementSearch(proxy, locator);
        }
    }

    public synchronized boolean isLoaded() {
        return element != null;
    }

    public synchronized void reset() {
        this.element = null;
    }

    public synchronized WebElement getOrFindElement(WebElement proxy) {
        if (element == null) {
            fireProxyElementSearch(proxy, locator);
            element = locator.findElement();
            fireProxyElementFound(proxy, locator, element);
        }
        return element;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (element == null) {
            if (GET_WRAPPED_ELEMENT.equals(method)) {
                return proxy;
            }
            if (TO_STRING.equals(method)) {
                return "Proxy Component for: " + locator;
            }
            if (EQUALS.equals(method)) {
                ComponentHandler componentHandler = Proxies.getComponentHandler(args[0]);
                if (componentHandler != null) {
                    return this.equals(componentHandler);
                }
                // Loading element if equals is called with a non ComponentHandler proxy parameter
            }
            if (HASH_CODE.equals(method)) {
                return ComponentHandler.class.hashCode() + locator.hashCode();
            }
        }

        getOrFindElement((WebElement)proxy);

        if (EQUALS.equals(method) && Proxies.getComponentHandler(args[0]) != null) {
            return equalsInternal(proxy, args[0]);
        }
        if (HASH_CODE.equals(method)) {
            return element.hashCode();
        }

        if (!(element instanceof WrapsElement) && GET_WRAPPED_ELEMENT.equals(method)) {
            // Delegates to element getWrappedElement only if implements the interface.
            return element;
        }

        try {
            return method.invoke(element, args);
        } catch (InvocationTargetException e) {
            // Unwrap the underlying exception
            throw e.getCause();
        }
    }

    private boolean equalsInternal(Object me, Object other) {
        if (other == null) {
            return false;
        }
        if (other.getClass() != me.getClass()) {
            // Not same proxy type; false.
            // This may not be true for other scenarios.
        }
        InvocationHandler handler = Proxy.getInvocationHandler(other);
        if (!(handler instanceof ComponentHandler)) {
            // the proxies behave differently.
            return false;
        }
        return ((ComponentHandler) handler).getOrFindElement((WebElement)me).equals(getOrFindElement((WebElement)other));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComponentHandler that = (ComponentHandler) o;

        return locator != null ? locator.equals(that.locator) : that.locator == null;

    }

    @Override
    public int hashCode() {
        return locator != null ? locator.hashCode() : 0;
    }

}
