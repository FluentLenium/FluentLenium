package io.fluentlenium.core.proxy;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import io.fluentlenium.core.domain.FluentWebElement;import io.fluentlenium.core.hook.FluentHook;import io.fluentlenium.core.hook.HookChainBuilder;import io.fluentlenium.core.hook.HookDefinition;import org.assertj.core.api.Assertions;import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.core.hook.FluentHook;
import io.fluentlenium.core.hook.HookChainBuilder;
import io.fluentlenium.core.hook.HookDefinition;
import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Unit test for {@link AbstractLocatorHandler}.
 */
@RunWith(MockitoJUnitRunner.class)
public class AbstractLocatorHandlerTest {

    private TestLocatorHandler locatorHandler;
    @Mock
    private ElementLocator locator;

    @Before
    public void setup() {
        locatorHandler = new TestLocatorHandler(locator);
    }

    //setHooks

    @Test
    public void shouldSetHooksWhenHookDefinitionsAreNotEmpty() {
        HookChainBuilder hookChainBuilder = mock(HookChainBuilder.class);
        HookDefinition<?> hookDefinition1 = mock(HookDefinition.class);
        HookDefinition<?> hookDefinition2 = mock(HookDefinition.class);
        List<HookDefinition<?>> hookDefinitions = ImmutableList.of(hookDefinition1, hookDefinition2);

        locatorHandler.setHooks(hookChainBuilder, hookDefinitions);

        Assertions.assertThat(locatorHandler.hookChainBuilder).isSameAs(hookChainBuilder);
        Assertions.assertThat(locatorHandler.hookDefinitions).isSameAs(hookDefinitions);
        verify(locatorHandler.hookChainBuilder).build(any(), any(), any(), eq(hookDefinitions));
        Assertions.assertThat(locatorHandler.hooks).isNotNull();
    }

    @Test
    public void shouldSetHooksToNullWhenHookDefinitionsAreEmpty() {
        HookChainBuilder hookChainBuilder = mock(HookChainBuilder.class);

        locatorHandler.setHooks(hookChainBuilder, emptyList());

        Assertions.assertThat(locatorHandler.hookChainBuilder).isNull();
        Assertions.assertThat(locatorHandler.hookDefinitions).isNull();
        Assertions.assertThat(locatorHandler.hooks).isNull();
    }

    @Test
    public void shouldSetHooksToNullWhenHookDefinitionsIsNull() {
        HookChainBuilder hookChainBuilder = mock(HookChainBuilder.class);

        locatorHandler.setHooks(hookChainBuilder, null);

        Assertions.assertThat(locatorHandler.hookChainBuilder).isNull();
        Assertions.assertThat(locatorHandler.hookDefinitions).isNull();
        Assertions.assertThat(locatorHandler.hooks).isNull();
    }

    //getHookLocator

    @Test
    public void shouldReturnLocatorIfHooksAreEmpty() {
        HookChainBuilder hookChainBuilder = mock(HookChainBuilder.class);
        locatorHandler.setHooks(hookChainBuilder, emptyList());

        assertThat(locatorHandler.getHookLocator()).isSameAs(locator);
    }

    @Test
    public void shouldReturnLocatorIfHooksAreNull() {
        HookChainBuilder hookChainBuilder = mock(HookChainBuilder.class);
        locatorHandler.setHooks(hookChainBuilder, emptyList());

        assertThat(locatorHandler.getHookLocator()).isSameAs(locator);
    }

    @Test
    public void shouldReturnLastHookIfHooksArePresent() {
        FluentHook fluentHook1 = mock(FluentHook.class);
        FluentHook fluentHook2 = mock(FluentHook.class);
        List<FluentHook> hooks = ImmutableList.of(fluentHook1, fluentHook2);
        HookChainBuilder hookChainBuilder = mock(HookChainBuilder.class);
        when(hookChainBuilder.build(any(), any(), any(), any())).thenReturn(hooks);
        HookDefinition<?> hookDefinition1 = mock(HookDefinition.class);
        HookDefinition<?> hookDefinition2 = mock(HookDefinition.class);

        locatorHandler.setHooks(hookChainBuilder, ImmutableList.of(hookDefinition1, hookDefinition2));

        assertThat(locatorHandler.getHookLocator()).isSameAs(fluentHook2);
    }

    //loaded

    @Test
    public void shouldBeLoadedWhenResultIsNotNull() {
        locatorHandler.result = Mockito.mock(FluentWebElement.class);

        assertThat(locatorHandler.loaded()).isTrue();
    }

    @Test
    public void shouldNotBeLoadedWhenResultIsNull() {
        locatorHandler.result = null;

        assertThat(locatorHandler.loaded()).isFalse();
    }

