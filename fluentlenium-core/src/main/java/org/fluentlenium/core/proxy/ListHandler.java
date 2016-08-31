package org.fluentlenium.core.proxy;

import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.WrapsElements;
import org.fluentlenium.core.hook.FluentHook;
import org.fluentlenium.core.hook.HookChainBuilder;
import org.fluentlenium.core.hook.HookDefinition;
import org.fluentlenium.core.proxy.plugin.LocatorHandlerPlugin;
import org.fluentlenium.core.proxy.plugin.LocatorHandlerReturn;
import org.fluentlenium.core.proxy.plugin.listelement.ListElementAccessor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ListHandler extends AbstractLocatorHandler<List<WebElement>> {
    private static final Method GET_WRAPPED_ELEMENTS = getMethod(WrapsElements.class, "getWrappedElements");

    public ListHandler(ElementLocator locator) {
        super(locator);
        if (this.locator instanceof WrapsElements) {
            this.result = wrapElements(((WrapsElements) this.locator).getWrappedElements());
            this.loaded = true;
        }
    }

    @Override
    public ElementLocator getLocator() {
        return locator;
    }

    @Override
    protected WebElement getElement() {
        return null;
    }

    @Override
    protected List<WebElement> getInvocationTarget() {
        return result;
    }

    @Override
    public boolean isPresent() {
        return super.isPresent() && result.size() > 0;
    }

    @Override
    public List<WebElement> getLocatorResultImpl() {
        fireProxyElementSearch(proxy, locator);
        List<WebElement> foundElements = getHookLocator().findElements();
        return wrapElements(foundElements);
    }

    protected List<WebElement> wrapElements(List<WebElement> foundElements) {
        List<WebElement> proxyElements = new ArrayList<>();
        for (WebElement element : foundElements) {
            WebElement proxyElement = LocatorProxies.createWebElement(new ElementLocatorAdapter(element));
            LocatorProxies.setHooks(proxyElement, hookChainBuilder, hookDefinitions);
            fireProxyElementFound(proxy, locator, proxyElement);
            proxyElements.add(proxyElement);
        }
        return proxyElements;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (GET_WRAPPED_ELEMENTS.equals(method)) {
            return loaded ? getLocatorResult() : proxy;
        }
        return super.invoke(proxy, method, args);
    }
}
