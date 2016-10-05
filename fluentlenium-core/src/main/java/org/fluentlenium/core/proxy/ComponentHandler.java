package org.fluentlenium.core.proxy;

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
public class ComponentHandler extends AbstractLocatorHandler<WebElement>
        implements InvocationHandler, LocatorHandler<WebElement> {
    private static final Method GET_WRAPPED_ELEMENT = getMethod(WrapsElement.class, "getWrappedElement");

    public ComponentHandler(final ElementLocator locator) {
        super(locator);
        if (this.locator instanceof WrapsElement) {
            fireProxyElementSearch();
            final WebElement result = ((WrapsElement) this.locator).getWrappedElement();
            if (result == null) {
                throw noSuchElement();
            }
            this.result = result;
            fireProxyElementFound(this.result);
        }
    }

    @Override
    public String getMessageContext() {
        return "Element " + toString();
    }

    @Override
    protected List<WebElement> resultToList(final WebElement result) {
        return Arrays.asList(result);
    }

    @Override
    protected boolean isStale() {
        try {
            result.isEnabled();
            return false;
        } catch (final StaleElementReferenceException e) {
            return true;
        }
    }

    @Override
    public WebElement getElement() {
        return result;
    }

    @Override
    public WebElement getLocatorResultImpl() {
        final WebElement element = getHookLocator().findElement();
        if (element == null) {
            throw noSuchElement();
        }
        return element;
    }

    @Override
    public WebElement getInvocationTarget(final Method method) {
        if (method != null && method.getDeclaringClass().equals(Object.class)) {
            return result;
        }
        if (getElement() == null) {
            return null;
        }
        if (hooks != null && !hooks.isEmpty()) {
            return hooks.get(hooks.size() - 1);
        }
        return getElement();
    }

    //CHECKSTYLE.OFF: IllegalThrows
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable { // NOPMD UseVarargs
        if (GET_WRAPPED_ELEMENT.equals(method)) {
            return isLoaded() ? getLocatorResult() : proxy;
        }
        return super.invoke(proxy, method, args);
    }
    //CHECKSTYLE.ON: IllegalThrows

}
