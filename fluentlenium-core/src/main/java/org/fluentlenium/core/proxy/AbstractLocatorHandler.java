package org.fluentlenium.core.proxy;

import com.google.common.base.Supplier;
import org.fluentlenium.core.hook.FluentHook;
import org.fluentlenium.core.hook.HookChainBuilder;
import org.fluentlenium.core.hook.HookDefinition;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Abstract proxy handler supporting lazy loading and hooks on {@link WebElement}.
 *
 * @param <T> type of underlying object.
 */
public abstract class AbstractLocatorHandler<T> implements InvocationHandler, LocatorHandler<T> {
    private static final Method TO_STRING = getMethod(Object.class, "toString");
    private static final Method EQUALS = getMethod(Object.class, "equals", Object.class);
    private static final Method HASH_CODE = getMethod(Object.class, "hashCode");

    private static final int MAX_RETRY = 5;
    private static final int HASH_CODE_SEED = 2048;

    protected HookChainBuilder hookChainBuilder;
    protected List<HookDefinition<?>> hookDefinitions;

    private final List<ProxyElementListener> listeners = new ArrayList<>();

    protected T proxy;
    protected final ElementLocator locator;
    protected T result;

    protected List<FluentHook> hooks;

    protected static Method getMethod(final Class<?> declaringClass, final String name, final Class... types) {
        try {
            return declaringClass.getMethod(name, types);
        } catch (final NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public boolean addListener(final ProxyElementListener listener) {
        return listeners.add(listener);
    }

    @Override
    public boolean removeListener(final ProxyElementListener listener) {
        return listeners.remove(listener);
    }

    protected void fireProxyElementSearch() {
        for (final ProxyElementListener listener : listeners) {
            listener.proxyElementSearch(proxy, locator);
        }
    }

    protected void fireProxyElementFound(final T result) {
        for (final ProxyElementListener listener : listeners) {
            listener.proxyElementFound(proxy, locator, resultToList(result));
        }
    }

    protected abstract List<WebElement> resultToList(T result);

    public AbstractLocatorHandler(final ElementLocator locator) {
        this.locator = locator;
    }

    public void setProxy(final T proxy) {
        this.proxy = proxy;
    }

    public abstract T getLocatorResultImpl();

    public T getLocatorResult() {
        synchronized (this) {
            if (result != null && isStale()) {
                result = null;
            }
            if (result == null) {
                fireProxyElementSearch();
                result = getLocatorResultImpl();
                fireProxyElementFound(result);
            }
            return result;
        }
    }

    protected abstract boolean isStale();

    protected abstract WebElement getElement();

    @Override
    public void setHooks(final HookChainBuilder hookChainBuilder, final List<HookDefinition<?>> hookDefinitions) {
        if (hookDefinitions == null || hookDefinitions.isEmpty()) {
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
            }, new Supplier<String>() {
                @Override
                public String get() {
                    return proxy.toString();
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
        if (hooks != null && !hooks.isEmpty()) {
            return hooks.get(hooks.size() - 1);
        }
        return locator;
    }

    @Override
    public boolean isLoaded() {
        return result != null;
    }

    @Override
    public boolean isPresent() {
        try {
            now();
        } catch (TimeoutException | NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
        return result != null && !isStale();
    }

    @Override
    public void reset() {
        result = null;
    }

    @Override
    public void now() {
        getLocatorResult();
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable { // NOPMD UseVarargs
        if (TO_STRING.equals(method)) {
            return proxyToString(result == null ? null : (String) invoke(method, args));
        }
        if (result == null) {
            if (EQUALS.equals(method)) {
                final LocatorHandler otherLocatorHandler = LocatorProxies.getLocatorHandler(args[0]);
                if (otherLocatorHandler != null) {
                    if (!otherLocatorHandler.isLoaded() || args[0] == null) {
                        return this.equals(otherLocatorHandler);
                    } else {
                        return args[0].equals(proxy);
                    }
                }
            }

            if (HASH_CODE.equals(method)) {
                return HASH_CODE_SEED + locator.hashCode();
            }
        }

        if (EQUALS.equals(method)) {
            final LocatorHandler otherLocatorHandler = LocatorProxies.getLocatorHandler(args[0]);
            if (otherLocatorHandler != null && !otherLocatorHandler.isLoaded()) {
                otherLocatorHandler.now();
                return otherLocatorHandler.equals(this);
            }
        }

        getLocatorResult();

        Throwable lastThrowable = null;
        for (int i = 0; i < MAX_RETRY; i++) {
            try {
                return invoke(method, args);
            } catch (final StaleElementReferenceException e) {
                lastThrowable = e;
                reset();
                getLocatorResult(); // Reload the stale element
            }
        }

        throw lastThrowable;
    }

    //CHECKSTYLE.OFF: IllegalThrows
    private Object invoke(final Method method, final Object[] args) throws Throwable { // NOPMD UseVarargs
        final Object returnValue;
        try {
            returnValue = method.invoke(getInvocationTarget(method), args);
        } catch (final InvocationTargetException e) {
            // Unwrap the underlying exception
            throw e.getCause();
        }
        return returnValue;
    }
    //CHECKSTYLE.ON: IllegalThrows

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AbstractLocatorHandler<?> that = (AbstractLocatorHandler<?>) o;
        return Objects.equals(locator, that.locator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locator);
    }

    protected String getLazyToString() {
        return "Lazy Element";
    }

    public String proxyToString(String elementToString) {
        if (elementToString == null) {
            elementToString = getLazyToString();
        }

        if (locator instanceof WrapsElement) {
            return elementToString;
        }

        return locator + " (" + elementToString + ")";
    }

    @Override
    public String toString() {
        return proxyToString(null);
    }
}
