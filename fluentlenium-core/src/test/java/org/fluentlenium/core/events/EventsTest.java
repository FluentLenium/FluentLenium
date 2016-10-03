package org.fluentlenium.core.events;

import org.assertj.core.api.ThrowableAssert;
import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.components.DefaultComponentInstantiator;
import org.fluentlenium.core.domain.FluentWebElement;
import org.hamcrest.CustomMatcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Matchers.isNull;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventsTest {
    @Mock
    private JavascriptWebDriver driver;

    @Mock
    private WebDriver.Navigation navigation;

    @Mock
    private WebDriver.Options options;

    @Mock
    private WebDriver.Timeouts timeouts;

    private EventFiringWebDriver eventDriver;

    private ComponentInstantiator instantiator;
    private FluentAdapter fluentAdapter;

    @Before
    public void before() {
        when(driver.navigate()).thenReturn(navigation);
        when(driver.manage()).thenReturn(options);
        when(options.timeouts()).thenReturn(timeouts);

        eventDriver = new EventFiringWebDriver(driver);
        fluentAdapter = new FluentAdapter(eventDriver);

        instantiator = new DefaultComponentInstantiator(fluentAdapter);
    }

    @Test
    public void testFindBy() {
        EventsRegistry eventsRegistry = new EventsRegistry(fluentAdapter);

        FindByListener beforeListener = mock(FindByListener.class);
        FindByListener afterListener = mock(FindByListener.class);

        eventsRegistry.beforeFindBy(beforeListener);
        eventsRegistry.afterFindBy(afterListener);

        WebElement element = mock(WebElement.class);
        when(driver.findElement(By.cssSelector(".test"))).thenReturn(element);
        WebElement eventElement = eventDriver.findElement(By.cssSelector(".test"));

        verify(beforeListener).on(eq(By.cssSelector(".test")), isNull(FluentWebElement.class), notNull(WebDriver.class));
        verify(afterListener).on(eq(By.cssSelector(".test")), isNull(FluentWebElement.class), (WebDriver) notNull());

        WebElement childElement = mock(WebElement.class);
        when(element.findElement(By.cssSelector(".test2"))).thenReturn(childElement);

        reset(beforeListener, afterListener);
        eventElement.findElement(By.cssSelector(".test2"));

        verify(beforeListener).on(eq(By.cssSelector(".test2")), argThat(new ElementMatcher(element)), notNull(WebDriver.class));
        verify(afterListener).on(eq(By.cssSelector(".test2")), argThat(new ElementMatcher(element)), notNull(WebDriver.class));
    }

    @Test
    public void testClickOn() {
        EventsRegistry eventsRegistry = new EventsRegistry(fluentAdapter);

        ElementListener beforeListener = mock(ElementListener.class);
        ElementListener afterListener = mock(ElementListener.class);

        eventsRegistry.beforeClickOn(beforeListener);
        eventsRegistry.afterClickOn(afterListener);

        WebElement element = mock(WebElement.class);
        when(driver.findElement(By.cssSelector(".test"))).thenReturn(element);
        WebElement eventElement = eventDriver.findElement(By.cssSelector(".test"));

        WebElement childElement = mock(WebElement.class);
        when(element.findElement(By.cssSelector(".test2"))).thenReturn(childElement);

        reset(beforeListener, afterListener);
        eventElement.click();

        verify(beforeListener).on(argThat(new ElementMatcher(element)), notNull(WebDriver.class));
        verify(afterListener).on(argThat(new ElementMatcher(element)), notNull(WebDriver.class));
    }

    @Test
    public void testChangeValueOf() {
        EventsRegistry eventsRegistry = new EventsRegistry(fluentAdapter);

        ElementListener beforeListener = mock(ElementListener.class);
        ElementListener afterListener = mock(ElementListener.class);

        eventsRegistry.beforeChangeValueOf(beforeListener);
        eventsRegistry.afterChangeValueOf(afterListener);

        WebElement element = mock(WebElement.class);
        when(driver.findElement(By.cssSelector(".test"))).thenReturn(element);
        WebElement eventElement = eventDriver.findElement(By.cssSelector(".test"));

        WebElement childElement = mock(WebElement.class);
        when(element.findElement(By.cssSelector(".test2"))).thenReturn(childElement);

        reset(beforeListener, afterListener);
        eventElement.sendKeys("changeValue");

        verify(beforeListener).on(argThat(new ElementMatcher(element)), notNull(WebDriver.class));
        verify(afterListener).on(argThat(new ElementMatcher(element)), notNull(WebDriver.class));
    }

    @Test
    public void testNavigate() {
        EventsRegistry eventsRegistry = new EventsRegistry(fluentAdapter);

        assertThat(eventsRegistry.getWrappedDriver()).isSameAs(driver);

        NavigateAllListener beforeAllListener = mock(NavigateAllListener.class);
        NavigateAllListener afterAllListener = mock(NavigateAllListener.class);
        NavigateToListener beforeToListener = mock(NavigateToListener.class);
        NavigateToListener afterToListener = mock(NavigateToListener.class);
        NavigateListener beforeListener = mock(NavigateListener.class);
        NavigateListener afterListener = mock(NavigateListener.class);
        ScriptListener beforeScriptListener = mock(ScriptListener.class);
        ScriptListener afterScriptListener = mock(ScriptListener.class);
        ExceptionListener exceptionListener = mock(ExceptionListener.class);

        eventsRegistry.beforeNavigate(beforeAllListener);
        eventsRegistry.afterNavigate(afterAllListener);
        eventsRegistry.beforeNavigateBack(beforeListener);
        eventsRegistry.afterNavigateBack(afterListener);
        eventsRegistry.beforeNavigateForward(beforeListener);
        eventsRegistry.afterNavigateForward(afterListener);
        eventsRegistry.beforeNavigateRefresh(beforeListener);
        eventsRegistry.afterNavigateRefresh(afterListener);
        eventsRegistry.beforeNavigateTo(beforeToListener);
        eventsRegistry.afterNavigateTo(afterToListener);
        eventsRegistry.beforeScript(beforeScriptListener);
        eventsRegistry.afterScript(afterScriptListener);
        eventsRegistry.onException(exceptionListener);

        eventDriver.get("http://www.google.fr");

        verify(beforeAllListener)
                .on(eq("http://www.google.fr"), notNull(WebDriver.class), isNull(NavigateAllListener.Direction.class));
        verify(afterAllListener)
                .on(eq("http://www.google.fr"), notNull(WebDriver.class), isNull(NavigateAllListener.Direction.class));
        verify(beforeToListener).on(eq("http://www.google.fr"), notNull(WebDriver.class));
        verify(afterToListener).on(eq("http://www.google.fr"), notNull(WebDriver.class));

        reset(beforeAllListener, afterAllListener, beforeToListener, afterToListener, beforeListener, afterListener);

        eventDriver.navigate().to("http://www.google.fr");

        verify(beforeAllListener)
                .on(eq("http://www.google.fr"), notNull(WebDriver.class), isNull(NavigateAllListener.Direction.class));
        verify(afterAllListener)
                .on(eq("http://www.google.fr"), notNull(WebDriver.class), isNull(NavigateAllListener.Direction.class));
        verify(beforeToListener).on(eq("http://www.google.fr"), notNull(WebDriver.class));
        verify(afterToListener).on(eq("http://www.google.fr"), notNull(WebDriver.class));

        reset(beforeAllListener, afterAllListener, beforeToListener, afterToListener, beforeListener, afterListener);

        eventDriver.navigate().back();

        verify(beforeAllListener).on(isNull(String.class), notNull(WebDriver.class), eq(NavigateAllListener.Direction.BACK));
        verify(afterAllListener).on(isNull(String.class), notNull(WebDriver.class), eq(NavigateAllListener.Direction.BACK));
        verify(beforeListener).on(notNull(WebDriver.class));
        verify(afterListener).on(notNull(WebDriver.class));

        reset(beforeAllListener, afterAllListener, beforeToListener, afterToListener, beforeListener, afterListener);

        eventDriver.navigate().forward();

        verify(beforeAllListener).on(isNull(String.class), notNull(WebDriver.class), eq(NavigateAllListener.Direction.FORWARD));
        verify(afterAllListener).on(isNull(String.class), notNull(WebDriver.class), eq(NavigateAllListener.Direction.FORWARD));
        verify(beforeListener).on(notNull(WebDriver.class));
        verify(afterListener).on(notNull(WebDriver.class));

        reset(beforeAllListener, afterAllListener, beforeToListener, afterToListener, beforeListener, afterListener);

        eventDriver.navigate().refresh();

        verify(beforeAllListener).on(isNull(String.class), notNull(WebDriver.class), eq(NavigateAllListener.Direction.REFRESH));
        verify(afterAllListener).on(isNull(String.class), notNull(WebDriver.class), eq(NavigateAllListener.Direction.REFRESH));
        verify(beforeListener).on(notNull(WebDriver.class));
        verify(afterListener).on(notNull(WebDriver.class));

        reset(beforeAllListener, afterAllListener, beforeToListener, afterToListener, beforeListener, afterListener);

        eventDriver.executeScript("test");

        verify(beforeScriptListener).on(eq("test"), notNull(WebDriver.class));
        verify(afterScriptListener).on(eq("test"), notNull(WebDriver.class));

        reset(beforeAllListener, afterAllListener, beforeToListener, afterToListener, beforeListener, afterListener);

        when(driver.findElement((By) any())).thenThrow(IllegalStateException.class);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                eventDriver.findElement(By.cssSelector(".test"));
            }
        }).isExactlyInstanceOf(IllegalStateException.class);

        verify(exceptionListener).on(isA(IllegalStateException.class), notNull(WebDriver.class));

        reset(exceptionListener);
        eventsRegistry.close();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                eventDriver.findElement(By.cssSelector(".test"));
            }
        }).isExactlyInstanceOf(IllegalStateException.class);

        verify(exceptionListener, never()).on(isA(IllegalStateException.class), notNull(WebDriver.class));
    }

    @Test
    public void testAdapterHashcodeEquals() {
        EventListener listener = mock(EventListener.class);
        EventListener otherListener = mock(EventListener.class);

        assertThat(new EventAdapter(listener, instantiator)).isEqualTo(new EventAdapter(listener, instantiator));
        assertThat(new EventAdapter(listener, instantiator).hashCode())
                .isEqualTo(new EventAdapter(listener, instantiator).hashCode());

        assertThat(new EventAdapter(listener, instantiator)).isNotEqualTo(new EventAdapter(otherListener, instantiator));
        assertThat(new EventAdapter(listener, instantiator)).isNotEqualTo("OtherType");

        EventAdapter instance = new EventAdapter(mock(EventListener.class), instantiator);
        assertThat(instance).isEqualTo(instance);
    }

    private static class ElementMatcher extends CustomMatcher<FluentWebElement> {
        private final WebElement element;

        private ElementMatcher(WebElement element) {
            super("Element");
            this.element = element;
        }

        @Override
        public boolean matches(Object argument) {
            if (argument == null && this.element == null) {
                return true;
            }
            if (argument == null && this.element != null) {
                return false;
            }
            WebElement element = ((FluentWebElement) argument).getElement();
            while (element instanceof WrapsElement) {
                element = ((WrapsElement) element).getWrappedElement();
            }
            return element == this.element;
        }
    }

    private static abstract class JavascriptWebDriver implements WebDriver, JavascriptExecutor {

    }
}
