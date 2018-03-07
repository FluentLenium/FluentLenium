package org.fluentlenium.core.action;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.interactions.internal.Coordinates;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class KeyboardActionsTest {
    @Mock
    private Keyboard keyboard;

    @Mock
    private Mouse mouse;

    @Mock
    private InputDevicesDriver driver;

    @Before
    public void before() {
        when(driver.getKeyboard()).thenReturn(keyboard);
        when(driver.getMouse()).thenReturn(mouse);
    }

    @After
    public void after() {
        reset(driver, keyboard, mouse);
    }

    @Test
    public void testKeyDown() {
        KeyboardActions actions = new KeyboardActions(driver);
        actions.keyDown(Keys.SHIFT);

        verify(mouse, never()).mouseMove(any(Coordinates.class));
        verify(keyboard).pressKey(Keys.SHIFT);
    }

    @Test
    public void testKeyUp() {
        KeyboardActions actions = new KeyboardActions(driver);
        actions.keyUp(Keys.SHIFT);

        verify(mouse, never()).mouseMove(any(Coordinates.class));
        verify(keyboard).releaseKey(Keys.SHIFT);
    }

    @Test
    public void testSendKeys() {
        KeyboardActions actions = new KeyboardActions(driver);
        actions.sendKeys(Keys.ENTER, Keys.SPACE);

        verify(mouse, never()).mouseMove(any(Coordinates.class));
        verify(keyboard).sendKeys(Keys.ENTER, Keys.SPACE);
    }

    @Test
    public void testBasic() {
        KeyboardActions actions = new KeyboardActions(driver);
        Assertions.assertThat(actions.basic()).isSameAs(keyboard);
    }

    private abstract static class InputDevicesDriver implements WebDriver, HasInputDevices { // NOPMD AbstractNaming
    }
}
