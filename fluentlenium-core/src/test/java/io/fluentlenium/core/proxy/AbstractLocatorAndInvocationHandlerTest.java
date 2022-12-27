package io.fluentlenium.core.proxy;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.Method;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link AbstractLocatorAndInvocationHandler}.
 */
@RunWith(MockitoJUnitRunner.class)
public class AbstractLocatorAndInvocationHandlerTest {

    @Mock
    private ElementLocator locator;
    private TestLocatorAndInvocationHandler invocationHandler;

    @Before
    public void setup() {
        invocationHandler = spy(new TestLocatorAndInvocationHandler(locator));
    }

    //toString

    @Test
    public void testLoadedToString() throws Throwable {
        WebElement proxy = mock(WebElement.class);
        Method toString = getMethod("toString");
        when(invocationHandler.loaded()).thenReturn(true);
        when(invocationHandler.getInvocationTarget(toString)).thenReturn(proxy);

        assertThat(invocationHandler.invoke(proxy, toString, new Object[0])).asString()
                .startsWith("locator (Mock for WebElement");
    }

    @Test
    public void testNotLoadedToString() throws Throwable {
        WebElement proxy = mock(WebElement.class);
        Method toString = getMethod("toString");
        when(invocationHandler.loaded()).thenReturn(false);

        assertThat(invocationHandler.invoke(proxy, toString, new Object[0])).asString().isEqualTo("locator (Lazy Element)");
    }

    //loaded equals

    @Test
    @Ignore("Needs a way to mock LocatorProxies.getLocatorHandler().")
    public void testLoadedEquals() {
    }

    @Test
    public void testLoadedEqualsWithRetry() throws Throwable {
        WebElement proxy = mock(WebElement.class);
        Method equals = getMethod("equals", Object.class);
        Object[] args = {proxy};
        when(invocationHandler.loaded()).thenReturn(true);
        when(invocationHandler.getInvocationTarget(equals)).thenReturn(proxy);

        assertThat(invocationHandler.invoke(proxy, equals, args)).isEqualTo(true);
    }

    @Test
    public void testLoadedNotEqualsWithRetry() throws Throwable {
        WebElement proxy = mock(WebElement.class);
        Method equals = getMethod("equals", Object.class);
        Object[] args = {mock(WebElement.class)};
        when(invocationHandler.loaded()).thenReturn(true);
        when(invocationHandler.getInvocationTarget(equals)).thenReturn(proxy);

        assertThat(invocationHandler.invoke(proxy, equals, args)).isEqualTo(false);
    }

    @Test
    public void testLoadedOtherThanEqualsWithRetry() throws Throwable {
        WebElement proxy = mock(WebElement.class);
        Method hashCode = getMethod("hashCode");
        when(invocationHandler.loaded()).thenReturn(true);
        when(invocationHandler.getInvocationTarget(hashCode)).thenReturn(proxy);

        assertThat(invocationHandler.invoke(proxy, hashCode, new Object[0])).isEqualTo(proxy.hashCode());
    }

    //loaded other than equals

    @Test
    @Ignore("Needs a way to mock LocatorProxies.getLocatorHandler().")
    public void testLoadedOtherThanEqualsWithoutRetry() {
    }

    //unloaded equals

    @Test
    @Ignore("Needs a way to mock LocatorProxies.getLocatorHandler().")
    public void testNotLoadedEquals() {

    }

    @Test
    public void testNotLoadedEqualsWithRetry() throws Throwable {
        WebElement proxy = mock(WebElement.class);
        Method equals = getMethod("equals", Object.class);
        Object[] args = {mock(WebElement.class)};
        when(invocationHandler.loaded()).thenReturn(false);
        when(invocationHandler.getInvocationTarget(equals)).thenReturn(proxy);

        assertThat(invocationHandler.invoke(proxy, equals, args)).isEqualTo(false);
    }

    @Test
    public void testNotLoadedNotEqualsWithRetry() throws Throwable {
        WebElement proxy = mock(WebElement.class);
        Method equals = getMethod("equals", Object.class);
        Object[] args = {proxy};
        when(invocationHandler.loaded()).thenReturn(false);
        when(invocationHandler.getInvocationTarget(equals)).thenReturn(proxy);

        assertThat(invocationHandler.invoke(proxy, equals, args)).isEqualTo(true);
    }

    //unloaded hashcode

    @Test
    public void testNotLoadedHashCode() throws Throwable {
        WebElement proxy = mock(WebElement.class);
        Method hashCode = getMethod("hashCode");
        when(invocationHandler.loaded()).thenReturn(false);
        int expectedHashCode = 2048 + locator.hashCode();

        assertThat(invocationHandler.invoke(proxy, hashCode, new Object[0])).isEqualTo(expectedHashCode);
    }

    //unloaded other than hashcode

    @Test
    public void testNotLoadedOtherThanHashCodeWithRetry() throws Throwable {
        WebElement proxy = mock(WebElement.class);
        Method isSelected = WebElement.class.getMethod("isSelected");
        when(proxy.isSelected()).thenReturn(true);
        when(invocationHandler.loaded()).thenReturn(false);
        when(invocationHandler.getInvocationTarget(isSelected)).thenReturn(proxy);

        assertThat(invocationHandler.invoke(proxy, isSelected, new Object[0])).isEqualTo(true);
    }

    private Method getMethod(String name, Class... types) {
        try {
            return Object.class.getMethod(name, types);
        } catch (NoSuchMethodException e) {
            fail("No method was found in Object for name: " + name);
            return null;
        }
    }

    private class TestLocatorAndInvocationHandler extends AbstractLocatorAndInvocationHandler<WebElement> {

        TestLocatorAndInvocationHandler(ElementLocator locator) {
            super(locator);
        }

        @Override
        public WebElement getLocatorResultImpl() {
            return null;
        }

        @Override
        protected List<WebElement> resultToList(WebElement result) {
            return null;
        }

        @Override
        protected boolean isStale() {
            return false;
        }

        @Override
        protected WebElement getElement() {
            return null;
        }

        @Override
        public WebElement getInvocationTarget(Method method) {
            return null;
        }

        @Override
        public String getMessageContext() {
            return null;
        }
    }
}
