package org.fluentlenium.core.hook;

import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.core.components.DefaultComponentInstantiator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link BaseHook}.
 */
@RunWith(MockitoJUnitRunner.class)
public class BaseHookTest {
    @Mock
    private WebDriver webDriver;

    @Mock(extraInterfaces = Locatable.class)
    private WebElement element;

    @Mock
    private ElementLocator locator;

    @Mock
    private Object options;

    private DefaultComponentInstantiator instantiator;

    private BaseHook<?> hook;

    private FluentAdapter fluentAdapter;

    @Before
    public void before() {
        fluentAdapter = new FluentAdapter();
        fluentAdapter.initFluent(webDriver);
        instantiator = new DefaultComponentInstantiator(fluentAdapter);

        hook = new BaseHook<>(fluentAdapter, instantiator, () -> element, () -> locator, () -> "toString", options);
    }

    @Test
    public void testDelegatesElement() {
        hook.click();
        verify(element).click();
    }

    @Test
    public void testDelegatesLocator() {
        hook.findElement();
        verify(element, never()).findElement(any(By.class));
        verify(locator).findElement();
    }

    @Test
    public void testGetters() {
        assertThat(hook.getDriver()).isSameAs(webDriver);
        assertThat(hook.getInstantiator()).isSameAs(instantiator);
        assertThat(hook.getElement()).isSameAs(element);
        assertThat(hook.getWrappedElement()).isSameAs(element);
        assertThat(hook.getElementLocator()).isSameAs(locator);
        assertThat(hook.getOptions()).isSameAs(options);
    }

    @Test
    public void testNoOptionHook() {
        Object defaultOptions = new Object();

        BaseHook noOptionHook = new BaseHook<Object>(fluentAdapter, instantiator, () -> element, () -> locator, () -> "hook",
                null) {
            @Override
            protected Object newOptions() {
                return defaultOptions;
            }
        };

        assertThat(noOptionHook.getOptions()).isSameAs(defaultOptions);
    }

    @Test
    public void testNoOptionHookWithoutDefault() {
        BaseHook noOptionHook = new BaseHook<>(fluentAdapter, instantiator, () -> element, () -> locator, () -> "hook", null);

        assertThat(noOptionHook.getOptions()).isNull();
    }

    @Test
    public void shouldReturnUnderlyingElementCoordinates() {
        Coordinates coordinates = mock(Coordinates.class);
        when(((Locatable) element).getCoordinates()).thenReturn(coordinates);

        assertThat(hook.getCoordinates()).isSameAs(coordinates);
    }
}
