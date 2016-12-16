package org.fluentlenium.core.proxy;

import com.google.common.base.Supplier;
import org.fluentlenium.core.domain.ElementUtils;
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
@SuppressWarnings("PMD.GodClass")
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

    /**
     * Get declared method.
     *
     * @param declaringClass declaring class
     * @param name           method name
     * @param types          argument types
     * @return method
     */
    protected static Method getMethod(Class<?> declaringClass, String name, Class... types) {
        try {
            return declaringClass.getMethod(name, types);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public boolean addListener(ProxyElementListener listener) {
        return listeners.add(listener);
    }

    @Override
    public boolean removeListener(ProxyElementListener listener) {
        return listeners.remove(listener);
    }

    /**
     * Fire proxy element search event.
     */
    protected void fireProxyElementSearch() {
        for (ProxyElementListener listener : listeners) {
            listener.proxyElementSearch(proxy, locator);
        }
    }

    /**
     * Fire proxy element found event.
     *
     * @param result found element
     */
    protected void fireProxyElementFound(T result) {
        for (ProxyElementListener listener : listeners) {
            listener.proxyElementFound(proxy, locator, resultToList(result));
        }
    }

    /**
     * Convert result to a list of selenium element.
     *
     * @param result found result
     * @return list of selenium element
     */
    protected abstract List<WebElement> resultToList(T result);

    /**
     * Creates a new locator handler.
     *
     * @param locator selenium element locator
     */
    public AbstractLocatorHandler(ElementLocator locator) {
        this.locator = locator;
    }

    /**
     * Set the proxy using this handler.
     *
     * @param proxy proxy using this handler
     */
    public void setProxy(T proxy) {
        this.proxy = proxy;
    }

    /**
     * Get the actual result of the locator.
     *
     * @return result of the locator
     */
    public abstract T getLocatorResultImpl();

    /**
     * Get the actual result of the locator, if result is not defined and not stale.
     * <p>
     * It also raise events.
     *
     * @return result of the locator
     */
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

    /**
     * Get the stale status of the element.
     *
     * @return true if element is stale, false otherwise
     */
    protected abstract boolean isStale();

    /**
     * Get the underlying element.
     *
     * @return underlying element
     */
    protected abstract WebElement getElement();

    /**
     * Builds a {@link NoSuchElementException} with a message matching this locator handler.
     *
     * @return no such element exception
     */
    public NoSuchElementException noSuchElement() {
        return ElementUtils.noSuchElementException(getMessageContext());
    }

    @Override
    public void setHooks(HookChainBuilder hookChainBuilder, List<HookDefinition<?>> hookDefinitions) {
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
    public boolean loaded() {
        return result != null;
    }

    @Override
    public boolean present() {
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
    @SuppressWarnings({"PMD.StdCyclomaticComplexity", "PMD.CyclomaticComplexity", "PMD.ModifiedCyclomaticComplexity",
            "PMD.NPathComplexity"})
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (TO_STRING.equals(method)) {
            return proxyToString(result == null ? null : (String) invoke(method, args));
        }
        if (result == null) {
            if (EQUALS.equals(method)) {
                LocatorHandler otherLocatorHandler = LocatorProxies.getLocatorHandler(args[0]);
                if (otherLocatorHandler != null) {
                    if (!otherLocatorHandler.loaded() || args[0] == null) {
                        return equals(otherLocatorHandler);
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
            LocatorHandler otherLocatorHandler = LocatorProxies.getLocatorHandler(args[0]);
            if (otherLocatorHandler != null && !otherLocatorHandler.loaded()) {
                otherLocatorHandler.now();
                return otherLocatorHandler.equals(this);
            }
        }

        getLocatorResult();

        return invokeWithRetry(method, args);
    }

    //CHECKSTYLE.OFF: IllegalThrows
    private Object invokeWithRetry(Method method, Object[] args) throws Throwable {
        Throwable lastThrowable = null;
        for (int i = 0; i < MAX_RETRY; i++) {
            try {
                return invoke(method, args);
            } catch (StaleElementReferenceException e) {
                lastThrowable = e;
                reset();
                getLocatorResult(); // Reload the stale element
            }
        }

        throw lastThrowable;
    }

    private Object invoke(Method method, Object[] args) throws Throwable {
        Object returnValue;
        try {
            returnValue = method.invoke(getInvocationTarget(method), args);
        } catch (InvocationTargetException e) {
            // Unwrap the underlying exception
            throw e.getCause();
        }
        return returnValue;
    }
    //CHECKSTYLE.ON: IllegalThrows

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AbstractLocatorHandler<?> that = (AbstractLocatorHandler<?>) obj;
        return Objects.equals(locator, that.locator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locator);
    }

    /**
     * Get string representation of not already found element.
     *
     * @return string representation of not already found element
     */
    protected String getLazyToString() {
        return "Lazy Element";
    }

    /**
     * Get string representation of the proxy
     *
     * @param elementToString string representation of the underlying element
     * @return string representation of the proxy
     */
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
