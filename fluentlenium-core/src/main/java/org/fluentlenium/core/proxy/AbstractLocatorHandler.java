package org.fluentlenium.core.proxy;

import static org.fluentlenium.utils.CollectionUtils.isEmpty;

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
    private static final String DEFAULT_LAZY_ELEMENT_TO_STRING = "Lazy Element";

    private static final int MAX_RETRY = 5;
    private static final int HASH_CODE_SEED = 2048;

    private final List<ProxyElementListener> listeners = new ArrayList<>();

    protected final ElementLocator locator;

    protected HookChainBuilder hookChainBuilder;
    protected List<HookDefinition<?>> hookDefinitions;
    protected List<FluentHook> hooks;

    protected T proxy;
    protected T result;

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
        listeners.forEach(listener -> listener.proxyElementSearch(proxy, locator));
    }

    /**
     * Fire proxy element found event.
     *
     * @param result found element
     */
    protected void fireProxyElementFound(T result) {
        listeners.forEach(listener -> listener.proxyElementFound(proxy, locator, resultToList(result)));
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
            if (loaded() && isStale()) {
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
        if (isEmpty(hookDefinitions)) {
            this.hookChainBuilder = null;
            this.hookDefinitions = null;

            hooks = null;
        } else {
            this.hookChainBuilder = hookChainBuilder;
            this.hookDefinitions = hookDefinitions;

            hooks = hookChainBuilder.build(this::getElement, () -> locator, () -> proxy.toString(), hookDefinitions);
        }
    }

    @Override
    public ElementLocator getLocator() {
        return locator;
    }

    @Override
    public ElementLocator getHookLocator() {
        return !isEmpty(hooks) ? hooks.get(hooks.size() - 1) : locator;
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
        return loaded() && !isStale();
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
    //CHECKSTYLE.ON: IllegalThrows

    private Object invoke(Method method, Object[] args) throws Throwable {
        try {
            return method.invoke(getInvocationTarget(method), args);
        } catch (InvocationTargetException e) {
            // Unwrap the underlying exception
            throw e.getCause();
        }
    }

    /**
     * Get string representation of not already found element.
     *
     * @return string representation of not already found element
     */
    protected String getLazyToString() {
        return DEFAULT_LAZY_ELEMENT_TO_STRING;
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

        return locator instanceof WrapsElement ? elementToString : locator + " (" + elementToString + ")";
    }

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

    @Override
    public String toString() {
        return proxyToString(null);
    }
}
