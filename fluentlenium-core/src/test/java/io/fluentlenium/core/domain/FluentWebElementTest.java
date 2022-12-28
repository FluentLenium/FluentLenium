package io.fluentlenium.core.domain;

import io.fluentlenium.adapter.FluentAdapter;
import io.fluentlenium.core.components.ComponentException;
import io.fluentlenium.core.components.ComponentsManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Locatable;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("PMD.ExcessivePublicCount")
public class FluentWebElementTest {

    @Mock
    private LocatableElement element;

    @Mock
    private InputDevicesDriver driver;

    @Spy
    private Actions actions;

    private FluentWebElement fluentElement;

    private ComponentsManager componentsManager;

    private FluentAdapter fluentAdapter;

    @Before
    public void before() {
        fluentAdapter = new FluentAdapter();
        fluentAdapter.initFluent(driver);

        when(driver.executeScript("script", "arg1", "arg2")).thenReturn(null);

        componentsManager = new ComponentsManager(fluentAdapter);

        fluentElement = spy(new FluentWebElement(element, fluentAdapter, componentsManager));
    }

    @After
    public void cleanUp() {
        reset(element, driver, actions);
    }

    @Test
    public void testClick() {
        fluentElement.click();
        verify(element).click();
    }

    @Test
    public void testDoubleClick() {
        fluentElement.doubleClick();
        verify(actions).doubleClick(any());
    }

    @Test
    public void testContextClick() {
        fluentElement.contextClick();
        verify(actions).contextClick(any());
    }

    @Test
    public void testHoverOver() {
        fluentElement.hoverOver();
        verify(actions).moveToElement(any());
    }

    @Test
    public void testAxes() {
        fluentElement.dom().parent();
    }

    @Test
    public void testConditions() {
        when(element.isEnabled()).thenReturn(true);
        assertThat(fluentElement.conditions().enabled()).isTrue();
    }

    @Test
    public void testMouse() {
        fluentElement.mouse().click();
        verify(actions).click(any());
    }

    @Test
    public void testKeyboard() {
        fluentElement.keyboard().sendKeys("ABC");
        verify(actions).sendKeys("ABC");
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
    public void testClearReactInputEmpty() {
        when(fluentElement.attribute("value")).thenReturn("");
        fluentElement.clearReactInput();
        verify(driver, never()).executeScript(
                "arguments[0].value = arguments[1]",
                element, "");
    }

    @Test
    public void testClearReactInputNonEmpty() {
        when(fluentElement.attribute("value")).thenReturn("nonEmpty");
        fluentElement.clearReactInput();
        verify(driver, times(1)).executeScript(
                "arguments[0].value = arguments[1]",
                element, "");
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
    public void testGetCssValue() {
        when(element.getCssValue("property")).thenReturn("test");
        assertThat(fluentElement.cssValue("property")).isEqualTo("test");
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
    public void shouldReturnFalseForDisplayedIfNoSuchElement() {
        when(element.isDisplayed()).thenThrow(NoSuchElementException.class);
        assertThat(fluentElement.displayed()).isFalse();
    }

    @Test
    public void testIsEnabled() {
        assertThat(fluentElement.enabled()).isFalse();
        when(element.isEnabled()).thenReturn(true);
        assertThat(fluentElement.enabled()).isTrue();
    }

    @Test
    public void shouldReturnFalseForEnabledIfNoSuchElement() {
        when(element.isEnabled()).thenThrow(NoSuchElementException.class);
        assertThat(fluentElement.enabled()).isFalse();
    }

    @Test
    public void testIsSelected() {
        assertThat(fluentElement.selected()).isFalse();
        when(element.isSelected()).thenReturn(true);
        assertThat(fluentElement.selected()).isTrue();
    }

    @Test
    public void shouldReturnFalseForSelectedIfNoSuchElement() {
        when(element.isSelected()).thenThrow(NoSuchElementException.class);
        assertThat(fluentElement.selected()).isFalse();
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

        assertThatThrownBy(() -> fluentElement.$()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> fluentElement.$().index(1)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> fluentElement.find()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> fluentElement.find().index(1)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testEl() {
        WebElement findElement = mock(WebElement.class);

        when(element.findElements(By.cssSelector(".test"))).thenReturn(Collections.singletonList(findElement));

        assertThat(fluentElement.el(".test").now().getElement()).isEqualTo(findElement);
        assertThat(fluentElement.el(By.cssSelector(".test")).now().getElement()).isEqualTo(findElement);

        assertThatThrownBy(() -> fluentElement.el(".other").now()).isInstanceOf(NoSuchElementException.class);

        assertThat(fluentElement.el(By.cssSelector(".other")).present()).isFalse();
        assertThat(fluentElement.el(By.cssSelector(".other")).optional()).isNotPresent();

        assertThatThrownBy(() -> fluentElement.el(By.cssSelector(".other")).now()).isInstanceOf(NoSuchElementException.class);

        assertThatThrownBy(() -> fluentElement.el().now()).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldReturnOptionalIfElementIsPresent() {
        assertThat(fluentElement.optional()).hasValue(fluentElement);
    }

    @Test
    public void testNowTrue() {
        fluentElement.now(true);
        verify(fluentElement).reset();
        verify(fluentElement).now();
    }

    @Test
    public void testNowFalse() {
        fluentElement.now(false);
        verify(fluentElement, never()).reset();
        verify(fluentElement).now();
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
        when(element.findElements(any(By.class))).thenReturn(Collections.singletonList(valueElement));

        fluentElement.fillSelect().withValue("value");
        verify(valueElement).click();
    }

    @Test(expected = NoSuchElementException.class)
    public void testFillSelectInvalidElement() {
        when(element.getTagName()).thenReturn("span");
        WebElement valueElement = mock(WebElement.class);
        when(element.findElements(any(By.class))).thenReturn(Collections.singletonList(valueElement));

        when(element.isDisplayed()).thenReturn(true);
        when(element.isEnabled()).thenReturn(true);

        fluentElement.fillSelect().withValue("value");
    }

    @Test
    public void testToString() {
        assertThat(fluentElement).hasToString(element.toString());
    }

    private static final class Component {
        private final WebElement element;

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

    private abstract static class InputDevicesDriver implements WebDriver, JavascriptExecutor {
    }

    private abstract static class LocatableElement implements WebElement, Locatable {
    }

}
