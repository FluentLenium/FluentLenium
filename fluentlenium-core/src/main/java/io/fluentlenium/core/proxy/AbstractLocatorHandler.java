package io.fluentlenium.core.proxy;

import io.fluentlenium.core.hook.FluentHook;
import io.fluentlenium.core.hook.HookChainBuilder;
import io.fluentlenium.core.hook.HookDefinition;
import io.fluentlenium.utils.CollectionUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract proxy handler supporting lazy loading and hooks on {@link WebElement}.
 * <p>
 * Contains the locator handling related logic.
 *
 * @param <T> type of underlying element or component
 */
public abstract class AbstractLocatorHandler<T> implements LocatorHandler<T> {
    private static final String DEFAULT_LAZY_ELEMENT_TO_STRING = "Lazy Element";
    private final List<ProxyElementListener> listeners = new ArrayList<>();
    protected final ElementLocator locator;
    protected HookChainBuilder hookChainBuilder;
    protected List<HookDefinition<?>> hookDefinitions;
    protected List<FluentHook> hooks;
    protected T proxy;
    protected T result;

    /**
     * Creates a new locator handler.
     *
     * @param locator selenium element locator
     */
    public AbstractLocatorHandler(ElementLocator locator) {
        this.locator = locator;
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
                result = null;
            }
            if (!loaded()) {
                fireProxyElementSearch();
                result = getLocatorResultImpl();
                fireProxyElementFound(result);
            }
            return result;
        }
    }

    @Override
    public void setHooks(HookChainBuilder hookChainBuilder, List<HookDefinition<?>> hookDefinitions) {
        if (CollectionUtils.isEmpty(hookDefinitions)) {
            this.hookChainBuilder = null;
            this.hookDefinitions = null;
            hooks = null;
        } else {
            this.hookChainBuilder = hookChainBuilder;
            this.hookDefinitions = hookDefinitions;
            hooks = hookChainBuilder
                    .build(this::getElement, () -> locator, () -> proxy.toString(), hookDefinitions);
        }
    }

    @Override
    public ElementLocator getLocator() {
        return locator;
    }

    @Override
    public ElementLocator getHookLocator() {
        return CollectionUtils.isEmpty(hooks) ? locator : hooks.get(hooks.size() - 1);
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

    /**
     * Set the proxy using this handler.
     *
     * @param proxy proxy using this handler
     */
    public void setProxy(T proxy) {
        this.proxy = proxy;
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
     * Get the actual result of the locator.
     *
     * @return result of the locator
     */
    public abstract T getLocatorResultImpl();

    /**
     * Convert result to a list of selenium element.
     *
     * @param result found result
     * @return list of selenium element
     */
    protected abstract List<WebElement> resultToList(T result);

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
}
