package org.fluentlenium.core.hook;

import com.google.common.base.Suppliers;
import org.assertj.core.api.Assertions;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.components.DefaultComponentInstantiator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.annotation.Annotation;

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

    @Before
    public void before() {
        instantiator = new DefaultComponentInstantiator(webDriver);
        hook = new BaseHook<>(webDriver, instantiator, Suppliers.ofInstance(element),  Suppliers.ofInstance(locator), options);
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
        assertThat(hook.getWebDriver()).isSameAs(webDriver);
        assertThat(hook.getInstantiator()).isSameAs(instantiator);
        assertThat(hook.getElement()).isSameAs(element);
        assertThat(hook.getElementLocator()).isSameAs(locator);
        assertThat(hook.getOptions()).isSameAs(options);
    }

    @Test
    public void testNoOptionHook() {
        final Object defaultOptions = new Object();

        BaseHook noOptionHook = new BaseHook<Object>(webDriver, instantiator, Suppliers.ofInstance(element),  Suppliers.ofInstance(locator), null) {
            @Override
            protected Object newOptions() {
                return defaultOptions;
            }
        };

        assertThat(noOptionHook.getOptions()).isSameAs(defaultOptions);
    }


    @Test
    public void testNoOptionHookWithoutDefault() {

        BaseHook noOptionHook = new BaseHook<>(webDriver, instantiator, Suppliers.ofInstance(element),  Suppliers.ofInstance(locator), null);

        assertThat(noOptionHook.getOptions()).isNull();
    }
}
