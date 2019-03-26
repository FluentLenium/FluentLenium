package org.fluentlenium.core.proxy;

import static org.fluentlenium.utils.CollectionUtils.isEmpty;
import static org.fluentlenium.utils.ReflectionUtils.getMethod;

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
    protected final ProxyResultHolder<T> proxyResultHolder;

    protected HookChainBuilder hookChainBuilder;
    protected List<HookDefinition<?>> hookDefinitions;
    protected List<FluentHook> hooks;


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
        listeners.forEach(listener -> listener.proxyElementSearch(proxyResultHolder.getProxy(), locator));
    }

    /**
     * Fire proxy element found event.
     *
     * @param result found element
     */
    protected void fireProxyElementFound(T result) {
        listeners.forEach(listener -> listener.proxyElementFound(proxyResultHolder.getProxy(), locator, resultToList(result)));
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
        proxyResultHolder = new ProxyResultHolder<>();
    }

    /**
     * Set the proxy using this handler.
     *
     * @param proxy proxy using this handler
     */
    public void setProxy(T proxy) {
        proxyResultHolder.setProxy(proxy);
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
    @Override
    public T getLocatorResult() {
        synchronized (this) {
            if (loaded() && isStale()) {
                proxyResultHolder.setResult(null);
            }
            if (!loaded()) {
                fireProxyElementSearch();
                proxyResultHolder.setResult(getLocatorResultImpl());
                fireProxyElementFound(proxyResultHolder.getResult());
            }
            return proxyResultHolder.getResult();
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

    @Override
    public void setHooks(HookChainBuilder hookChainBuilder, List<HookDefinition<?>> hookDefinitions) {
        if (isEmpty(hookDefinitions)) {
            this.hookChainBuilder = null;
            this.hookDefinitions = null;

            hooks = null;
        } else {
            this.hookChainBuilder = hookChainBuilder;
            this.hookDefinitions = hookDefinitions;

            hooks = hookChainBuilder.build(this::getElement, () -> locator, () -> proxyResultHolder.getProxy().toString(), hookDefinitions);
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
        return proxyResultHolder.isResultLoaded();
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
        proxyResultHolder.setResult(null);
    }

    @Override
    @SuppressWarnings({"PMD.StdCyclomaticComplexity", "PMD.CyclomaticComplexity", "PMD.ModifiedCyclomaticComplexity",
            "PMD.NPathComplexity"})
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (TO_STRING.equals(method)) {
            return proxyToString(!loaded() ? null : (String) invoke(method, args));
        }
        if (!loaded()) {
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
