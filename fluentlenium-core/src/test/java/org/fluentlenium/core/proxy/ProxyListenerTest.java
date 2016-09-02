package org.fluentlenium.core.proxy;

import com.google.common.base.Supplier;
import org.hamcrest.CustomMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class ProxyListenerTest {
    @Mock
    private WebElement element1;

    @Mock
    private WebElement element2;

    @Mock
    private WebElement element3;

    @Mock
    private ProxyElementListener listener;

    @Test
    public void testElement() {
        WebElement proxy = LocatorProxies.createWebElement(new Supplier<WebElement>() {
            @Override
            public WebElement get() {
                return element1;
            }
        });

        LocatorProxies.addProxyListener(proxy, listener);

        verifyZeroInteractions(listener);

        proxy.click();

        verify(listener).proxyElementSearch(refEq(proxy), any(ElementLocator.class));
        verify(listener).proxyElementFound(refEq(proxy), any(ElementLocator.class), refEq(element1));

        LocatorProxies.removeProxyListener(proxy, listener);

        reset(listener);

        proxy.click();

        verifyZeroInteractions(listener);
    }

    private static class ElementMatcher extends CustomMatcher<WebElement> {
        private final WebElement expected;

        public ElementMatcher(WebElement expected) {
            super("matches element");
            this.expected = expected;
        }

        @Override
        public boolean matches(Object item) {
            return ((WrapsElement) item).getWrappedElement().equals(expected);
        }
    }

    @Test
    public void testElementList() {
        List<WebElement> proxy = LocatorProxies.createWebElementList(new Supplier<List<WebElement>>() {
            @Override
            public List<WebElement> get() {
                return Arrays.asList(element1, element2, element3);
            }
        });

        LocatorProxies.addProxyListener(proxy, listener);

        verifyZeroInteractions(listener);

        LocatorProxies.now(proxy);

        verify(listener).proxyElementSearch(refEq(proxy), any(ElementLocator.class));
        verify(listener).proxyElementFound(refEq(proxy), any(ElementLocator.class), Matchers.argThat(new ElementMatcher(element1)));
        verify(listener).proxyElementFound(refEq(proxy), any(ElementLocator.class), Matchers.argThat(new ElementMatcher(element2)));
        verify(listener).proxyElementFound(refEq(proxy), any(ElementLocator.class), Matchers.argThat(new ElementMatcher(element3)));


        LocatorProxies.removeProxyListener(proxy, listener);

        reset(listener);

        LocatorProxies.now(proxy);

        verifyZeroInteractions(listener);
    }
}
