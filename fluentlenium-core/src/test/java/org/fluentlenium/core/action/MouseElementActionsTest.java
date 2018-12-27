package org.fluentlenium.core.action;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.interactions.Mouse;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MouseElementActionsTest {
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
    public void testClickAndHold() {
        MouseElementActions actions = new MouseElementActions(driver, element);
        actions.clickAndHold();

        verify(mouse).mouseMove(coordinates);
        verify(mouse).mouseDown(coordinates);
    }

    @Test
    public void testClick() {
        MouseElementActions actions = new MouseElementActions(driver, element);
        actions.click();

        verify(mouse).mouseMove(coordinates);
        verify(mouse).click(coordinates);
    }

    @Test
    public void testContextClick() {
        MouseElementActions actions = new MouseElementActions(driver, element);
        actions.contextClick();

        verify(mouse).mouseMove(coordinates);
        verify(mouse).contextClick(coordinates);
    }

    @Test
    public void testDoubleClick() {
        MouseElementActions actions = new MouseElementActions(driver, element);
        actions.doubleClick();

        verify(mouse).mouseMove(coordinates);
        verify(mouse).doubleClick(coordinates);
    }

    @Test
    public void testRelease() {
        MouseElementActions actions = new MouseElementActions(driver, element);
        actions.release();

        verify(mouse).mouseMove(coordinates);
        verify(mouse).mouseUp(coordinates);
    }

    @Test
    public void moveToElement() {
        MouseElementActions actions = new MouseElementActions(driver, element);
        actions.moveToElement();

        verify(mouse).mouseMove(coordinates);
    }

    @Test
    public void moveToElementOffset() {
        MouseElementActions actions = new MouseElementActions(driver, element);
        actions.moveToElement(10, 20);

        verify(mouse).mouseMove(coordinates, 10, 20);
    }

    @Test
    public void dragAndDropFrom() {
        MouseElementActions actions = new MouseElementActions(driver, element);

        LocatableElement source = mock(LocatableElement.class);
        Coordinates sourceCoordinates = mock(Coordinates.class);
        when(source.getCoordinates()).thenReturn(sourceCoordinates);

        actions.dragAndDropFrom(source);

        verify(mouse).mouseMove(sourceCoordinates);
        verify(mouse).mouseDown(sourceCoordinates);
        verify(mouse, times(2)).mouseMove(coordinates);
        verify(mouse).mouseUp(coordinates);
    }

    @Test
    public void dragAndDropTo() {
        MouseElementActions actions = new MouseElementActions(driver, element);

        LocatableElement target = mock(LocatableElement.class);
        Coordinates targetCoordinates = mock(Coordinates.class);
        when(target.getCoordinates()).thenReturn(targetCoordinates);

        actions.dragAndDropTo(target);

        verify(mouse).mouseMove(coordinates);
        verify(mouse).mouseDown(coordinates);
        verify(mouse, times(2)).mouseMove(targetCoordinates);
        verify(mouse).mouseUp(targetCoordinates);
    }

    @Test
    public void dragAndDropBy() {
        MouseElementActions actions = new MouseElementActions(driver, element);
        actions.dragAndDropBy(10, 20);

        verify(mouse).mouseMove(coordinates);
        verify(mouse).mouseDown(coordinates);
        verify(mouse).mouseMove(null, 10, 20);
        verify(mouse).mouseUp(null);
    }

    @Test
    public void testBasic() {
        MouseElementActions actions = new MouseElementActions(driver, element);
        Assertions.assertThat(actions.basic()).isSameAs(mouse);
    }

    private abstract static class InputDevicesDriver implements WebDriver, HasInputDevices { // NOPMD AbstractNaming
    }

    private abstract static class LocatableElement implements WebElement, Locatable { // NOPMD AbstractNaming
    }
}
