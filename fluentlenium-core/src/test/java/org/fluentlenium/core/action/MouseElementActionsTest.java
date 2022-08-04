package org.fluentlenium.core.action;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Interactive;
import org.openqa.selenium.interactions.Locatable;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MouseElementActionsTest {
    private Actions actionsSpy;

    @Mock
    private InputDevicesDriver driver;

    @Mock
    private LocatableElement element;

    MouseElementActions actions;

    @Before
    public void before() {
        actionsSpy = Mockito.spy(new Actions(driver));

        actions = new MouseElementActions(driver, element, this::mockActions);
    }

    @After
    public void after() {
        reset(driver);
    }

    private Actions mockActions(WebDriver driver) {
        return actionsSpy;
    }

    @Test
    public void testClickAndHold() {
        actions.clickAndHold();

        verify(actionsSpy).clickAndHold(Mockito.any());
    }

    @Test
    public void testClickWebElement() {
        actions.click();

        verify(actionsSpy).click(Mockito.any());
    }


    @Test
    public void testContextClick() {
        actions.contextClick();

        verify(actionsSpy).contextClick(Mockito.any());
    }

    @Test
    public void testDoubleClick() {
        actions.doubleClick();

        verify(actionsSpy).doubleClick(Mockito.any());
    }

    @Test
    public void testRelease() {
        actions.release();

        verify(actionsSpy).release(Mockito.any());
    }

    @Test
    public void moveToElement() {
        actions.moveToElement();

        verify(actionsSpy).moveToElement(Mockito.any());
    }

    @Test
    public void moveToElementOffset() {
        actions.moveToElement(10, 20);

        verify(actionsSpy).moveToElement(Mockito.any(), eq(10), eq(20));
    }

    @Test
    public void dragAndDropFrom() {
        LocatableElement source = mock(LocatableElement.class);

        actions.dragAndDropFrom(source);

        verify(actionsSpy).dragAndDrop(eq(source), Mockito.any());
    }

    @Test
    public void dragAndDropTo() {

        LocatableElement target = mock(LocatableElement.class);

        actions.dragAndDropTo(target);

        verify(actionsSpy).dragAndDrop(Mockito.any(), eq(target));
    }

    @Test
    public void dragAndDropBy() {
        actions.dragAndDropBy(10, 20);

        verify(actionsSpy).dragAndDropBy(Mockito.any(), eq(10), eq(20));
    }

    @Test
    public void dragAndDropByWithTargetOffset() {
        LocatableElement target = mock(LocatableElement.class);

        actions.dragAndDropByWithTargetOffset(target, 10, 20);

        verify(actionsSpy).clickAndHold(Mockito.any());
        verify(actionsSpy).moveToElement(target, 10, 20);
    }

    private abstract static class InputDevicesDriver implements WebDriver, Interactive { // NOPMD AbstractNaming
    }

    private abstract static class LocatableElement implements WebElement, Interactive, Locatable { // NOPMD AbstractNaming
    }
}
