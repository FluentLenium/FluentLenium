package org.fluentlenium.core.action;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Interactive;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class KeyboardActionsTest {

    private KeyboardActions actions;

    private Actions actionsSpy;

    @Mock
    private InputDevicesDriver driver;

    @Before
    public void before() {
        actionsSpy = Mockito.spy(new Actions(driver));

        actions = new KeyboardActions(driver, webDriver -> actionsSpy);
    }

    @Test
    public void testKeyDown() {
        actions.keyDown(Keys.SHIFT);

        verify(actionsSpy).keyDown(Keys.SHIFT);
    }

    @Test
    public void testKeyUp() {
        actions.keyUp(Keys.SHIFT);

        verify(actionsSpy).keyUp(Keys.SHIFT);
    }

    @Test
    public void testSendKeys() {
        actions.sendKeys(Keys.ENTER, Keys.SPACE);

        verify(actionsSpy).sendKeys(Keys.ENTER, Keys.SPACE);
    }

    private abstract static class InputDevicesDriver implements WebDriver, Interactive { // NOPMD AbstractNaming
    }
}