    //fireProxyElementSearch

    @Test
    public void shouldFireProxyElementSearch() {
        ProxyElementListener proxyElementListener1 = mock(ProxyElementListener.class);
        ProxyElementListener proxyElementListener2 = mock(ProxyElementListener.class);
        FluentWebElement proxy = mock(FluentWebElement.class);
        locatorHandler.setProxy(proxy);
        locatorHandler.addListener(proxyElementListener1);
        locatorHandler.addListener(proxyElementListener2);

        locatorHandler.fireProxyElementSearch();

        verify(proxyElementListener1).proxyElementSearch(proxy, locator);
        verify(proxyElementListener2).proxyElementSearch(proxy, locator);
    }

    //fireProxyElementFound

    @Test
    public void shouldFireProxyElementFound() {
        ProxyElementListener proxyElementListener1 = mock(ProxyElementListener.class);
        ProxyElementListener proxyElementListener2 = mock(ProxyElementListener.class);
        FluentWebElement proxy = mock(FluentWebElement.class);
        locatorHandler.setProxy(proxy);
        locatorHandler.addListener(proxyElementListener1);
        locatorHandler.addListener(proxyElementListener2);
        FluentWebElement result = mock(FluentWebElement.class);

        locatorHandler.fireProxyElementFound(result);

        verify(proxyElementListener1).proxyElementFound(eq(proxy), eq(locator), any(List.class));
        verify(proxyElementListener2).proxyElementFound(eq(proxy), eq(locator), any(List.class));
    }

    //present

    @Test
    public void shouldReturnFalseInCaseOfTimeoutException() {
        TestLocatorHandler handler = spy(new TestLocatorHandler(mock(ElementLocator.class)));

        doThrow(TimeoutException.class).when(handler).now();

        assertThat(handler.present()).isFalse();
    }

    @Test
    public void shouldReturnFalseInCaseOfNoSuchElementException() {
        TestLocatorHandler handler = spy(new TestLocatorHandler(mock(ElementLocator.class)));

        doThrow(NoSuchElementException.class).when(handler).now();

        assertThat(handler.present()).isFalse();
    }

    @Test
    public void shouldReturnFalseInCaseOfStaleElementReferenceException() {
        TestLocatorHandler handler = spy(new TestLocatorHandler(mock(ElementLocator.class)));

        doThrow(StaleElementReferenceException.class).when(handler).now();

        assertThat(handler.present()).isFalse();
    }

    @Test
    public void shouldReturnAsPresentIfElementIsLoadedAndNotStale() {
        TestLocatorHandler handler = spy(new TestLocatorHandler(mock(ElementLocator.class)));

        when(handler.loaded()).thenReturn(true);
        when(handler.isStale()).thenReturn(false);

        assertThat(handler.present()).isTrue();
    }

    //proxyToString

    @Test
    public void shouldReturnLocatorSpecificDefaultToStringIfElementToStringIsNull() {
        ElementLocator elementLocator = mock(ElementLocator.class, withSettings().extraInterfaces(WrapsElement.class));
        TestLocatorHandler handler = new TestLocatorHandler(elementLocator);

        assertThat(handler.proxyToString(null)).isEqualTo("Lazy Element");
    }

    @Test
    public void shouldReturnDefaultToStringIfElementToStringIsNull() {
        assertThat(locatorHandler.proxyToString(null)).isEqualTo("locator (Lazy Element)");
    }

    @Test
    public void shouldReturnLocatorSpecificDefaultToStringWithElementToString() {
        ElementLocator elementLocator = mock(ElementLocator.class, withSettings().extraInterfaces(WrapsElement.class));
        TestLocatorHandler handler = new TestLocatorHandler(elementLocator);

        assertThat(handler.proxyToString("some to string")).isEqualTo("some to string");
    }

    @Test
    public void shouldReturnCustomToStringIfElementToStringWithElementToString() {
        assertThat(locatorHandler.proxyToString("some to string")).isEqualTo("locator (some to string)");
    }

    private class TestLocatorHandler extends AbstractLocatorHandler<FluentWebElement> {
        TestLocatorHandler(ElementLocator locator) {
            super(locator);
        }

        @Override
        public FluentWebElement getLocatorResultImpl() {
            return null;
        }

        @Override
        protected List<WebElement> resultToList(FluentWebElement result) {
            return emptyList();
        }

        @Override
        protected boolean isStale() {
            return false;
        }

        @Override
        protected WebElement getElement() {
            return result.getElement();
        }

        @Override
        public FluentWebElement getInvocationTarget(Method method) {
            return null;
        }

        @Override
        public String getMessageContext() {
            return null;
        }
    }
}
