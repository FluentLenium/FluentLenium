package org.fluentlenium.core.action;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
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
public class MouseActionsTest {
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
    public void testClickAndHold() {
        MouseActions actions = new MouseActions(driver);
        actions.clickAndHold();

        verify(mouse, never()).mouseMove(any(Coordinates.class));
        verify(mouse).mouseDown((Coordinates) any());
    }

    @Test
    public void testClick() {
        MouseActions actions = new MouseActions(driver);
        actions.click();

        verify(mouse, never()).mouseMove(any(Coordinates.class));
        verify(mouse).click((Coordinates) any());
    }

    @Test
    public void testClickKeyboardMouse() {
        MouseActions actions = new MouseActions(keyboard, mouse);
        actions.click();

        verify(mouse, never()).mouseMove(any(Coordinates.class));
        verify(mouse).click((Coordinates) any());
    }

    @Test
    public void testContextClick() {
        MouseActions actions = new MouseActions(driver);
        actions.contextClick();

        verify(mouse, never()).mouseMove(any(Coordinates.class));
        verify(mouse).contextClick((Coordinates) any());
    }

    @Test
    public void testDoubleClick() {
        MouseActions actions = new MouseActions(driver);
        actions.doubleClick();

        verify(mouse, never()).mouseMove(any(Coordinates.class));
        verify(mouse).doubleClick((Coordinates) any());
    }

    @Test
    public void testRelease() {
        MouseActions actions = new MouseActions(driver);
        actions.release();

        verify(mouse, never()).mouseMove(any(Coordinates.class));
        verify(mouse).mouseUp((Coordinates) any());
    }

    @Test
    public void testBasic() {
        MouseActions actions = new MouseActions(driver);
        Assertions.assertThat(actions.basic()).isSameAs(mouse);

        MouseActions actionsAlt = new MouseActions(keyboard, mouse);
        Assertions.assertThat(actionsAlt.basic()).isSameAs(mouse);
    }

    private abstract static class InputDevicesDriver implements WebDriver, HasInputDevices { // NOPMD AbstractNaming
    }
}
