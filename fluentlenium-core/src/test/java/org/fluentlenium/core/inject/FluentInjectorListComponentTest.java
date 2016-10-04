package org.fluentlenium.core.inject;

import org.assertj.core.api.Assertions;
import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.core.components.ComponentsManager;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.proxy.LocatorProxies;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ByIdOrName;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FluentInjectorListComponentTest {

    @Mock
    private WebDriver webDriver;

    private FluentAdapter fluentAdapter;

    private FluentInjector injector;

    @Before
    public void before() {
        fluentAdapter = new FluentAdapter();
        fluentAdapter.initFluent(webDriver);

        injector = new FluentInjector(fluentAdapter, null, new ComponentsManager(fluentAdapter),
                new DefaultContainerInstanciator(fluentAdapter));
    }

    public static class Component {
        private final WebElement foundElement;
        private FluentWebElement element;

        public Component(final WebElement webElement) {
            this.foundElement = webElement;
        }
    }

    public static class Container {
        private List<Component> components;
    }

    @Test
    public void testListComponent() {
        final Container container = new Container();

        final WebElement component1 = Mockito.mock(WebElement.class);
        final WebElement component2 = Mockito.mock(WebElement.class);
        final WebElement component3 = Mockito.mock(WebElement.class);
        final WebElement componentElement1 = Mockito.mock(WebElement.class);
        final WebElement componentElement2 = Mockito.mock(WebElement.class);
        final WebElement componentElement3 = Mockito.mock(WebElement.class);

        when(webDriver.findElements(new ByIdOrName("components"))).thenReturn(Arrays.asList(component1, component2, component3));
        when(component1.findElement(new ByIdOrName("element"))).thenReturn(componentElement1);
        when(component2.findElement(new ByIdOrName("element"))).thenReturn(componentElement2);
        when(component3.findElement(new ByIdOrName("element"))).thenReturn(componentElement3);

        injector.inject(container);

        Assertions.assertThat(container.components).isNotNull();
        Assertions.assertThat(LocatorProxies.isLoaded(container.components)).isFalse();

        for (final Component component : container.components) {
            Assertions.assertThat(component.element).isNotNull();
        }

        //verify(webDriver).findElements(new ByIdOrName("components"));
    }

}
