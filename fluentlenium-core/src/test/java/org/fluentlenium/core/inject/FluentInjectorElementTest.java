package org.fluentlenium.core.inject;

import org.assertj.core.api.Assertions;
import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.components.ComponentsManager;
import org.fluentlenium.core.components.DefaultComponentInstantiator;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentListImpl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class FluentInjectorElementTest {

    @Mock
    private WebDriver webDriver;

    private FluentAdapter fluentAdapter;

    private FluentInjector injector;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);

        fluentAdapter = new FluentAdapter();
        fluentAdapter.initFluent(webDriver);

        injector = new FluentInjector(fluentAdapter, new ComponentsManager(webDriver));
    }

    @After
    public void after() {
        reset(webDriver);
    }

    public static class FluentWebElementSubClass extends FluentWebElement {
        public FluentWebElementSubClass(WebElement webElement, WebDriver driver, ComponentInstantiator instantiator) {
            super(webElement, driver, instantiator);
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
        private final WebDriver webDriver;

        public WebElementDriverWrapper(WebElement element, WebDriver webDriver) {
            this.element = element;
            this.webDriver = webDriver;
        }

        public WebElement getElement() {
            return element;
        }

        public WebDriver getWebDriver() {
            return webDriver;
        }
    }

    public static class FluentWebElementContainer {
        FluentWebElement element;
    }

    private static FluentWebElement existingElement = Mockito.mock(FluentWebElement.class);
    public static class ExistingFluentWebElementContainer {
        FluentWebElement element = existingElement;
    }

    public static class FluentWebElementSubClassContainer {
        FluentWebElementSubClass element;
    }

    public static class WebElementWrapperContainer {
        WebElementWrapper element;
    }

    public static class WebElementDriverWrapperContainer {
        WebElementDriverWrapper element;
    }

    public static class FluentWebElementListContainer {
        List<FluentWebElement> element;
    }

    public static class FluentWebElementSubClassListContainer {
        List<FluentWebElementSubClass> element;
    }

    public static class WebElementWrapperListContainer {
        List<WebElementWrapper> element;
    }

    public static class WebElementDriverWrapperListContainer {
        List<WebElementDriverWrapper> element;
    }

    @Test
    public void testFluentWebElement() {
        FluentWebElementContainer container = new FluentWebElementContainer();

        injector.inject(container);

        WebElement webElement = mock(WebElement.class);
        when(webElement.getTagName()).thenReturn("h1");

        when(webDriver.findElement(any(By.class))).thenReturn(webElement);

        Assertions.assertThat(container.element.getTagName()).isEqualTo("h1");
        Assertions.assertThat(container.element).isExactlyInstanceOf(FluentWebElement.class);
        Assertions.assertThat(container.element.getElement()).isInstanceOf(WebElement.class);
    }

    /**
     * Existing variables should not be injected.
     */
    @Test
    public void testExistingFluentWebElement() {
        ExistingFluentWebElementContainer container = new ExistingFluentWebElementContainer();

        injector.inject(container);

        WebElement webElement = mock(WebElement.class);
        when(webElement.getTagName()).thenReturn("h1");

        when(webDriver.findElement(any(By.class))).thenReturn(webElement);

        Assertions.assertThat(container.element).isSameAs(existingElement);
    }

    @Test
    public void testFluentWebElementExtends() {
        FluentWebElementSubClassContainer container = new FluentWebElementSubClassContainer();

        injector.inject(container);

        WebElement webElement = mock(WebElement.class);
        when(webElement.getTagName()).thenReturn("h1");

        when(webDriver.findElement(any(By.class))).thenReturn(webElement);

        Assertions.assertThat(container.element.getTagName()).isEqualTo("h1");
        Assertions.assertThat(container.element).isExactlyInstanceOf(FluentWebElementSubClass.class);
        Assertions.assertThat(container.element.getElement()).isInstanceOf(WebElement.class);
    }

    @Test
    public void testWebElementWrapper() {
        WebElementWrapperContainer container = new WebElementWrapperContainer();

        WebElement webElement = mock(WebElement.class);
        when(webElement.getTagName()).thenReturn("h1");

        when(webDriver.findElement(any(By.class))).thenReturn(webElement);

        injector.inject(container);

        Assertions.assertThat(container.element).isExactlyInstanceOf(WebElementWrapper.class);
        Assertions.assertThat(container.element.getElement()).isInstanceOf(WebElement.class);
    }

    @Test
    public void testWebElementDriverWrapper() {
        WebElementDriverWrapperContainer container = new WebElementDriverWrapperContainer();

        WebElement webElement = mock(WebElement.class);
        when(webElement.getTagName()).thenReturn("h1");

        when(webDriver.findElement(any(By.class))).thenReturn(webElement);

        injector.inject(container);

        Assertions.assertThat(container.element).isExactlyInstanceOf(WebElementDriverWrapper.class);
        Assertions.assertThat(container.element.getElement()).isInstanceOf(WebElement.class);
        Assertions.assertThat(container.element.getWebDriver()).isSameAs(webDriver);
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

        Assertions.assertThat(container.element).hasSize(2);
        Assertions.assertThat(container.element).isInstanceOf(FluentList.class);

        Assertions.assertThat(container.element.get(0).getTagName()).isEqualTo("h1");
        Assertions.assertThat(container.element.get(0)).isExactlyInstanceOf(FluentWebElement.class);
        Assertions.assertThat(container.element.get(0).getElement()).isInstanceOf(WebElement.class);

        Assertions.assertThat(container.element.get(1).getTagName()).isEqualTo("h2");
        Assertions.assertThat(container.element.get(1)).isExactlyInstanceOf(FluentWebElement.class);
        Assertions.assertThat(container.element.get(1).getElement()).isInstanceOf(WebElement.class);
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

        Assertions.assertThat(container.element).hasSize(2);
        Assertions.assertThat(container.element).isInstanceOf(FluentList.class);

        Assertions.assertThat(container.element.get(0).getTagName()).isEqualTo("h1");
        Assertions.assertThat(container.element.get(0)).isExactlyInstanceOf(FluentWebElementSubClass.class);
        Assertions.assertThat(container.element.get(0).getElement()).isInstanceOf(WebElement.class);

        Assertions.assertThat(container.element.get(1).getTagName()).isEqualTo("h2");
        Assertions.assertThat(container.element.get(1)).isExactlyInstanceOf(FluentWebElementSubClass.class);
        Assertions.assertThat(container.element.get(1).getElement()).isInstanceOf(WebElement.class);
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

        Assertions.assertThat(container.element).hasSize(2);
        Assertions.assertThat(container.element).isNotInstanceOf(FluentList.class);


        Assertions.assertThat(container.element.get(0)).isExactlyInstanceOf(WebElementWrapper.class);
        Assertions.assertThat(container.element.get(0).getElement()).isInstanceOf(WebElement.class);
        Assertions.assertThat(container.element.get(0).getElement().getTagName()).isEqualTo("h1");

        Assertions.assertThat(container.element.get(1)).isExactlyInstanceOf(WebElementWrapper.class);
        Assertions.assertThat(container.element.get(1).getElement()).isInstanceOf(WebElement.class);
        Assertions.assertThat(container.element.get(1).getElement().getTagName()).isEqualTo("h2");
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

        Assertions.assertThat(container.element).hasSize(2);
        Assertions.assertThat(container.element).isNotInstanceOf(FluentList.class);


        Assertions.assertThat(container.element.get(0)).isExactlyInstanceOf(WebElementDriverWrapper.class);
        Assertions.assertThat(container.element.get(0).getElement()).isInstanceOf(WebElement.class);
        Assertions.assertThat(container.element.get(0).getElement().getTagName()).isEqualTo("h1");
        Assertions.assertThat(container.element.get(0).getWebDriver()).isSameAs(webDriver);


        Assertions.assertThat(container.element.get(1)).isExactlyInstanceOf(WebElementDriverWrapper.class);
        Assertions.assertThat(container.element.get(1).getElement()).isInstanceOf(WebElement.class);
        Assertions.assertThat(container.element.get(1).getElement().getTagName()).isEqualTo("h2");
        Assertions.assertThat(container.element.get(1).getWebDriver()).isSameAs(webDriver);
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

        Assertions.assertThat(container.element).hasSize(2);
        Assertions.assertThat(container.element).isNotInstanceOf(FluentList.class);

        Assertions.assertThat(container.element.get(0)).isExactlyInstanceOf(WebElementDriverWrapper.class);
        Assertions.assertThat(container.element.get(0).getElement()).isInstanceOf(WebElement.class);
        Assertions.assertThat(container.element.get(0).getElement().getTagName()).isEqualTo("h1");
        Assertions.assertThat(container.element.get(0).getWebDriver()).isSameAs(webDriver);

        Assertions.assertThat(container.element.get(1)).isExactlyInstanceOf(WebElementDriverWrapper.class);
        Assertions.assertThat(container.element.get(1).getElement()).isInstanceOf(WebElement.class);
        Assertions.assertThat(container.element.get(1).getElement().getTagName()).isEqualTo("h2");
        Assertions.assertThat(container.element.get(1).getWebDriver()).isSameAs(webDriver);
    }

    @Test
    public void testDeprecatedMethod() {
        FluentInjector injectorSpy = spy(injector);

        injectorSpy.createPage(Object.class);

        verify(injectorSpy).newInstance(Object.class);
    }

    @Test
    public void testInjectArray() {
        Object container1 = new Object();
        Object container2 = new Object();
        Object container3 = new Object();

        FluentInjector injectorSpy = spy(injector);

        injectorSpy.inject(container1, container2, container3);

        verify(injectorSpy).inject(container1);
        verify(injectorSpy).inject(container2);
        verify(injectorSpy).inject(container3);

        injectorSpy.release();
    }

}
