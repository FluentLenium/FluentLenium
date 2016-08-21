package org.fluentlenium.core.proxy;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentListImpl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

class FluentListHandler<T extends FluentWebElement> implements InvocationHandler {
    private static final Method EQUALS = getMethod(Object.class, "equals", Object.class);
    private static final Method HASH_CODE = getMethod(Object.class, "hashCode");
    private static final Method TO_STRING = getMethod(Object.class, "toString");

    private static Method getMethod(Class<?> declaringClass, String name, Class... types) {
        try {
            return declaringClass.getMethod(name, types);
        }
        catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private final ElementLocator locator;

    private FluentList<T> list;

    private final Class<T> componentClass;

    private final ComponentInstantiator instantiator;

    public FluentListHandler(ElementLocator locator, Class<T> componentClass, ComponentInstantiator instantiator) {
        this.locator = locator;
        this.componentClass = componentClass;
        this.instantiator = instantiator;
    }

    public synchronized <E extends FluentWebElement> FluentList<E> getOrFindList(FluentList<E> proxy) {
        if (list == null) {
            List<WebElement> elements = locator.findElements();
            list = new FluentListImpl(this.componentClass, instantiator, FluentIterable.from(elements).transform(new Function<WebElement, T>() {

                @Override
                public T apply(WebElement input) {
                    return instantiator.newComponent(componentClass, input);
                }
            }).toList());
        }
        return (FluentList<E>) list;
    }

    public ElementLocator getLocator() {
        return locator;
    }

    public void reset() {
        list = null;
    }

    public boolean isLoaded() {
        return list != null;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (list == null) {
            if (TO_STRING.equals(method)) {
                return "Proxy FluentList for: " + locator;
            }
            if (EQUALS.equals(method)) {
                return this.equals(Proxies.getComponentHandler(args[0]));
            }
            if (HASH_CODE.equals(method)) {
                return FluentListHandler.class.hashCode() + locator.hashCode();
            }

            ListElementAccessor annotation = FluentListImpl.class.getMethod(method.getName(), method.getParameterTypes()).getAnnotation(ListElementAccessor.class);
            if (annotation != null) {
                if (annotation.first()) {
                    return Proxies.createComponent(new FirstElementLocator(locator), componentClass, instantiator);
                } else if (annotation.last()) {
                    return Proxies.createComponent(new LastElementLocator(locator), componentClass, instantiator);
                } else if (annotation.index()) {
                    return Proxies.createComponent(new AtIndexElementLocator(locator, (int)args[0]), componentClass, instantiator);
                } else {
                    throw new IllegalArgumentException("@ListElementAccessor should have first(), last() or index() defined");
                }
            }
        }

        getOrFindList((FluentList) proxy);
        try {
            return method.invoke(list, args);
        } catch (InvocationTargetException e) {
            // Unwrap the underlying exception
            throw e.getCause();
        }
    }


}
