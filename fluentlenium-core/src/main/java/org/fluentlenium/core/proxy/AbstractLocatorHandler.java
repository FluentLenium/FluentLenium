package org.fluentlenium.core.proxy;

import com.google.common.base.Supplier;
import org.fluentlenium.core.hook.FluentHook;
import org.fluentlenium.core.hook.HookChainBuilder;
import org.fluentlenium.core.hook.HookDefinition;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract proxy handler supporting lazy loading and hooks on {@link WebElement}.
 *
 * @param <T> type of underlying object.
 */
public abstract class AbstractLocatorHandler<T> implements InvocationHandler, LocatorHandler<T> {
    private static final Method TO_STRING = getMethod(Object.class, "toString");
    private static final Method EQUALS = getMethod(Object.class, "equals", Object.class);
    private static final Method HASH_CODE = getMethod(Object.class, "hashCode");

    protected HookChainBuilder hookChainBuilder = null;
    protected List<HookDefinition<?>> hookDefinitions = null;

    protected static Method getMethod(Class<?> declaringClass, String name, Class... types) {
        try {
            return declaringClass.getMethod(name, types);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private List<ProxyElementListener> listeners = new ArrayList<>();

    @Override
    public synchronized boolean addListener(ProxyElementListener listener) {
        return listeners.add(listener);
    }

    @Override
    public synchronized boolean removeListener(ProxyElementListener listener) {
        return listeners.remove(listener);
    }

    protected void fireProxyElementSearch() {
        for (ProxyElementListener listener : listeners) {
            listener.proxyElementSearch(proxy, locator);
        }
    }

    protected void fireProxyElementFound(T result) {
        for (ProxyElementListener listener : listeners) {
            listener.proxyElementFound(proxy, locator, resultToList(result));
        }
    }

    protected abstract List<WebElement> resultToList(T result);

    protected T proxy;
    protected final ElementLocator locator;
    protected T result;

    private List<FluentHook> hooks;

    public AbstractLocatorHandler(ElementLocator locator) {
        this.locator = locator;
    }

    public void setProxy(T proxy) {
        this.proxy = proxy;
    }

    abstract public T getLocatorResultImpl();

    public synchronized T getLocatorResult() {
        if (result == null) {
            fireProxyElementSearch();
            result = getLocatorResultImpl();
            fireProxyElementFound(result);
        }
        return result;
    }

    protected abstract WebElement getElement();

    @Override
    public void setHooks(HookChainBuilder hookChainBuilder, List<HookDefinition<?>> hookDefinitions) {
        if (hookDefinitions == null || hookDefinitions.size() == 0) {
            this.hookChainBuilder = null;
            this.hookDefinitions = null;

            hooks = null;
        } else {
            this.hookChainBuilder = hookChainBuilder;
            this.hookDefinitions = hookDefinitions;

            hooks = hookChainBuilder.build(new Supplier<WebElement>() {
                @Override
                public WebElement get() {
                    return getElement();
                }
            }, new Supplier<ElementLocator>() {
                @Override
                public ElementLocator get() {
                    return locator;
                }
            }, hookDefinitions);
        }
    }

    @Override
    public ElementLocator getLocator() {
        return locator;
    }

    @Override
    public ElementLocator getHookLocator() {
        if (hooks != null && hooks.size() > 0) {
            return hooks.get(hooks.size() - 1);
        }
        return locator;
    }

    @Override
    public WebElement getHookElement() {
        if (getElement() == null) return null;
        if (hooks != null && hooks.size() > 0) {
            return hooks.get(hooks.size() - 1);
        }
        return getElement();
    }

    @Override
    public boolean isLoaded() {
        return result != null;
    }

    @Override
    public boolean isPresent() {
        try {
            now();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
        return result != null;
    }

    @Override
    public void reset() {
        result = null;
    }

    @Override
    public void now() {
        getLocatorResult();
    }

    protected abstract T getInvocationTarget();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (result == null) {
            if (TO_STRING.equals(method)) {
                return toString();
            }

            if (EQUALS.equals(method)) {
                LocatorHandler otherLocatorHandler = LocatorProxies.getLocatorHandler(args[0]);
                if (otherLocatorHandler != null) {
                    if (!otherLocatorHandler.isLoaded() || args[0] == null) {
                        return this.equals(otherLocatorHandler);
                    } else {
                        return args[0].equals(proxy);
                    }
                }
            }

            if (HASH_CODE.equals(method)) {
                return 2048 + locator.hashCode();
            }
        }

        getLocatorResult();

        if (EQUALS.equals(method)) {
            LocatorHandler otherLocatorHandler = LocatorProxies.getLocatorHandler(args[0]);
            if (otherLocatorHandler != null && !otherLocatorHandler.isLoaded()) {
                otherLocatorHandler.now();
                return otherLocatorHandler.equals(this);
            }
        }

        Object returnValue;
        try {
            returnValue = method.invoke(getInvocationTarget(), args);
        } catch (InvocationTargetException e) {
            // Unwrap the underlying exception
            throw e.getCause();
        }
        return returnValue;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractLocatorHandler that = (AbstractLocatorHandler) o;

        return locator != null ? locator.equals(that.locator) : that.locator == null;
    }

    @Override
    public String toString() {
        return "Proxy for " + locator;
    }
}
