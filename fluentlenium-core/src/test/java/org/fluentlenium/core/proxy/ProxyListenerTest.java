package org.fluentlenium.core.proxy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

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
        WebElement proxy = LocatorProxies.createWebElement(() -> element1);

        LocatorProxies.addProxyListener(proxy, listener);

        verifyNoInteractions(listener);

        proxy.click();

        verify(listener).proxyElementSearch(refEq(proxy), any(ElementLocator.class));
        verify(listener).proxyElementFound(refEq(proxy), any(ElementLocator.class), refEq(Arrays.asList(element1)));

        LocatorProxies.removeProxyListener(proxy, listener);

        reset(listener);

        proxy.click();

        verifyNoInteractions(listener);
    }

    private static class ElementMatcher implements ArgumentMatcher<List<WebElement>> {
        private final List<WebElement> expected;

        ElementMatcher(List<WebElement> expected) {
            this.expected = expected;
        }

        @Override
        public boolean matches(List<WebElement> items) {
            List<WebElement> unwrapped = new ArrayList<>();
            for (WebElement item : items) {
                if (item instanceof WrapsElement) {
                    item = ((WrapsElement) item).getWrappedElement();
                }
                unwrapped.add(item);
            }
            return unwrapped.equals(expected);
        }
    }

    @Test
    public void testElementList() {
        List<WebElement> proxy = LocatorProxies.createWebElementList(() -> Arrays.asList(element1, element2, element3));

        LocatorProxies.addProxyListener(proxy, listener);

        verifyNoInteractions(listener);

        LocatorProxies.now(proxy);

        verify(listener).proxyElementSearch(refEq(proxy), any(ElementLocator.class));
        verify(listener).proxyElementFound(refEq(proxy), any(ElementLocator.class),
                ArgumentMatchers.argThat(new ElementMatcher(Arrays.asList(element1, element2, element3))));

        LocatorProxies.removeProxyListener(proxy, listener);

        reset(listener);

        LocatorProxies.now(proxy);

        verifyNoInteractions(listener);
    }
}
