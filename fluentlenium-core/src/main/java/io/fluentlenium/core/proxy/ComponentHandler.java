package io.fluentlenium.core.proxy;

import io.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * Proxy handler for {@link WebElement}.
 */
public class ComponentHandler extends AbstractLocatorAndInvocationHandler<WebElement> {
    private static final Method GET_WRAPPED_ELEMENT = ReflectionUtils.getMethod(WrapsElement.class, "getWrappedElement");

    /**
     * Creates a new component handler
     *
     * @param locator element locator for this component
     */
    public ComponentHandler(ElementLocator locator) {
        super(locator);
        if (this.locator instanceof WrapsElement) {
            fireProxyElementSearch();
            WebElement result = ((WrapsElement) this.locator).getWrappedElement();
            if (result == null) {
                throw noSuchElement();
            }
            this.result = result;
            fireProxyElementFound(result);
        }
    }

    @Override
    public String getMessageContext() {
        return "Element " + toString();
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
        WebElement element;
        try {
            element = getHookLocator().findElement();
        } catch (NoSuchElementException e) {
            element = null;
        }
        if (element == null) {
            throw noSuchElement();
        }
        return element;
    }

    @Override
    public WebElement getInvocationTarget(Method method) {
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
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (GET_WRAPPED_ELEMENT.equals(method)) {
            return loaded() ? getLocatorResult() : proxy;
        }
        return super.invoke(proxy, method, args);
    }
    //CHECKSTYLE.ON: IllegalThrows

}
