package org.fluentlenium.core.proxy;

import com.google.common.base.Supplier;
import org.fluentlenium.core.hook.FluentHook;
import org.fluentlenium.core.hook.HookChainBuilder;
import org.fluentlenium.core.hook.HookDefinition;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class ComponentHandler extends AbstractLocatorHandler implements InvocationHandler, LocatorHandler<WebElement> {
    private static final Method EQUALS = getMethod(Object.class, "equals", Object.class);
    private static final Method HASH_CODE = getMethod(Object.class, "hashCode");
    private static final Method TO_STRING = getMethod(Object.class, "toString");
    private static final Method GET_WRAPPED_ELEMENT = getMethod(WrapsElement.class, "getWrappedElement");

    private List<FluentHook> hooks;
    private WebElement proxy;

    private static Method getMethod(Class<?> declaringClass, String name, Class... types) {
        try {
            return declaringClass.getMethod(name, types);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private final ElementLocator locator;

    private WebElement element;

    private List<ProxyElementListener> listeners = new ArrayList<>();

    public ComponentHandler(ElementLocator locator) {
        this.locator = locator;
        if (this.locator instanceof WrapsElement) {
            this.element = ((WrapsElement) this.locator).getWrappedElement();
        }
    }

    public void setProxy(WebElement proxy) {
        this.proxy = proxy;
    }

    @Override
    public ElementLocator getLocator() {
        return locator;
    }


    @Override
    public WebElement getLocatorResult() {
        if (element == null) {
            fireProxyElementSearch(proxy, locator);
            element = getHookLocator().findElement();
            fireProxyElementFound(proxy, locator, element);
        }
        return element;
    }

    @Override
    public WebElement getHookLocatorResult() {
        if (hooks != null && hooks.size() > 0) {
            return hooks.get(hooks.size()-1);
        }
        return element;
    }

    @Override
    public ElementLocator getHookLocator() {
        if (hooks != null && hooks.size() > 0) {
            return hooks.get(hooks.size()-1);
        }
        return locator;
    }

    @Override
    public synchronized boolean isLoaded() {
        return element != null;
    }

    @Override
    public void now() {
        getLocatorResult();
    }

    @Override
    public synchronized void reset() {
        if (this.locator != null) {
            this.element = null;
        }
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (element == null) {
            if (GET_WRAPPED_ELEMENT.equals(method)) {
                return proxy;
            }
            if (TO_STRING.equals(method)) {
                return "Proxy Component for: " + locator;
            }
            if (EQUALS.equals(method)) {
                LocatorHandler locatorHandler = LocatorProxies.getLocatorHandler(args[0]);
                if (locatorHandler != null) {
                    return this.equals(locatorHandler);
                }
                // Loading element if equals is called with a non ComponentHandler proxy parameter
            }
            if (HASH_CODE.equals(method)) {
                return ComponentHandler.class.hashCode() + locator.hashCode();
            }
        }

        getLocatorResult();

        if (EQUALS.equals(method) && LocatorProxies.getLocatorHandler(args[0]) != null) {
            return equalsInternal(proxy, args[0]);
        }

        if (!(element instanceof WrapsElement) && GET_WRAPPED_ELEMENT.equals(method)) {
            // Delegates to element getWrappedElement only if implements the interface.
            return element;
        }

        try {
            return method.invoke(getHookLocatorResult(), args);
        } catch (InvocationTargetException e) {
            // Unwrap the underlying exception
            throw e.getCause();
        }
    }

    private boolean equalsInternal(Object me, Object other) {
        if (other == null) {
            return false;
        }
        if (other.getClass() != me.getClass()) {
            // Not same proxy type; false.
            // This may not be true for other scenarios.
        }
        InvocationHandler handler = Proxy.getInvocationHandler(other);
        if (!(handler instanceof ComponentHandler)) {
            // the proxies behave differently.
            return false;
        }
        return ((ComponentHandler) handler).getLocatorResult().equals(getLocatorResult());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComponentHandler that = (ComponentHandler) o;

        return locator != null ? locator.equals(that.locator) : that.locator == null;
    }

    @Override
    public int hashCode() {
        return locator != null ? locator.hashCode() : 0;
    }

    public void setHooks(HookChainBuilder hookChainBuilder, List<HookDefinition<?>> hookDefinitions) {
        if (hookDefinitions == null || hookDefinitions.size() == 0) {
            hooks = null;
        } else {
            hooks = hookChainBuilder.build(new Supplier<WebElement>() {
                @Override
                public WebElement get() {
                    return element;
                }
            }, new Supplier<ElementLocator>() {
                @Override
                public ElementLocator get() {
                    return locator;
                }
            }, hookDefinitions);
        }
    }
}
