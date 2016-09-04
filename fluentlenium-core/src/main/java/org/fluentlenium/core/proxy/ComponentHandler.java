package org.fluentlenium.core.proxy;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Proxy handler for {@link WebElement}.
 */
public class ComponentHandler extends AbstractLocatorHandler<WebElement> implements InvocationHandler, LocatorHandler<WebElement> {
    private static final Method GET_WRAPPED_ELEMENT = getMethod(WrapsElement.class, "getWrappedElement");

    public ComponentHandler(ElementLocator locator) {
        super(locator);
        if (this.locator instanceof WrapsElement) {
            WebElement result = ((WrapsElement) this.locator).getWrappedElement();
            if (result == null) {
                throw new NoSuchElementException("Element not found");
            }
            this.result = result;
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
            return isLoaded() ? getLocatorResult() : proxy;
        }
        return super.invoke(proxy, method, args);
    }


}
