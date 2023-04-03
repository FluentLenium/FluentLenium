package io.fluentlenium.core.action;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Interactive;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class KeyboardActionsTest {

    @Mock
    private InputDevicesDriver driver;

    @Spy
    @InjectMocks
    private Actions actions;

    @After
    public void after() {
        reset(driver, actions);
    }

    @Test
    public void testKeyDown() {
        KeyboardActionsTestable actions = new KeyboardActionsTestable(driver, this.actions);
        actions.keyDown(Keys.SHIFT);

        verify(this.actions, never()).moveToElement(any());
        verify(this.actions).keyDown(Keys.SHIFT);
    }

    @Test
    public void testKeyUp() {
        KeyboardActionsTestable actions = new KeyboardActionsTestable(driver, this.actions);
        actions.keyUp(Keys.SHIFT);

        verify(this.actions, never()).moveToElement(any());
        verify(this.actions).keyUp(Keys.SHIFT);
    }

    @Test
    public void testSendKeys() {
        KeyboardActionsTestable actions = new KeyboardActionsTestable(driver, this.actions);
        actions.sendKeys(Keys.ENTER, Keys.SPACE);

        verify(this.actions, never()).moveToElement(any());
        verify(this.actions).sendKeys(Keys.ENTER, Keys.SPACE);
    }

    private abstract static class InputDevicesDriver implements WebDriver, Interactive { // NOPMD AbstractNaming
    }

    static class KeyboardActionsTestable extends KeyboardActions {
        Actions actions;

        public KeyboardActionsTestable(WebDriver driver, Actions actions) {
            super(driver);
            this.actions = actions;
        }

        @Override
        protected Actions actions() {
            return actions;
        }
    }
}