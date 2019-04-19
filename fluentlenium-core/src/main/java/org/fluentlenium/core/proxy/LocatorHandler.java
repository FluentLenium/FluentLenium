package org.fluentlenium.core.proxy;

import org.fluentlenium.core.domain.ElementUtils;
import org.fluentlenium.core.hook.HookChainBuilder;
import org.fluentlenium.core.hook.HookDefinition;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Public interface to control handler for proxies of {@link List} of
 * {@link org.fluentlenium.core.domain.FluentWebElement} and {@link org.fluentlenium.core.domain.FluentWebElement}.
 *
 * @param <T> type of the result retrieved by the element locator
 */
public interface LocatorHandler<T> extends LocatorStatusHandler {
    /**
     * Retrieve the element locator used by this proxy, without any hook applied.
     *
     * @return element locator
     */
    ElementLocator getLocator();

    /**
     * Get the result retrieved by the element locator, without any hook applied.
     *
     * @return results of the element locator
     */
    T getLocatorResult();

    /**
     * Retrieve the element locator used by this proxy, with hooks applied.
     *
     * @return element locator wrapped with hooks
     */
    ElementLocator getHookLocator();

    /**
     * Retrieve the invocation target of this proxy handler.
     *
     * @param method method to invoke
     * @return invocation target
     */
    T getInvocationTarget(Method method);

    /**
     * Apply this hook list.
     *
     * @param hookChainBuilder hook chain builder
     * @param hookDefinitions  hook definitions
     */
    void setHooks(HookChainBuilder hookChainBuilder, List<HookDefinition<?>> hookDefinitions);

    /**
     * {@inheritDoc}
     */
    @Override
    default void now() {
        getLocatorResult();
    }

    /**
     * Add a listener for this locator handler.
     *
     * @param listener listener to add, which will be notified when result is searched and found
     * @return true if the listener was added, false otherwise
     */
    boolean addListener(ProxyElementListener listener);

    /**
     * Removes a proxy element listener.
     *
     * @param listener listener to remove
     * @return true if the listener was removed, false otherwise
     */
    boolean removeListener(ProxyElementListener listener);

    /**
     * Build a {@link NoSuchElementException} with message from this locator.
     *
     * @return Exception with not present message
     */
    default NoSuchElementException noSuchElement() {
        return ElementUtils.noSuchElementException(getMessageContext());
    }

    /**
     * Retrieve the message context from this proxy locator.
     *
     * @return message context
     */
    String getMessageContext();
}
