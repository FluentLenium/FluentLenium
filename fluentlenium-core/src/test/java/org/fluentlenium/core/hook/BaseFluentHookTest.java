package org.fluentlenium.core.hook;

import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.core.components.DefaultComponentInstantiator;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BaseFluentHookTest {
    @Mock
    private WebDriver webDriver;

    @Mock
    private WebElement element;

    @Mock
    private ElementLocator locator;

    @Mock
    private Object options;

    private DefaultComponentInstantiator instantiator;

    private BaseFluentHook<?> hook;

    private FluentAdapter fluentAdapter;

    @Before
    public void before() {
        fluentAdapter = new FluentAdapter();
        fluentAdapter.initFluent(webDriver);
        instantiator = spy(new DefaultComponentInstantiator(fluentAdapter));
        hook = new BaseFluentHook<>(fluentAdapter, instantiator, () -> element, () -> locator, () -> "toString", options);
    }

    @Test
    public void testFluentWebElement() {
        FluentWebElement fluentWebElement = hook.getFluentWebElement();
        verify(instantiator).newComponent(FluentWebElement.class, element);

        assertThat(fluentWebElement).isInstanceOf(FluentWebElement.class);
        assertThat(fluentWebElement.getElement()).isSameAs(element);
        assertThat(hook.toString()).isEqualTo("toString");
    }

}
