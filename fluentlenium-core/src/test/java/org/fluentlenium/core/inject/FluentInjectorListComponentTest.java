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
import org.mockito.junit.MockitoJUnitRunner;
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

        public Component(WebElement webElement) {
            foundElement = webElement;
        }
    }

    public static class Container {
        private List<Component> components;
    }

    @Test
    public void testListComponent() {
        Container container = new Container();

        WebElement component1 = Mockito.mock(WebElement.class);
        WebElement component2 = Mockito.mock(WebElement.class);
        WebElement component3 = Mockito.mock(WebElement.class);

        when(webDriver.findElements(new ByIdOrName("components"))).thenReturn(Arrays.asList(component1, component2, component3));

        injector.inject(container);

        Assertions.assertThat(container.components).isNotNull();
        Assertions.assertThat(LocatorProxies.loaded(container.components)).isFalse();

        for (Component component : container.components) {
            Assertions.assertThat(component.element).isNotNull();
        }

        //verify(webDriver).findElements(new ByIdOrName("components"));
    }

}
