package io.fluentlenium.core.action;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Interactive;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MouseActionsTest {
    @Spy
    @InjectMocks
    private Actions actions;

    @Mock
    private InputDevicesDriver driver;

    @After
    public void after() {
        reset(driver, actions);
    }

    @Test
    public void testClickAndHold() {
        MouseActionsTestable actions = new MouseActionsTestable(driver, this.actions);
        actions.clickAndHold();

        verify(this.actions, never()).moveToElement(any());
        verify(this.actions).clickAndHold();
    }

    @Test
    public void testClick() {
        MouseActionsTestable actions = new MouseActionsTestable(driver, this.actions);
        actions.click();

        verify(this.actions, never()).moveToElement(any());
        verify(this.actions).click();
    }

    @Test
    public void testContextClick() {
        MouseActionsTestable actions = new MouseActionsTestable(driver, this.actions);
        actions.contextClick();

        verify(this.actions, never()).moveToElement(any());
        verify(this.actions).contextClick();
    }

    @Test
    public void testDoubleClick() {
        MouseActionsTestable actions = new MouseActionsTestable(driver, this.actions);
        actions.doubleClick();

        verify(this.actions, never()).moveToElement(any());
        verify(this.actions).doubleClick();
    }

    @Test
    public void testRelease() {
        MouseActionsTestable actions = new MouseActionsTestable(driver, this.actions);
        actions.release();

        verify(this.actions, never()).moveToElement(any());
        verify(this.actions).release();
    }

    @Test
    public void moveByOffset() {
        MouseActionsTestable actions = new MouseActionsTestable(driver, this.actions);
        actions.moveByOffset(1, 1);
        verify(this.actions).moveByOffset(1, 1);
    }

    private abstract static class InputDevicesDriver implements WebDriver, Interactive { // NOPMD AbstractNaming
    }

    static class MouseActionsTestable extends MouseActions {
        Actions actions;

        public MouseActionsTestable(WebDriver driver, Actions actions) {
            super(driver);
            this.actions = actions;
        }

        @Override
        protected Actions actions() {
            return actions;
        }
    }
}