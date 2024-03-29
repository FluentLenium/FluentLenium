package io.fluentlenium.core.inject;

import io.fluentlenium.adapter.FluentAdapter;
import io.fluentlenium.core.FluentControl;
import io.fluentlenium.core.components.ComponentInstantiator;
import io.fluentlenium.core.components.ComponentsManager;
import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentListImpl;
import io.fluentlenium.core.domain.FluentWebElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FluentInjectorElementTest {

    private static final FluentWebElement EXISTING_ELEMENT = mock(FluentWebElement.class);

    @Mock
    private WebDriver webDriver;
    private FluentAdapter fluentAdapter;
    private FluentInjector injector;

    @Before
    public void before() {
        fluentAdapter = new FluentAdapter();
        fluentAdapter.initFluent(webDriver);

        injector = new FluentInjector(fluentAdapter, null, new ComponentsManager(fluentAdapter),
                new DefaultContainerInstantiator(fluentAdapter));
    }

    @After
    public void after() {
        reset(webDriver);
    }

    public static class FluentWebElementSubClass extends FluentWebElement {
        public FluentWebElementSubClass(WebElement webElement, FluentControl fluentControl, ComponentInstantiator instantiator) {
            super(webElement, fluentControl, instantiator);
        }
    }

    public static class WebElementWrapper {
        private final WebElement element;

        public WebElementWrapper(WebElement element) {
            this.element = element;
        }

        public WebElement getElement() {
            return element;
        }
    }

    public static class WebElementDriverWrapper {
        private final WebElement element;
        private final FluentControl fluentControl;

        public WebElementDriverWrapper(WebElement element, FluentControl fluentControl) {
            this.element = element;
            this.fluentControl = fluentControl;
        }

        public WebElement getElement() {
            return element;
        }

        public FluentControl getFluentControl() {
            return fluentControl;
        }
    }

    public static class FluentWebElementContainer {
        private FluentWebElement element;
    }

    public static class ExistingFluentWebElementContainer {
        private final FluentWebElement element = EXISTING_ELEMENT;
    }

    public static class FluentWebElementSubClassContainer {
        private FluentWebElementSubClass element;
    }

    public static class WebElementWrapperContainer {
        private WebElementWrapper element;
    }

    public static class WebElementDriverWrapperContainer {
        private WebElementDriverWrapper element;
    }

    public static class FluentWebElementListContainer {
        private List<FluentWebElement> element;
    }

    public static class FluentWebElementSubClassListContainer {
        private List<FluentWebElementSubClass> element;
    }

    public static class WebElementWrapperListContainer {
        private List<WebElementWrapper> element;
    }

    public static class WebElementDriverWrapperListContainer {
        private List<WebElementDriverWrapper> element;
    }

    public static class FluentListSubClass<T extends FluentWebElementSubClass> extends FluentListImpl<T> {
        public FluentListSubClass(Class<T> componentClass, List<T> list, FluentControl fluentControl,
                                  ComponentInstantiator instantiator) {
            super(componentClass, list, fluentControl, instantiator);
        }
    }

    public static class ListSubClassContainer {
        private FluentListSubClass<FluentWebElementSubClass> element;
    }

    @Test
    public void testFluentWebElement() {
        FluentWebElementContainer container = new FluentWebElementContainer();

        injector.inject(container);

        WebElement webElement = mock(WebElement.class);
        when(webElement.getTagName()).thenReturn("h1");

        when(webDriver.findElement(any(By.class))).thenReturn(webElement);

        assertThat(container.element.tagName()).isEqualTo("h1");
        assertThat(container.element).isExactlyInstanceOf(FluentWebElement.class);
        assertThat(container.element.getElement()).isInstanceOf(WebElement.class);
    }

    /**
     * Existing variables should not be injected.
     */
    @Test
    public void testExistingFluentWebElement() {
        ExistingFluentWebElementContainer container = new ExistingFluentWebElementContainer();

        injector.inject(container);

        WebElement webElement = mock(WebElement.class);

        when(webDriver.findElement(any(By.class))).thenReturn(webElement);

        assertThat(container.element).isSameAs(EXISTING_ELEMENT);
    }

    @Test
    public void testFluentWebElementExtends() {
        FluentWebElementSubClassContainer container = new FluentWebElementSubClassContainer();

        injector.inject(container);

        WebElement webElement = mock(WebElement.class);
        when(webElement.getTagName()).thenReturn("h1");

        when(webDriver.findElement(any(By.class))).thenReturn(webElement);

        assertThat(container.element.tagName()).isEqualTo("h1");
        assertThat(container.element).isExactlyInstanceOf(FluentWebElementSubClass.class);
        assertThat(container.element.getElement()).isInstanceOf(WebElement.class);
    }

    @Test
    public void testWebElementWrapper() {
        WebElementWrapperContainer container = new WebElementWrapperContainer();

        WebElement webElement = mock(WebElement.class);

        when(webDriver.findElement(any(By.class))).thenReturn(webElement);

        injector.inject(container);

        assertThat(container.element).isExactlyInstanceOf(WebElementWrapper.class);
        assertThat(container.element.getElement()).isInstanceOf(WebElement.class);
    }

    @Test
    public void testWebElementDriverWrapper() {
        WebElementDriverWrapperContainer container = new WebElementDriverWrapperContainer();

        WebElement webElement = mock(WebElement.class);

        when(webDriver.findElement(any(By.class))).thenReturn(webElement);

        injector.inject(container);

        assertThat(container.element).isExactlyInstanceOf(WebElementDriverWrapper.class);
        assertThat(container.element.getElement()).isInstanceOf(WebElement.class);
        assertThat(container.element.getFluentControl()).isSameAs(fluentAdapter);
    }

    @Test
    public void testFluentWebElementList() {
        FluentWebElementListContainer container = new FluentWebElementListContainer();

        injector.inject(container);

        WebElement webElement = mock(WebElement.class);
        when(webElement.getTagName()).thenReturn("h1");

        WebElement webElement2 = mock(WebElement.class);
        when(webElement2.getTagName()).thenReturn("h2");

        ArrayList<WebElement> webElements = new ArrayList<>();
        webElements.add(webElement);
        webElements.add(webElement2);

        when(webDriver.findElements(any(By.class))).thenReturn(webElements);

        assertThat(container.element).hasSize(2).isInstanceOf(FluentList.class);

        assertThat(container.element.get(0).tagName()).isEqualTo("h1");
        assertThat(container.element.get(0)).isExactlyInstanceOf(FluentWebElement.class);
        assertThat(container.element.get(0).getElement()).isInstanceOf(WebElement.class);

        assertThat(container.element.get(1).tagName()).isEqualTo("h2");
        assertThat(container.element.get(1)).isExactlyInstanceOf(FluentWebElement.class);
        assertThat(container.element.get(1).getElement()).isInstanceOf(WebElement.class);
    }

    @Test
    public void testFluentWebElementExtendsList() {
        FluentWebElementSubClassListContainer container = new FluentWebElementSubClassListContainer();

        injector.inject(container);

        WebElement webElement = mock(WebElement.class);
        when(webElement.getTagName()).thenReturn("h1");

        WebElement webElement2 = mock(WebElement.class);
        when(webElement2.getTagName()).thenReturn("h2");

        ArrayList<WebElement> webElements = new ArrayList<>();
        webElements.add(webElement);
        webElements.add(webElement2);

        when(webDriver.findElements(any(By.class))).thenReturn(webElements);

        assertThat(container.element).hasSize(2).isInstanceOf(FluentList.class);

        assertThat(container.element.get(0).tagName()).isEqualTo("h1");
        assertThat(container.element.get(0)).isExactlyInstanceOf(FluentWebElementSubClass.class);
        assertThat(container.element.get(0).getElement()).isInstanceOf(WebElement.class);

        assertThat(container.element.get(1).tagName()).isEqualTo("h2");
        assertThat(container.element.get(1)).isExactlyInstanceOf(FluentWebElementSubClass.class);
        assertThat(container.element.get(1).getElement()).isInstanceOf(WebElement.class);
    }

    @Test
    public void testListSubClass() {
        ListSubClassContainer container = new ListSubClassContainer();

        injector.inject(container);

        WebElement webElement = mock(WebElement.class);
        when(webElement.getTagName()).thenReturn("h1");

        WebElement webElement2 = mock(WebElement.class);
        when(webElement2.getTagName()).thenReturn("h2");

        ArrayList<WebElement> webElements = new ArrayList<>();
        webElements.add(webElement);
        webElements.add(webElement2);

        when(webDriver.findElements(any(By.class))).thenReturn(webElements);

        assertThat(container.element).hasSize(2).isExactlyInstanceOf(FluentListSubClass.class);

        assertThat(container.element.get(0).tagName()).isEqualTo("h1");
        assertThat(container.element.get(0)).isExactlyInstanceOf(FluentWebElementSubClass.class);
        assertThat(container.element.get(0).getElement()).isInstanceOf(WebElement.class);

        assertThat(container.element.get(1).tagName()).isEqualTo("h2");
        assertThat(container.element.get(1)).isExactlyInstanceOf(FluentWebElementSubClass.class);
        assertThat(container.element.get(1).getElement()).isInstanceOf(WebElement.class);
    }

    @Test
    public void testWebElementWrapperList() {
        WebElementWrapperListContainer container = new WebElementWrapperListContainer();

        injector.inject(container);

        WebElement webElement = mock(WebElement.class);
        when(webElement.getTagName()).thenReturn("h1");

        WebElement webElement2 = mock(WebElement.class);
        when(webElement2.getTagName()).thenReturn("h2");

        ArrayList<WebElement> webElements = new ArrayList<>();
        webElements.add(webElement);
        webElements.add(webElement2);

        when(webDriver.findElements(any(By.class))).thenReturn(webElements);

        assertThat(container.element).hasSize(2).isNotInstanceOf(FluentList.class);

        assertThat(container.element.get(0)).isExactlyInstanceOf(WebElementWrapper.class);
        assertThat(container.element.get(0).getElement()).isInstanceOf(WebElement.class);
        assertThat(container.element.get(0).getElement().getTagName()).isEqualTo("h1");

        assertThat(container.element.get(1)).isExactlyInstanceOf(WebElementWrapper.class);
        assertThat(container.element.get(1).getElement()).isInstanceOf(WebElement.class);
        assertThat(container.element.get(1).getElement().getTagName()).isEqualTo("h2");
    }

    @Test
    public void testWebElementDriverWrapperList() {
        WebElementDriverWrapperListContainer container = new WebElementDriverWrapperListContainer();

        injector.inject(container);

        WebElement webElement = mock(WebElement.class);
        when(webElement.getTagName()).thenReturn("h1");

        WebElement webElement2 = mock(WebElement.class);
        when(webElement2.getTagName()).thenReturn("h2");

        ArrayList<WebElement> webElements = new ArrayList<>();
        webElements.add(webElement);
        webElements.add(webElement2);

        when(webDriver.findElements(any(By.class))).thenReturn(webElements);

        assertThat(container.element).hasSize(2).isNotInstanceOf(FluentList.class);

        assertThat(container.element.get(0)).isExactlyInstanceOf(WebElementDriverWrapper.class);
        assertThat(container.element.get(0).getElement()).isInstanceOf(WebElement.class);
        assertThat(container.element.get(0).getElement().getTagName()).isEqualTo("h1");
        assertThat(container.element.get(0).getFluentControl()).isSameAs(fluentAdapter);

        assertThat(container.element.get(1)).isExactlyInstanceOf(WebElementDriverWrapper.class);
        assertThat(container.element.get(1).getElement()).isInstanceOf(WebElement.class);
        assertThat(container.element.get(1).getElement().getTagName()).isEqualTo("h2");
        assertThat(container.element.get(1).getFluentControl()).isSameAs(fluentAdapter);
    }

    @Test
    public void testNewInstance() {
        WebElement webElement = mock(WebElement.class);
        when(webElement.getTagName()).thenReturn("h1");

        WebElement webElement2 = mock(WebElement.class);
        when(webElement2.getTagName()).thenReturn("h2");

        ArrayList<WebElement> webElements = new ArrayList<>();
        webElements.add(webElement);
        webElements.add(webElement2);

        when(webDriver.findElements(any(By.class))).thenReturn(webElements);

        WebElementDriverWrapperListContainer container = injector.newInstance(WebElementDriverWrapperListContainer.class);

        assertThat(container.element).hasSize(2).isNotInstanceOf(FluentList.class);

        assertThat(container.element.get(0)).isExactlyInstanceOf(WebElementDriverWrapper.class);
        assertThat(container.element.get(0).getElement()).isInstanceOf(WebElement.class);
        assertThat(container.element.get(0).getElement().getTagName()).isEqualTo("h1");
        assertThat(container.element.get(0).getFluentControl()).isSameAs(fluentAdapter);

        assertThat(container.element.get(1)).isExactlyInstanceOf(WebElementDriverWrapper.class);
        assertThat(container.element.get(1).getElement()).isInstanceOf(WebElement.class);
        assertThat(container.element.get(1).getElement().getTagName()).isEqualTo("h2");
        assertThat(container.element.get(1).getFluentControl()).isSameAs(fluentAdapter);
    }

}
