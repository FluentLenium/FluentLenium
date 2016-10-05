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

    public ListHandler(final ElementLocator locator) {
        super(locator);
        if (this.locator instanceof WrapsElements) {
            fireProxyElementSearch();
            List<WebElement> foundElements = ((WrapsElements) this.locator).getWrappedElements();
            if (foundElements == null) {
                foundElements = Collections.emptyList();
            }
            this.result = wrapElements(foundElements);
            fireProxyElementFound(this.result);
        }
    }

    @Override
    protected List<WebElement> resultToList(final List<WebElement> result) {
        return result;
    }

    @Override
    protected WebElement getElement() {
        return null;
    }

    @Override
    public List<WebElement> getInvocationTarget(final Method method) {
        return result;
    }

    @Override
    public boolean isPresent() {
        return super.isPresent() && result.size() > 0;
    }

    @Override
    protected boolean isStale() {
        if (result.size() > 0) {
            try {
                result.get(0).isEnabled();
            } catch (final StaleElementReferenceException e) {
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

    private List<WebElement> wrapElements(final List<WebElement> foundElements) {
        final List<WebElement> proxyElements = new ArrayList<>();
        for (final WebElement element : foundElements) {
            final WebElement proxyElement = LocatorProxies.createWebElement(new ElementInstanceLocator(element));
            LocatorProxies.setHooks(proxyElement, hookChainBuilder, hookDefinitions);
            proxyElements.add(proxyElement);
        }
        return proxyElements;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable { // NOPMD UseVarargs
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
