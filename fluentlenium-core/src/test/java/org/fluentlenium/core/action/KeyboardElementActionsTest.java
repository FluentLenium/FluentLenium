package org.fluentlenium.core.action;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;

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
    public void testKeyDown() {
        KeyboardElementActions actions = new KeyboardElementActions(driver, element);
        actions.keyDown(Keys.SHIFT);

        verify(mouse).click(coordinates);
        verify(keyboard).pressKey(Keys.SHIFT);
    }

    @Test
    public void testKeyDownKeyboardMouse() {
        KeyboardElementActions actions = new KeyboardElementActions(keyboard, mouse, element);
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

        KeyboardElementActions actionsAlt = new KeyboardElementActions(keyboard, mouse, element);
        Assertions.assertThat(actionsAlt.basic()).isSameAs(keyboard);
    }

    private abstract static class InputDevicesDriver implements WebDriver, HasInputDevices { // NOPMD AbstractNaming
    }

    private abstract static class LocatableElement implements WebElement, Locatable { // NOPMD AbstractNaming
    }
}
