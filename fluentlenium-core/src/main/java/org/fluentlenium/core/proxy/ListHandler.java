package org.fluentlenium.core.proxy;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class ListHandler<T> implements InvocationHandler {
    private final ElementLocator elementLocator;

    private List<T> list;

    private final Class<T> componentClass;

    private final ComponentInstantiator instantiator;

    public ListHandler(ElementLocator elementLocator,
                       Class<T> componentClass, ComponentInstantiator instantiator) {
        this.elementLocator = elementLocator;
        this.componentClass = componentClass;
        this.instantiator = instantiator;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        synchronized (this) {
            if (list == null) {
                List<WebElement> elements = elementLocator.findElements();
                list = new ArrayList<T>(FluentIterable.from(elements).transform(new Function<WebElement, T>() {
                    @Override
                    public T apply(WebElement input) {
                        return instantiator.newComponent(componentClass, input);
                    }
                }).toList());
            }
        }
        try {
            return method.invoke(list, args);
        } catch (InvocationTargetException e) {
            // Unwrap the underlying exception
            throw e.getCause();
        }
    }
}
