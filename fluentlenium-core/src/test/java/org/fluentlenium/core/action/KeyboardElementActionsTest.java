package org.fluentlenium.core.action;

import org.assertj.core.api.Assertions;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.interactions.Mouse;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class KeyboardElementActionsTest {
    @Mock
    private Keyboard keyboard;

    @Mock
    private Mouse mouse;

    @Mock
    private InputDevicesDriver driver;

    @Mock
    private LocatableElement element;

    @Mock
    private FluentWebElement fluentWebElement;

    @Mock
    private Coordinates coordinates;

    @Before
    public void before() {
        when(driver.getKeyboard()).thenReturn(keyboard);
        when(driver.getMouse()).thenReturn(mouse);

        when(element.getCoordinates()).thenReturn(coordinates);
    }

    @After
    public void after() {
        reset(driver, keyboard, mouse);
    }

    @Test
    public void testKeyDownWebElement() {
        KeyboardElementActions actions = new KeyboardElementActions(driver, element);
        actions.keyDown(Keys.SHIFT);

        verify(mouse).click(coordinates);
        verify(keyboard).pressKey(Keys.SHIFT);
    }

    @Test
    public void testKeyDownFluentWebElement() {
        when(fluentWebElement.getElement()).thenReturn(element);

        KeyboardElementActions actions = new KeyboardElementActions(driver, fluentWebElement);
        actions.keyDown(Keys.SHIFT);

        verify(mouse).click(coordinates);
        verify(keyboard).pressKey(Keys.SHIFT);
    }

    @Test
    public void testKeyUp() {
        KeyboardElementActions actions = new KeyboardElementActions(driver, element);
        actions.keyUp(Keys.SHIFT);

        verify(mouse).click(coordinates);
        verify(keyboard).releaseKey(Keys.SHIFT);
    }

    @Test
    public void testSendKeys() {
        KeyboardElementActions actions = new KeyboardElementActions(driver, element);
        actions.sendKeys(Keys.ENTER, Keys.SPACE);

        verify(mouse).click(coordinates);
        verify(keyboard).sendKeys(Keys.ENTER, Keys.SPACE);
    }

    @Test
    public void testBasic() {
        KeyboardElementActions actions = new KeyboardElementActions(driver, element);
        Assertions.assertThat(actions.basic()).isSameAs(keyboard);
    }

    private abstract static class InputDevicesDriver implements WebDriver, HasInputDevices { // NOPMD AbstractNaming
    }

    private abstract static class LocatableElement implements WebElement, Locatable { // NOPMD AbstractNaming
    }
}
