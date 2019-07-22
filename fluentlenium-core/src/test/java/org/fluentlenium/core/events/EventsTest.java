package org.fluentlenium.core.events;

import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.components.DefaultComponentInstantiator;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// NOPMD AccessorClassGeneration
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
        fluentAdapter = new FluentAdapter();
        fluentAdapter.initFluent(eventDriver);

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

        verify(beforeListener).on(eq(By.cssSelector(".test")), isNull(), notNull());
        verify(afterListener).on(eq(By.cssSelector(".test")), argThat(new ElementMatcher(element)), notNull());

        WebElement childElement = mock(WebElement.class);
        when(element.findElement(By.cssSelector(".test2"))).thenReturn(childElement);

        reset(beforeListener, afterListener);
        eventElement.findElement(By.cssSelector(".test2"));

        verify(beforeListener).on(eq(By.cssSelector(".test2")), argThat(new ElementMatcher(element)), notNull());
        verify(afterListener).on(eq(By.cssSelector(".test2")), argThat(new ElementMatcher(element)), notNull());
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

        reset(beforeListener, afterListener);
        eventElement.click();

        verify(beforeListener).on(argThat(new ElementMatcher(element)), notNull());
        verify(afterListener).on(argThat(new ElementMatcher(element)), notNull());
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

        reset(beforeListener, afterListener);
        eventElement.sendKeys("changeValue");

        verify(beforeListener).on(argThat(new ElementMatcher(element)), notNull());
        verify(afterListener).on(argThat(new ElementMatcher(element)), notNull());
    }

    @Test
    public void testNavigate() { // NOPMD ExcessiveMethodLength
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
                .on(eq("http://www.google.fr"), notNull(), isNull());
        verify(afterAllListener)
                .on(eq("http://www.google.fr"), notNull(), isNull());
        verify(beforeToListener).on(eq("http://www.google.fr"), notNull());
        verify(afterToListener).on(eq("http://www.google.fr"), notNull());

        reset(beforeAllListener, afterAllListener, beforeToListener, afterToListener, beforeListener, afterListener);

        eventDriver.navigate().to("http://www.google.fr");

        verify(beforeAllListener)
                .on(eq("http://www.google.fr"), notNull(), isNull());
        verify(afterAllListener)
                .on(eq("http://www.google.fr"), notNull(), isNull());
        verify(beforeToListener).on(eq("http://www.google.fr"), notNull());
        verify(afterToListener).on(eq("http://www.google.fr"), notNull());

        reset(beforeAllListener, afterAllListener, beforeToListener, afterToListener, beforeListener, afterListener);

        eventDriver.navigate().back();

        verify(beforeAllListener).on(isNull(), notNull(), eq(NavigateAllListener.Direction.BACK));
        verify(afterAllListener).on(isNull(), notNull(), eq(NavigateAllListener.Direction.BACK));
        verify(beforeListener).on(notNull());
        verify(afterListener).on(notNull());

        reset(beforeAllListener, afterAllListener, beforeToListener, afterToListener, beforeListener, afterListener);

        eventDriver.navigate().forward();

        verify(beforeAllListener).on(isNull(), notNull(), eq(NavigateAllListener.Direction.FORWARD));
        verify(afterAllListener).on(isNull(), notNull(), eq(NavigateAllListener.Direction.FORWARD));
        verify(beforeListener).on(notNull());
        verify(afterListener).on(notNull());

        reset(beforeAllListener, afterAllListener, beforeToListener, afterToListener, beforeListener, afterListener);

        eventDriver.navigate().refresh();

        verify(beforeAllListener).on(isNull(), notNull(), eq(NavigateAllListener.Direction.REFRESH));
        verify(afterAllListener).on(isNull(), notNull(), eq(NavigateAllListener.Direction.REFRESH));
        verify(beforeListener).on(notNull());
        verify(afterListener).on(notNull());

        reset(beforeAllListener, afterAllListener, beforeToListener, afterToListener, beforeListener, afterListener);

        eventDriver.executeScript("test");

        verify(beforeScriptListener).on(eq("test"), notNull());
        verify(afterScriptListener).on(eq("test"), notNull());

        reset(beforeAllListener, afterAllListener, beforeToListener, afterToListener, beforeListener, afterListener);

        when(driver.findElement(any())).thenThrow(IllegalStateException.class);
        assertThatThrownBy(() -> eventDriver.findElement(By.cssSelector(".test")))
                .isExactlyInstanceOf(IllegalStateException.class);

        verify(exceptionListener).on(isA(IllegalStateException.class), notNull());

        reset(exceptionListener);
        eventsRegistry.close();

        assertThatThrownBy(() -> eventDriver.findElement(By.cssSelector(".test")))
                .isExactlyInstanceOf(IllegalStateException.class);

        verify(exceptionListener, never()).on(isA(IllegalStateException.class), notNull());
    }

    @Test
    public void testAdapterHashcodeEquals() {
        EventListener listener = mock(EventListener.class);
        EventListener otherListener = mock(EventListener.class);

        assertThat(new EventAdapter(listener, instantiator)).isEqualTo(new EventAdapter(listener, instantiator));
        assertThat(new EventAdapter(listener, instantiator).hashCode())
                .isEqualTo(new EventAdapter(listener, instantiator).hashCode());

        assertThat(new EventAdapter(listener, instantiator))
                .isNotEqualTo(new EventAdapter(otherListener, instantiator));
        assertThat(new EventAdapter(listener, instantiator)).isNotEqualTo("OtherType");

        EventAdapter instance = new EventAdapter(mock(EventListener.class), instantiator);
        assertThat(instance).isEqualTo(instance);
    }

    private static final class ElementMatcher implements ArgumentMatcher<FluentWebElement> {
        private final WebElement element;

        ElementMatcher(WebElement element) {
            this.element = element;
        }

        @Override
        public boolean matches(FluentWebElement argument) {
            if (argument == null && element == null) {
                return true;
            }
            if (argument == null) {
                return false;
            }
            WebElement argElement = argument.getElement();
            while (argElement instanceof WrapsElement) {
                argElement = ((WrapsElement) argElement).getWrappedElement();
            }
            return argElement == element;
        }
    }

    private abstract static class JavascriptWebDriver implements WebDriver, JavascriptExecutor { // NOPMD AbstractNaming

    }
}
