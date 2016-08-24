package org.fluentlenium.core.proxy;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.FluentListImpl;
import org.fluentlenium.core.hook.FluentHook;
import org.fluentlenium.core.hook.HookChainBuilder;
import org.fluentlenium.core.hook.HookDefinition;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

abstract class AbstractListHandler<T, L extends List<T>> extends AbstractLocatorHandler implements InvocationHandler, LocatorHandler<L> {
    private static final Method EQUALS = getMethod(Object.class, "equals", Object.class);
    private static final Method HASH_CODE = getMethod(Object.class, "hashCode");
    private static final Method TO_STRING = getMethod(Object.class, "toString");

    private List<FluentHook> hooks;
    private L proxy;

    private static Method getMethod(Class<?> declaringClass, String name, Class... types) {
        try {
            return declaringClass.getMethod(name, types);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private L list;

    private final ElementLocator locator;

    private final Class<T> componentClass;

    private final ComponentInstantiator instantiator;

    private HookChainBuilder hookChainBuilder;
    private List<HookDefinition<?>> hookDefinitions;

    public AbstractListHandler(ElementLocator locator,
                               Class<T> componentClass, ComponentInstantiator instantiator, HookChainBuilder hookChainBuilder) {
        this.locator = locator;
        this.componentClass = componentClass;;
        this.instantiator = instantiator;
        this.hookChainBuilder = hookChainBuilder;
    }

    protected abstract L buildList(List<WebElement> elements);

    public Class<T> getComponentClass() {
        return componentClass;
    }

    public ComponentInstantiator getInstantiator() {
        return instantiator;
    }

    public HookChainBuilder getHookChainBuilder() {
        return hookChainBuilder;
    }

    @Override
    public ElementLocator getLocator() {
        return locator;
    }

    @Override
    public ElementLocator getHookLocator() {
        if (hooks != null && hooks.size() > 0) {
            return hooks.get(hooks.size()-1);
        }
        return locator;
    }

    @Override
    public L getHookLocatorResult() {
        return list;
    }

    @Override
    public boolean isLoaded() {
        return list != null;
    }

    @Override
    public void reset() {
        list = null;
    }

    @Override
    public void now() {
        getLocatorResult();
    }

    @Override
    public void setHooks(HookChainBuilder hookChainBuilder, List<HookDefinition<?>> hookDefinitions) {
        this.hookChainBuilder = hookChainBuilder;
        this.hookDefinitions = hookDefinitions;

        if (hookDefinitions == null || hookDefinitions.size() == 0) {
            hooks = null;
        } else {
            hooks = hookChainBuilder.build(new Supplier<WebElement>() {
                @Override
                public WebElement get() {
                    return null;
                }
            }, new Supplier<ElementLocator>() {
                @Override
                public ElementLocator get() {
                    return locator;
                }
            }, hookDefinitions);
        }

        if (list != null) {
            for (T component : list) {
                LocatorHandler handler = LocatorProxies.getLocatorHandler(component);
                handler.setHooks(hookChainBuilder, hookDefinitions);
            }
        }
    }

    protected Function<WebElement, T> getTransformer() {
        if (hookDefinitions != null && hookDefinitions.size() > 0) {
            return new Function<WebElement, T>() {
                @Override
                public T apply(WebElement input) {
                    return LocatorProxies.createComponent(input, componentClass, instantiator, hookChainBuilder, hookDefinitions);
                }
            };
        } else {
            return new Function<WebElement, T>() {
                @Override
                public T apply(WebElement input) {
                    return LocatorProxies.createComponent(input, componentClass, instantiator);
                }
            };
        }
    }

    @Override
    public synchronized L getLocatorResult() {
        if (list == null) {
            fireProxyElementSearch(proxy, locator);
            List<WebElement> elements = getHookLocator().findElements();
            for (WebElement element : elements) {
                fireProxyElementFound(proxy, locator, element);
            }
            list = buildList(elements);
        }
        return list;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (list == null) {
            if (TO_STRING.equals(method)) {
                return "Proxy List for: " + locator;
            }
            if (EQUALS.equals(method)) {
                return this.equals(LocatorProxies.getLocatorHandler(args[0]));
            }
            if (HASH_CODE.equals(method)) {
                return FluentListHandler.class.hashCode() + locator.hashCode();
            }

            ListElementAccessor annotation = FluentListImpl.class.getMethod(method.getName(), method.getParameterTypes()).getAnnotation(ListElementAccessor.class);
            if (annotation != null) {
                if (annotation.first()) {
                    return LocatorProxies.createComponent(new FirstElementLocator(locator), componentClass, instantiator);
                } else if (annotation.last()) {
                    return LocatorProxies.createComponent(new LastElementLocator(locator), componentClass, instantiator);
                } else if (annotation.index()) {
                    return LocatorProxies.createComponent(new AtIndexElementLocator(locator, (int) args[0]), componentClass, instantiator);
                } else {
                    throw new IllegalArgumentException("@ListElementAccessor should have first(), last() or index() defined");
                }
            }
        }

        getLocatorResult();
        try {
            return method.invoke(list, args);
        } catch (InvocationTargetException e) {
            // Unwrap the underlying exception
            throw e.getCause();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractListHandler that = (AbstractListHandler) o;

        return locator != null ? locator.equals(that.locator) : that.locator == null;
    }

    @Override
    public int hashCode() {
        return locator != null ? locator.hashCode() : 0;
    }


    public void setProxy(L proxy) {
        this.proxy = proxy;
    }
}
