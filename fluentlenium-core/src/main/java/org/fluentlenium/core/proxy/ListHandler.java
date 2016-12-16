package org.fluentlenium.core.proxy;

import org.fluentlenium.core.domain.WrapsElements;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Proxy handler for list of {@link WebElement}.
 */
public class ListHandler extends AbstractLocatorHandler<List<WebElement>> {
    private static final Method GET_WRAPPED_ELEMENTS = getMethod(WrapsElements.class, "getWrappedElements");

    /**
     * Creates a new proxy handler for elements.
     *
     * @param locator elements locator
     */
    public ListHandler(ElementLocator locator) {
        super(locator);
        if (this.locator instanceof WrapsElements) {
            fireProxyElementSearch();
            List<WebElement> foundElements = ((WrapsElements) this.locator).getWrappedElements();
            if (foundElements == null) {
                foundElements = Collections.emptyList();
            }
            result = wrapElements(foundElements);
            fireProxyElementFound(result);
        }
    }

    @Override
    public String getMessageContext() {
        return "Elements " + toString();
    }

    @Override
    protected List<WebElement> resultToList(List<WebElement> result) {
        return result;
    }

    @Override
    protected WebElement getElement() {
        return null;
    }

    @Override
    public List<WebElement> getInvocationTarget(Method method) {
        return result;
    }

    @Override
    public boolean present() {
        return super.present() && result.size() > 0;
    }

    @Override
    protected boolean isStale() {
        if (result.size() > 0) {
            try {
                result.get(0).isEnabled();
            } catch (StaleElementReferenceException e) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<WebElement> getLocatorResultImpl() {
        List<WebElement> foundElements = getHookLocator().findElements();
        if (foundElements == null) {
            foundElements = Collections.emptyList();
        }
        return wrapElements(foundElements);
    }

    private List<WebElement> wrapElements(List<WebElement> foundElements) {
        List<WebElement> proxyElements = new ArrayList<>();
        for (WebElement element : foundElements) {
            WebElement proxyElement = LocatorProxies.createWebElement(new ElementInstanceLocator(element));
            LocatorProxies.setHooks(proxyElement, hookChainBuilder, hookDefinitions);
            proxyElements.add(proxyElement);
        }
        return proxyElements;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (GET_WRAPPED_ELEMENTS.equals(method)) {
            return result == null ? proxy : getLocatorResult();
        }
        return super.invoke(proxy, method, args);
    }

    @Override
    protected String getLazyToString() {
        return "Lazy Element List";
    }
}
