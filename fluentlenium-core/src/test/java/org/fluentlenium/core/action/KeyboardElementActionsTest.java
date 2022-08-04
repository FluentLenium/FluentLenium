package org.fluentlenium.core.action;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Interactive;
import org.openqa.selenium.interactions.Locatable;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class KeyboardElementActionsTest {

    @Mock
    private InputDevicesDriver driver;

    @Mock
    private LocatableElement element;

    private KeyboardElementActions actions;
    private Actions actionsSpy;

    @Before
    public void before() {
        actionsSpy = Mockito.spy(new Actions(driver));

        actions = new KeyboardElementActions(driver, element, webDriver -> actionsSpy);
    }

    @Test
    public void testKeyDownWebElement() {
        actions.keyDown(Keys.SHIFT);

        verify(actionsSpy).keyDown(element, Keys.SHIFT);
    }

    @Test
    public void testKeyUp() {
        actions.keyUp(Keys.SHIFT);

        verify(actionsSpy).keyUp(element, Keys.SHIFT);
    }

    @Test
    public void testSendKeys() {
        actions.sendKeys(Keys.ENTER, Keys.SPACE);

        verify(actionsSpy).sendKeys(element, Keys.ENTER, Keys.SPACE);
    }

    private abstract static class InputDevicesDriver implements WebDriver, Interactive { // NOPMD AbstractNaming
    }

    private abstract static class LocatableElement implements WebElement, Locatable { // NOPMD AbstractNaming
    }
}
