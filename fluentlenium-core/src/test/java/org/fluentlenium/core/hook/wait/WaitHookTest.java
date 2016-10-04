package org.fluentlenium.core.hook.wait;

import com.google.common.base.Suppliers;
import org.assertj.core.api.ThrowableAssert;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.components.DefaultComponentInstantiator;
import org.fluentlenium.core.wait.FluentWait;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WaitHookTest {

    @Mock
    private FluentControl fluentControl;

    @Mock
    private WebElement element;

    @Mock
    private ElementLocator locator;

    private ComponentInstantiator instantiator;

    private WaitHook waitHook;

    private FluentWait wait;

    @Before
    public void before() {
        instantiator = new DefaultComponentInstantiator(fluentControl);
        wait = new FluentWait(fluentControl);

        when(fluentControl.await()).thenReturn(wait);
        when(element.isEnabled()).thenReturn(true);
        when(element.isDisplayed()).thenReturn(true);

        WaitHookOptions waitHookOptions = new WaitHookOptions();
        waitHookOptions.setAtMost(100L);
        waitHookOptions.setTimeUnit(TimeUnit.MILLISECONDS);
        waitHookOptions.setPollingEvery(10L);

        waitHook = new WaitHook(fluentControl, instantiator, Suppliers.ofInstance(element), Suppliers.ofInstance(locator),
                Suppliers.ofInstance("toString"), waitHookOptions);
    }

    @Test
    public void testElementNotFound() {
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                waitHook.findElement();
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void testElementListNotFound() {
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                waitHook.findElements();
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void testElementFound() {
        WebElement childElement = mock(WebElement.class);

        when(locator.findElement()).thenReturn(childElement);

        WebElement found = waitHook.findElement();
        assertThat(found).isSameAs(childElement);
    }

    @Test
    public void testElementListFound() {
        WebElement element1 = mock(WebElement.class);
        WebElement element2 = mock(WebElement.class);
        WebElement element3 = mock(WebElement.class);

        when(locator.findElements()).thenReturn(Arrays.asList(element1, element2, element3));

        List<WebElement> found = waitHook.findElements();
        assertThat(found).containsExactly(element1, element2, element3);
    }

    @Test
    public void testElementClick() {
        WebElement childElement = mock(WebElement.class);

        when(locator.findElement()).thenReturn(childElement);

        waitHook.click();
        verify(element).click();
    }

    @Test
    public void testElementSendKeys() {
        WebElement childElement = mock(WebElement.class);

        when(locator.findElement()).thenReturn(childElement);

        waitHook.sendKeys("abc");
        verify(element).sendKeys("abc");
    }

    @Test
    public void testElementSubmit() {
        WebElement childElement = mock(WebElement.class);

        when(locator.findElement()).thenReturn(childElement);

        waitHook.submit();
        verify(element).submit();
    }

    @Test
    public void testElementClear() {
        WebElement childElement = mock(WebElement.class);

        when(locator.findElement()).thenReturn(childElement);

        waitHook.clear();
        verify(element).clear();
    }

    @Test
    public void testDefaultOptions() {
        WaitHook defaultWaitHook = new WaitHook(fluentControl, instantiator, Suppliers.ofInstance(element),
                Suppliers.ofInstance(locator), Suppliers.ofInstance("toString"), null);

        assertThat(defaultWaitHook.getOptions()).isEqualToComparingFieldByField(new WaitHookOptions());
    }
}
