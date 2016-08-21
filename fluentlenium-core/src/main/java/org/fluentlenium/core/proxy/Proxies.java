package org.fluentlenium.core.proxy;

import lombok.experimental.UtilityClass;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * Utility class to create proxies of WebElement, Component, FluentList and List of Components.
 */
@UtilityClass
public class Proxies {
    public ComponentHandler getComponentHandler(Object proxy) {
        if (Proxy.isProxyClass(proxy.getClass())) {
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(proxy);
            if (invocationHandler instanceof ComponentHandler) {
                return ((ComponentHandler)invocationHandler);
            }
        }
        return null;
    }


    private <E extends FluentWebElement> FluentListHandler getFluentListHandler(FluentList<E> proxy) {
        if (Proxy.isProxyClass(proxy.getClass())) {
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(proxy);
            if (invocationHandler instanceof FluentListHandler) {
                return ((FluentListHandler)invocationHandler);
            }
        }
        return null;
    }

    public WebElement getElement(WebElement proxy) {
        ComponentHandler componentHandler = getComponentHandler(proxy);
        if (componentHandler != null) {
            return componentHandler.getOrFindElement(proxy);
        }
        return proxy;
    }

    public <E extends FluentWebElement> FluentList<E> getList(FluentList<E> proxy) {
        FluentListHandler fluentListHandler = getFluentListHandler(proxy);
        if (fluentListHandler != null) {
            return fluentListHandler.getOrFindList(proxy);
        }
        return proxy;
    }

    public ElementLocator getLocator(WebElement proxy) {
        ComponentHandler componentHandler = getComponentHandler(proxy);
        if (componentHandler != null) {
            return componentHandler.getLocator();
        }
        return null;
    }

    public ElementLocator getLocator(FluentList<?> proxy) {
        FluentListHandler fluentListHandler = getFluentListHandler(proxy);
        if (fluentListHandler != null) {
            return fluentListHandler.getLocator();
        }
        return null;
    }

    public void reset(WebElement proxy) {
        ComponentHandler componentHandler = getComponentHandler(proxy);
        if (componentHandler != null) {
            componentHandler.reset();
        }
    }

    public void reset(FluentList<?> proxy) {
        FluentListHandler componentHandler = getFluentListHandler(proxy);
        if (componentHandler != null) {
            componentHandler.reset();
        }
    }

    public boolean isPresent(WebElement proxy) {
        try {
            now(proxy);
            return true;
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    public boolean isPresent(FluentList<?> proxy) {
        try {
            now(proxy);
            return true;
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    public static void now(WebElement proxy) {
        ComponentHandler componentHandler = getComponentHandler(proxy);
        if (componentHandler != null) {
            componentHandler.getOrFindElement(proxy);
        }
    }


    public <E extends FluentWebElement> void now(FluentList<E> listProxy) {
        FluentListHandler fluentListHandler = getFluentListHandler(listProxy);
        if (fluentListHandler != null) {
            fluentListHandler.getOrFindList(listProxy);
        }
    }

    public boolean isLoaded(WebElement proxy) {
        ComponentHandler componentHandler = getComponentHandler(proxy);
        if (componentHandler != null) {
            return componentHandler.isLoaded();
        }
        return true;
    }

    public boolean isLoaded(FluentList<?> proxy) {
        FluentListHandler fluentListHandler = getFluentListHandler(proxy);
        if (fluentListHandler != null) {
            return fluentListHandler.isLoaded();
        }
        return true;
    }

    public boolean addProxyListener(WebElement proxy, ProxyElementListener listener) {
        if (Proxy.isProxyClass(proxy.getClass())) {
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(proxy);
            if (invocationHandler instanceof ComponentHandler) {
                return ((ComponentHandler)invocationHandler).addListener(listener);
            }
        }
        return false;
    }

    public boolean removeProxyListener(WebElement proxy, ProxyElementListener listener) {
        if (Proxy.isProxyClass(proxy.getClass())) {
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(proxy);
            if (invocationHandler instanceof ComponentHandler) {
                return ((ComponentHandler)invocationHandler).removeListener(listener);
            }
        }
        return false;
    }

    public WebElement createWebElement(ElementLocator locator) {
        final InvocationHandler handler = new ComponentHandler(locator);
        WebElement proxy = (WebElement) Proxy.newProxyInstance(locator.getClass().getClassLoader(), new Class[]{WebElement.class, Locatable.class, WrapsElement.class}, handler);
        return proxy;
    }

    public <T> T createComponent(ElementLocator locator, Class<T> componentClass, ComponentInstantiator instantiator) {
        return instantiator.newComponent(componentClass, createWebElement(locator));
    }

    public <T extends FluentWebElement> FluentList<T> createFluentList(ElementLocator locator, Class<T> componentClass, ComponentInstantiator instantiator) {
        final InvocationHandler handler = new FluentListHandler(locator, componentClass, instantiator);
        return (FluentList<T>) Proxy.newProxyInstance(
                locator.getClass().getClassLoader(), new Class[]{FluentList.class}, handler);
    }

    public <T> List<T> createComponentList(ElementLocator locator, Class<T> componentClass, ComponentInstantiator instantiator) {
        final InvocationHandler handler = new ListHandler(locator, componentClass, instantiator);
        return (List<T>) Proxy.newProxyInstance(
                locator.getClass().getClassLoader(), new Class[]{List.class}, handler);
    }

}
