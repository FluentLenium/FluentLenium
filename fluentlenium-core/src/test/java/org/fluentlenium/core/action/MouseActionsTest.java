package org.fluentlenium.core.action;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@Ignore
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
        verify(mouse).mouseDown(any());
    }

    @Test
    public void testClick() {
        MouseActions actions = new MouseActions(driver);
        actions.click();

        verify(mouse, never()).mouseMove(any(Coordinates.class));
        verify(mouse).click(any());
    }

    @Test
    public void testContextClick() {
        MouseActions actions = new MouseActions(driver);
        actions.contextClick();

        verify(mouse, never()).mouseMove(any(Coordinates.class));
        verify(mouse).contextClick(any());
    }

    @Test
    public void testDoubleClick() {
        MouseActions actions = new MouseActions(driver);
        actions.doubleClick();

        verify(mouse, never()).mouseMove(any(Coordinates.class));
        verify(mouse).doubleClick(any());
    }

    @Test
    public void testRelease() {
        MouseActions actions = new MouseActions(driver);
        actions.release();

        verify(mouse, never()).mouseMove(any(Coordinates.class));
        verify(mouse).mouseUp(any());
    }

    @Test
    public void moveByOffset() {
        MouseActions actions = new MouseActions(driver);
        actions.moveByOffset(1, 1);
        verify(mouse).mouseMove(null, 1, 1);
    }

    private abstract static class InputDevicesDriver implements Interactive, WebDriver, HasInputDevices { // NOPMD AbstractNaming
    }
}
