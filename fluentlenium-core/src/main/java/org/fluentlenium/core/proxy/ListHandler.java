package org.fluentlenium.core.proxy;

import org.fluentlenium.core.domain.WrapsElements;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Proxy handler for list of {@link WebElement}.
 */
class ListHandler extends AbstractLocatorHandler<List<WebElement>> {
    private static final Method GET_WRAPPED_ELEMENTS = getMethod(WrapsElements.class, "getWrappedElements");

    public ListHandler(ElementLocator locator) {
        super(locator);
        if (this.locator instanceof WrapsElements) {
            List<WebElement> foundElements = ((WrapsElements) this.locator).getWrappedElements();
            if (foundElements == null) foundElements = Collections.emptyList();
            this.result = wrapElements(foundElements);
        }
    }

    @Override
    protected WebElement getElement() {
        return null;
    }

    @Override
    protected List<WebElement> getInvocationTarget() {
        return result;
    }

    @Override
    public boolean isPresent() {
        return super.isPresent() && result.size() > 0;
    }

    @Override
    public List<WebElement> getLocatorResultImpl() {
        fireProxyElementSearch(proxy, locator);
        List<WebElement> foundElements = getHookLocator().findElements();
        if (foundElements == null) foundElements = Collections.emptyList();
        foundElements = wrapElements(foundElements);
        for (WebElement element : foundElements) {
            fireProxyElementFound(proxy, locator, element);
        }
        return foundElements;
    }

    protected List<WebElement> wrapElements(List<WebElement> foundElements) {
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
            return result != null ? getLocatorResult() : proxy;
        }
        return super.invoke(proxy, method, args);
    }
}
