package org.fluentlenium.core.proxy.plugin.listelement;

import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.proxy.FirstElementLocator;
import org.fluentlenium.core.proxy.LastElementLocator;
import org.fluentlenium.core.proxy.LocatorProxies;
import org.fluentlenium.core.proxy.plugin.LocatorHandlerReturn;
import org.fluentlenium.core.proxy.plugin.LocatorHandlerPlugin;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.Method;
import java.util.List;

public class ListElementAccessorPlugin implements LocatorHandlerPlugin<List<WebElement>> {

    private final ComponentInstantiator instantiator;
    private final Class<?> componentClass;

    public ListElementAccessorPlugin(Class<?> componentClass, ComponentInstantiator instantiator) {
        this.componentClass = componentClass;
        this.instantiator = instantiator;
    }

    @Override
    public LocatorHandlerReturn beforeSearch(Object proxy, Method method, Object[] args, ElementLocator locator) throws Throwable {
        ListElementAccessor annotation = proxy.getClass().getMethod(method.getName(), method.getParameterTypes()).getAnnotation(ListElementAccessor.class);
        if (annotation != null) {
            WebElement webElement;
            if (annotation.first()) {
                webElement = LocatorProxies.createWebElement(new FirstElementLocator(locator));
            } else if (annotation.last()) {
                webElement = LocatorProxies.createWebElement(new LastElementLocator(locator));
            } else if (annotation.index()) {
                webElement = LocatorProxies.createWebElement(new LastElementLocator(locator));
            } else {
                throw new IllegalArgumentException("@ListElementAccessor should have first(), last() or index() defined");
            }

            return new LocatorHandlerReturn(instantiator.newComponent(componentClass, webElement));
        }
        return null;
    }

    @Override
    public LocatorHandlerReturn afterSearch(Object proxy, Method method, Object[] args, ElementLocator locator, List<WebElement> result) throws Throwable {
        return null;
    }

    @Override
    public LocatorHandlerReturn afterInvoke(Object proxy, Method method, Object[] args, ElementLocator locator, List<WebElement> result, Object returnValue) throws Throwable {
        return null;
    }
}
