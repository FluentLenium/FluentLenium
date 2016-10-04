package org.fluentlenium.core.domain;

import org.assertj.core.api.ThrowableAssert;
import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.core.components.ComponentException;
import org.fluentlenium.core.components.ComponentsManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FluentWebElementTest {

    @Mock
    private LocatableElement element;

    @Mock
    private InputDevicesDriver driver;

    @Mock
    private Keyboard keyboard;

    @Mock
    private Mouse mouse;

    private FluentWebElement fluentElement;

    private ComponentsManager componentsManager;

    private FluentAdapter fluentAdapter;

    @Before
    public void before() {
        fluentAdapter = new FluentAdapter();
        fluentAdapter.initFluent(driver);

        when(driver.getMouse()).thenReturn(mouse);
        when(driver.getKeyboard()).thenReturn(keyboard);

        componentsManager = new ComponentsManager(fluentAdapter);

        fluentElement = new FluentWebElement(element, fluentAdapter, componentsManager);
    }

    @After
    public void adter() {
        reset(element, driver, keyboard, mouse);
    }

    @Test
    public void testClick() {
        fluentElement.click();
        verify(element).click();
    }

    @Test
    public void testAxes() {
        fluentElement.axes().parent();
    }

    @Test
    public void testConditions() {
        when(element.isEnabled()).thenReturn(true);
        assertThat(fluentElement.conditions().enabled()).isTrue();
    }

    @Test
    public void testMouse() {
        assertThat(fluentElement.mouse().click());
        verify(mouse).click(any(Coordinates.class));
    }

    @Test
    public void testKeyboard() {
        assertThat(fluentElement.keyboard().sendKeys("ABC"));
        verify(keyboard).sendKeys("ABC");
    }

    @Test
    public void testAs() {
        Component as = fluentElement.as(Component.class);
        assertThat(as.getElement()).isSameAs(element);
        assertThat(componentsManager.getComponents(element)).containsExactly(as);
    }

    @Test(expected = ComponentException.class)
    public void testAsInvalidClass() {
        fluentElement.as(InvalidComponent.class);
    }

    @Test
    public void testClear() {
        fluentElement.clear();
        verify(element).clear();
    }

    @Test
    public void testSubmit() {
        fluentElement.submit();
        verify(element).submit();
    }

    @Test
    public void testText() {
        fluentElement.write("abc");
        verify(element).clear();
        verify(element).sendKeys("abc");
    }

    @Test
    public void testGetName() {
        when(element.getAttribute("name")).thenReturn("test");
        assertThat(fluentElement.name()).isEqualTo("test");
    }

    @Test
    public void testGetAttribute() {
        when(element.getAttribute("attr")).thenReturn("test");
        assertThat(fluentElement.attribute("attr")).isEqualTo("test");
    }

    @Test
    public void testGetId() {
        when(element.getAttribute("id")).thenReturn("test");
        assertThat(fluentElement.id()).isEqualTo("test");
    }

    @Test
    public void testGetText() {
        when(element.getText()).thenReturn("test");
        assertThat(fluentElement.text()).isEqualTo("test");
    }

    @Test
    public void testGetTextContext() {
        when(element.getAttribute("textContent")).thenReturn("test");
        assertThat(fluentElement.textContent()).isEqualTo("test");
    }

    @Test
    public void testGetValue() {
        when(element.getAttribute("value")).thenReturn("test");
        assertThat(fluentElement.value()).isEqualTo("test");
    }

    @Test
    public void testIsDisplayed() {
        assertThat(fluentElement.displayed()).isFalse();
        when(element.isDisplayed()).thenReturn(true);
        assertThat(fluentElement.displayed()).isTrue();
    }

    @Test
    public void testIsEnabled() {
        assertThat(fluentElement.enabled()).isFalse();
        when(element.isEnabled()).thenReturn(true);
        assertThat(fluentElement.enabled()).isTrue();
    }

    @Test
    public void testIsSelected() {
        assertThat(fluentElement.selected()).isFalse();
        when(element.isSelected()).thenReturn(true);
        assertThat(fluentElement.selected()).isTrue();
    }

    @Test
    public void testIsClickable() {
        assertThat(fluentElement.clickable()).isFalse();
        when(element.isEnabled()).thenReturn(true);
        when(element.isDisplayed()).thenReturn(true);
        assertThat(fluentElement.clickable()).isTrue();
    }

    @Test
    public void testIsStable() {
        assertThat(fluentElement.stale()).isFalse();
        when(element.isEnabled()).thenThrow(StaleElementReferenceException.class);
        assertThat(fluentElement.stale()).isTrue();
    }

    @Test
    public void testGetTagName() {
        when(element.getTagName()).thenReturn("test");
        assertThat(fluentElement.tagName()).isEqualTo("test");
    }

    @Test
    public void testGetElement() {
        assertThat(fluentElement.getElement()).isSameAs(element);
    }

    @Test
    public void testGetSize() {
        when(element.getSize()).thenReturn(new Dimension(10, 20));
        assertThat(fluentElement.size()).isEqualTo(new Dimension(10, 20));
    }

    @Test
    public void testFind() {
        when(element.findElements(By.cssSelector(".test")))
                .thenReturn(Arrays.asList(mock(WebElement.class), mock(WebElement.class)));

        fluentElement.$(".test");
        fluentElement.$(By.cssSelector(".test"));

        fluentElement.find(".test");
        fluentElement.find(By.cssSelector(".test"));

        fluentElement.$(".test").index(1);
        fluentElement.$(By.cssSelector(".test")).index(1);

        fluentElement.find(".test").index(1);
        fluentElement.find(By.cssSelector(".test")).index(1);

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                fluentElement.$();
            }
        }).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                fluentElement.$().index(1);
            }
        }).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                fluentElement.find();
            }
        }).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                fluentElement.find().index(1);
            }
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testel() {
        WebElement findElement = mock(WebElement.class);

        when(element.findElements(By.cssSelector(".test"))).thenReturn(Arrays.asList(findElement));

        assertThat(fluentElement.el(".test").now().getElement()).isEqualTo(findElement);
        assertThat(fluentElement.el(By.cssSelector(".test")).now().getElement()).isEqualTo(findElement);

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                fluentElement.el(".other").now();
            }
        }).isInstanceOf(NoSuchElementException.class);

        assertThat(fluentElement.el(By.cssSelector(".other")).present()).isFalse();
        assertThat(fluentElement.el(By.cssSelector(".other")).optional().isPresent()).isFalse();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                fluentElement.el(By.cssSelector(".other")).now();
            }
        }).isInstanceOf(NoSuchElementException.class);

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                fluentElement.el().now();
            }
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testHtml() {
        when(element.getAttribute("innerHTML")).thenReturn("<html/>");
        assertThat(fluentElement.html()).isEqualTo("<html/>");
    }

    @Test
    public void testFill() {
        when(element.isEnabled()).thenReturn(true);
        when(element.isDisplayed()).thenReturn(true);

        fluentElement.fill().withText("test");
    }

    @Test
    public void testFillSelect() {
        when(element.getTagName()).thenReturn("select");
        WebElement valueElement = mock(WebElement.class);
        when(element.findElements(any(By.class))).thenReturn(Arrays.asList(valueElement));

        fluentElement.fillSelect().withValue("value");
        verify(valueElement).click();
    }

    @Test(expected = NoSuchElementException.class)
    public void testFillSelectInvalidElement() {
        when(element.getTagName()).thenReturn("span");
        WebElement valueElement = mock(WebElement.class);
        when(element.findElements(any(By.class))).thenReturn(Arrays.asList(valueElement));

        when(element.isDisplayed()).thenReturn(true);
        when(element.isEnabled()).thenReturn(true);

        fluentElement.fillSelect().withValue("value");
    }

    @Test
    public void testToString() {
        assertThat(fluentElement.toString()).isEqualTo(element.toString());
    }

    private static final class Component {
        private WebElement element;

        Component(WebElement element) {
            this.element = element;
        }

        public WebElement getElement() {
            return element;
        }

        @Override
        public String toString() {
            return "Component";
        }
    }

    private static class InvalidComponent {
    }

    private abstract static class InputDevicesDriver implements WebDriver, HasInputDevices { // NOPMD AbstractNaming
    }

    private abstract static class LocatableElement implements WebElement, Locatable { // NOPMD AbstractNaming
    }

}
