package org.fluentlenium.core.inject;

import org.assertj.core.api.Assertions;
import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.components.ComponentsManager;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ByIdOrName;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FluentInjectorComponentTest {

    @Mock
    private WebDriver webDriver;

    private FluentAdapter fluentAdapter;

    private FluentInjector injector;

    @Before
    public void before() {
        fluentAdapter = new FluentAdapter();
        fluentAdapter.initFluent(webDriver);

        injector = new FluentInjector(fluentAdapter, new ComponentsManager(fluentAdapter), new DefaultContainerInstanciator(fluentAdapter));
    }

    public static class SomeChildComponent extends FluentWebElement {
        @Parent
        private SomeComponent parentComponent;

        private WebElement webElement;

        private List<WebElement> webElements;

        private FluentWebElement fluentWebElement;

        private List<FluentWebElement> fluentList;

        @NoInject
        private WebElement noInject;

        public SomeChildComponent(WebElement webElement, FluentControl fluentControl, ComponentInstantiator instantiator) {
            super(webElement, fluentControl, instantiator);
        }
    }

    public static class SomeComponent extends FluentWebElement {
        @Parent
        private Container parentContainer;

        private SomeChildComponent childComponent;

        public SomeComponent(WebElement webElement, FluentControl fluentControl, ComponentInstantiator instantiator) {
            super(webElement, fluentControl, instantiator);
        }
    }

    public static class Container {
        private SomeComponent component;
    }

    @Test
    public void test() {
        Container container = new Container();

        WebElement component = Mockito.mock(WebElement.class);
        WebElement childComponent = Mockito.mock(WebElement.class);
        WebElement childWebElement = Mockito.mock(WebElement.class);

        when(webDriver.findElement(new ByIdOrName("component"))).thenReturn(component);
        when(component.findElement(new ByIdOrName("childComponent"))).thenReturn(childComponent);
        when(childComponent.findElement(new ByIdOrName("webElement"))).thenReturn(childWebElement);

        injector.inject(container);

        Assertions.assertThat(container.component).isNotNull();
        Assertions.assertThat(container.component.getElement()).isEqualTo(component);

        Assertions.assertThat(container.component.childComponent).isNotNull();
        Assertions.assertThat(container.component.childComponent.getElement()).isEqualTo(childComponent);

        Assertions.assertThat(container.component.parentContainer).isSameAs(container);
        Assertions.assertThat(container.component.childComponent.parentComponent).isSameAs(container.component);

        Assertions.assertThat(container.component.childComponent.webElement).isNotNull();
        Assertions.assertThat(container.component.childComponent.webElement).isEqualTo(childWebElement);
        Assertions.assertThat(container.component.childComponent.webElements).isNotNull();
        Assertions.assertThat(container.component.childComponent.webElements).isNotInstanceOf(FluentList.class);
        Assertions.assertThat(container.component.childComponent.fluentWebElement).isNotNull();
        Assertions.assertThat(container.component.childComponent.fluentList).isInstanceOf(FluentList.class);
        Assertions.assertThat(container.component.childComponent.noInject).isNull();
    }

    @Test
    public void testLazyness() {
        Container container = new Container();

        WebElement component = Mockito.mock(WebElement.class);
        WebElement childComponent = Mockito.mock(WebElement.class);
        WebElement childWebElement = Mockito.mock(WebElement.class);

        when(webDriver.findElement(new ByIdOrName("component"))).thenReturn(component);
        when(component.findElement(new ByIdOrName("childComponent"))).thenReturn(childComponent);
        when(childComponent.findElement(new ByIdOrName("webElement"))).thenReturn(childWebElement);

        injector.inject(container);

        verifyZeroInteractions(component, childComponent, childWebElement);

        Assertions.assertThat(container.component.childComponent.webElement).isEqualTo(childWebElement);


        verify(webDriver).findElement(new ByIdOrName("component"));
        verify(component).findElement(new ByIdOrName("childComponent"));
        verify(childComponent).findElement(new ByIdOrName("webElement"));
    }


}
