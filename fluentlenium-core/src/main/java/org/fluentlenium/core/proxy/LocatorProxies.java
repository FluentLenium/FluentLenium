package org.fluentlenium.core.proxy;

import lombok.experimental.UtilityClass;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.hook.HookChainBuilder;
import org.fluentlenium.core.hook.HookDefinition;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
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
@UtilityClass
public class LocatorProxies {
    public LocatorHandler getLocatorHandler(Object proxy) {
        if (Proxy.isProxyClass(proxy.getClass())) {
            InvocationHandler proxyHandler = Proxy.getInvocationHandler(proxy);
            if (proxyHandler instanceof LocatorHandler) {
                return ((LocatorHandler) proxyHandler);
            }
        }
        return null;
    }

    public <T> T getLocatorResult(T proxy) {
        LocatorHandler<?> componentHandler = getLocatorHandler(proxy);
        if (componentHandler != null) {
            return (T) componentHandler.getLocatorResult();
        }
        return proxy;
    }

    public void reset(Object proxy) {
        LocatorHandler handler = getLocatorHandler(proxy);
        if (handler != null) {
            handler.reset();
        }
    }

    public boolean isPresent(Object proxy) {
        try {
            now(proxy);
            return true;
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    public static void now(Object proxy) {
        LocatorHandler<?> handler = getLocatorHandler(proxy);
        if (handler != null) {
            handler.now();
        }
    }

    public boolean isLoaded(Object proxy) {
        LocatorHandler handler = getLocatorHandler(proxy);
        if (handler != null) {
            return handler.isLoaded();
        }
        return true;
    }

    public boolean addProxyListener(WebElement proxy, ProxyElementListener listener) {
        if (Proxy.isProxyClass(proxy.getClass())) {
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(proxy);
            if (invocationHandler instanceof ComponentHandler) {
                return ((ComponentHandler) invocationHandler).addListener(listener);
            }
        }
        return false;
    }

    public boolean removeProxyListener(WebElement proxy, ProxyElementListener listener) {
        if (Proxy.isProxyClass(proxy.getClass())) {
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(proxy);
            if (invocationHandler instanceof ComponentHandler) {
                return ((ComponentHandler) invocationHandler).removeListener(listener);
            }
        }
        return false;
    }

    public WebElement createWebElement(ElementLocator locator) {
        final ComponentHandler handler = new ComponentHandler(locator);
        WebElement proxy = (WebElement) Proxy.newProxyInstance(locator.getClass().getClassLoader(), new Class[]{WebElement.class, Locatable.class, WrapsElement.class}, handler);
        handler.setProxy(proxy);
        return proxy;
    }

    public <T> T createComponent(ElementLocator locator, Class<T> componentClass, ComponentInstantiator instantiator) {
        return instantiator.newComponent(componentClass, createWebElement(locator));
    }

    public <T> T createComponent(WebElement element, Class<T> componentClass, ComponentInstantiator instantiator) {
        return createComponent(new InstanceElementLocator(element), componentClass, instantiator);
    }

    public static <T> T createComponent(ElementLocator locator, Class<T> componentClass, ComponentInstantiator instantiator, HookChainBuilder hookChainBuilder, List<HookDefinition<?>> hookDefinitions) {
        WebElement proxy = createWebElement(locator);
        setHooks(hookChainBuilder, proxy, hookDefinitions);
        return instantiator.newComponent(componentClass, proxy);
    }

    public static <T> T createComponent(WebElement element, Class<T> componentClass, ComponentInstantiator instantiator, HookChainBuilder hookChainBuilder, List<HookDefinition<?>> hookDefinitions) {
        return createComponent(new InstanceElementLocator(element), componentClass, instantiator, hookChainBuilder, hookDefinitions);
    }

    public <T extends FluentWebElement> FluentList<T> createFluentList(ElementLocator locator, Class<T> componentClass, ComponentInstantiator instantiator, HookChainBuilder hookChainBuilder) {
        final FluentListHandler handler = new FluentListHandler(locator, componentClass, instantiator, hookChainBuilder);
        FluentList<T> proxy = (FluentList<T>) Proxy.newProxyInstance(
                locator.getClass().getClassLoader(), new Class[]{FluentList.class}, handler);
        handler.setProxy(proxy);
        return proxy;
    }

    public <T> List<T> createComponentList(ElementLocator locator, Class<T> componentClass, ComponentInstantiator instantiator, HookChainBuilder hookChainBuilder) {
        final ListHandler handler = new ListHandler(locator, componentClass, instantiator, hookChainBuilder);
        List<T> proxy = (List<T>) Proxy.newProxyInstance(
                locator.getClass().getClassLoader(), new Class[]{List.class}, handler);
        handler.setProxy(proxy);
        return proxy;
    }

    public void setHooks(HookChainBuilder hookChainBuilder, Object element, List<HookDefinition<?>> hookDefinitions) {
        LocatorHandler<?> componentHandler = getLocatorHandler(element);
        componentHandler.setHooks(hookChainBuilder, hookDefinitions);
    }
}
