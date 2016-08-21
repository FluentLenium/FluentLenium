package org.fluentlenium.core.proxy;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import lombok.experimental.UtilityClass;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentListImpl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to create proxies of WebElement, Component, FluentList and List of Components.
 */
@UtilityClass
public class Proxies {
    public WebElement createWebElement(ElementLocator locator) {
        final InvocationHandler handler = new LocatingElementHandler(locator);
        WebElement proxy = (WebElement) Proxy.newProxyInstance(locator.getClass().getClassLoader(), new Class[]{WebElement.class, Locatable.class}, handler);
        return proxy;
    }

    public <T> T createComponent(ElementLocator locator, Class<T> componentClass, ComponentInstantiator instantiator) {
        return instantiator.newComponent(componentClass, createWebElement(locator));
    }

    public <T extends FluentWebElement> FluentList<T> createFluentList(ElementLocator locator, Class<T> componentClass, ComponentInstantiator instantiator) {
        final InvocationHandler handler = new FluentListInvocationHandler(locator, componentClass, instantiator);
        return (FluentList<T>) Proxy.newProxyInstance(
                locator.getClass().getClassLoader(), new Class[]{FluentList.class}, handler);
    }

    public <T> List<T> createComponentList(ElementLocator locator, Class<T> componentClass, ComponentInstantiator instantiator) {
        final InvocationHandler handler = new ArrayListInvocationHandler(locator, componentClass, instantiator);
        return (List<T>) Proxy.newProxyInstance(
                locator.getClass().getClassLoader(), new Class[]{List.class}, handler);
    }

    private class ArrayListInvocationHandler<T> implements InvocationHandler {

        private final ElementLocator elementLocator;

        private final Class<T> componentClass;

        private final ComponentInstantiator instantiator;

        public ArrayListInvocationHandler(ElementLocator elementLocator,
                                          Class<T> componentClass, ComponentInstantiator instantiator) {
            this.elementLocator = elementLocator;
            this.componentClass = componentClass;
            this.instantiator = instantiator;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            List<WebElement> elements = elementLocator.findElements();
            List<T> list = new ArrayList<T>(FluentIterable.from(elements).transform(new Function<WebElement, T>() {
                @Override
                public T apply(WebElement input) {
                    return instantiator.newComponent(componentClass, input);
                }
            }).toList());
            try {
                return method.invoke(list, args);
            } catch (InvocationTargetException e) {
                // Unwrap the underlying exception
                throw e.getCause();
            }
        }
    }

    private class FluentListInvocationHandler<T extends FluentWebElement> implements InvocationHandler {

        private final ElementLocator elementLocator;

        private final Class<T> fluentElementClass;

        private final ComponentInstantiator instantiator;

        public FluentListInvocationHandler(ElementLocator elementLocator, Class<T> fluentElementClass, ComponentInstantiator instantiator) {
            this.elementLocator = elementLocator;
            this.fluentElementClass = fluentElementClass;
            this.instantiator = instantiator;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            List<WebElement> elements = elementLocator.findElements();
            FluentListImpl list = new FluentListImpl(FluentIterable.from(elements).transform(new Function<WebElement, T>() {

                @Override
                public T apply(WebElement input) {
                    return instantiator.newComponent(fluentElementClass, input);
                }
            }).toList());
            try {
                return method.invoke(list, args);
            } catch (InvocationTargetException e) {
                // Unwrap the underlying exception
                throw e.getCause();
            }
        }
    }
}
