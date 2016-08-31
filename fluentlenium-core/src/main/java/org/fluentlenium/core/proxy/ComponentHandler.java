package org.fluentlenium.core.proxy;

import com.google.common.base.Supplier;
import org.fluentlenium.core.hook.FluentHook;
import org.fluentlenium.core.hook.HookChainBuilder;
import org.fluentlenium.core.hook.HookDefinition;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComponentHandler extends AbstractLocatorHandler<WebElement> implements InvocationHandler, LocatorHandler<WebElement> {
    private static final Method GET_WRAPPED_ELEMENT = getMethod(WrapsElement.class, "getWrappedElement");

    public ComponentHandler(ElementLocator locator) {
        super(locator);
        if (this.locator instanceof WrapsElement) {
            this.result = ((WrapsElement) this.locator).getWrappedElement();
            this.loaded = true;
        }
    }

    @Override
    public WebElement getElement() {
        return result;
    }

    @Override
    public WebElement getLocatorResultImpl() {
        fireProxyElementSearch(proxy, locator);
        WebElement element = getHookLocator().findElement();
        if (element == null) {
            throw new NoSuchElementException("Element not found");
        }
        fireProxyElementFound(proxy, locator, element);
        return element;
    }

    @Override
    protected WebElement getInvocationTarget() {
        return getHookElement();
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (GET_WRAPPED_ELEMENT.equals(method)) {
            return loaded ? getLocatorResult() : proxy;
        }
        return super.invoke(proxy, method, args);
    }


}
