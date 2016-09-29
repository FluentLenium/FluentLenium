package org.fluentlenium.core.proxy;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * Proxy handler for {@link WebElement}.
 */
public class ComponentHandler extends AbstractLocatorHandler<WebElement> implements InvocationHandler, LocatorHandler<WebElement> {
    private static final Method GET_WRAPPED_ELEMENT = getMethod(WrapsElement.class, "getWrappedElement");

    public ComponentHandler(ElementLocator locator) {
        super(locator);
        if (this.locator instanceof WrapsElement) {
            fireProxyElementSearch();
            WebElement result = ((WrapsElement) this.locator).getWrappedElement();
            if (result == null) {
                throw new NoSuchElementException("Element not found");
            }
            this.result = result;
            fireProxyElementFound(this.result);
        }
    }

    @Override
    protected List<WebElement> resultToList(WebElement result) {
        return Arrays.asList(result);
    }

    @Override
    protected boolean isStale() {
        try {
            result.isEnabled();
            return false;
        } catch (StaleElementReferenceException e) {
            return true;
        }
    }

    @Override
    public WebElement getElement() {
        return result;
    }

    @Override
    public WebElement getLocatorResultImpl() {
        WebElement element = getHookLocator().findElement();
        if (element == null) {
            throw new NoSuchElementException("Element not found");
        }
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
