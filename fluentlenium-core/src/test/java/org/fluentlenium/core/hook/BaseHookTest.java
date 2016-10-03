package org.fluentlenium.core.hook;

import com.google.common.base.Suppliers;
import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.core.components.DefaultComponentInstantiator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BaseHookTest {
    @Mock
    private WebDriver webDriver;

    @Mock
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
        fluentAdapter = new FluentAdapter(webDriver);
        instantiator = new DefaultComponentInstantiator(fluentAdapter);
        hook = new BaseHook<>(fluentAdapter, instantiator, Suppliers.ofInstance(element), Suppliers.ofInstance(locator),
                Suppliers.ofInstance("toString"), options);
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
        final Object defaultOptions = new Object();

        BaseHook noOptionHook = new BaseHook<Object>(fluentAdapter, instantiator, Suppliers.ofInstance(element),
                Suppliers.ofInstance(locator), Suppliers.ofInstance("hook"), null) {
            @Override
            protected Object newOptions() {
                return defaultOptions;
            }
        };

        assertThat(noOptionHook.getOptions()).isSameAs(defaultOptions);
    }

    @Test
    public void testNoOptionHookWithoutDefault() {

        BaseHook noOptionHook = new BaseHook<>(fluentAdapter, instantiator, Suppliers.ofInstance(element),
                Suppliers.ofInstance(locator), Suppliers.ofInstance("hook"), null);

        assertThat(noOptionHook.getOptions()).isNull();
    }
}
