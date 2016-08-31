package org.fluentlenium.core.proxy.plugin;

import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.Method;

public interface LocatorHandlerPlugin<T> {
    LocatorHandlerReturn beforeSearch(Object proxy, Method method, Object[] args, ElementLocator locator) throws Throwable;

    LocatorHandlerReturn afterSearch(Object proxy, Method method, Object[] args, ElementLocator locator, T result) throws Throwable;

    LocatorHandlerReturn afterInvoke(Object proxy, Method method, Object[] args, ElementLocator locator, T result, Object returnValue) throws Throwable;
}
