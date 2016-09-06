package org.fluentlenium.core.proxy;

import org.fluentlenium.core.hook.HookChainBuilder;
import org.fluentlenium.core.hook.HookDefinition;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.List;

public interface LocatorHandler<T> {
    /**
     * Locator used by this proxy, without any hook applied.
     *
     * @return
     */
    ElementLocator getLocator();

    /**
     * Result retrieved by the locator, without any hook applied.
     *
     * @return
     */
    T getLocatorResult();

    /**
     * Locator used by this proxy, with all hooks applied.
     *
     * @return
     */
    ElementLocator getHookLocator();

    /**
     * Element used by this proxy, with all hooks applied.
     */
    WebElement getHookElement();

    /**
     * Set hooks of this locator handler.
     *
     * @param hookChainBuilder
     * @param hookDefinitions
     */
    void setHooks(HookChainBuilder hookChainBuilder, List<HookDefinition<?>> hookDefinitions);

    /**
     * Check if this handler has loaded it's result.
     *
     * @return
     */
    boolean isLoaded();

    /**
     * Reset the loaded data.
     */
    void reset();

    /**
     * Force loading of the results.
     */
    void now();

    /**
     * Is the result present or absent.
     *
     * @return
     */
    boolean isPresent();

    /**
     * Add a proxy element listener.
     *
     * @param listener
     * @return
     */
    boolean addListener(ProxyElementListener listener);

    /**
     * Removes a proxy element listener.
     *
     * @param listener
     * @return
     */
    boolean removeListener(ProxyElementListener listener);
    
}
