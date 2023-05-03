package io.fluentlenium.core.action;

import io.fluentlenium.core.domain.FluentWebElement;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Interactive;
import org.openqa.selenium.interactions.Locatable;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class KeyboardElementActionsTest {
    @Spy
    @InjectMocks
    private Actions actions;

    @Mock
    private InputDevicesDriver driver;

    @Mock
    private LocatableElement element;

    @Mock
    private FluentWebElement fluentWebElement;

    @Mock
    private Coordinates coordinates;

    @After
    public void after() {
        reset(driver, actions);
    }

    @Test
    public void testKeyDownWebElement() {
        KeyboardElementActionTestable actions = new KeyboardElementActionTestable(driver, element, this.actions);
        actions.keyDown(Keys.SHIFT);

        verify(this.actions).keyDown(element, Keys.SHIFT);
    }

    @Test
    public void testKeyDownFluentWebElement() {
        KeyboardElementActionTestable actions = new KeyboardElementActionTestable(driver, element, this.actions);
        actions.keyDown(Keys.SHIFT);

        verify(this.actions).keyDown(element, Keys.SHIFT);
    }

    @Test
    public void testKeyUp() {
        KeyboardElementActionTestable actions = new KeyboardElementActionTestable(driver, element, this.actions);
        actions.keyUp(Keys.SHIFT);

        verify(this.actions).keyUp(element, Keys.SHIFT);
    }

    @Test
    public void testSendKeys() {
        KeyboardElementActionTestable actions = new KeyboardElementActionTestable(driver, element, this.actions);
        actions.sendKeys(Keys.ENTER, Keys.SPACE);

        verify(this.actions).sendKeys(element, Keys.ENTER, Keys.SPACE);
    }

    private abstract static class InputDevicesDriver implements WebDriver, Interactive { // NOPMD AbstractNaming
    }

    private abstract static class LocatableElement implements WebElement, Locatable { // NOPMD AbstractNaming
    }

    static class KeyboardElementActionTestable extends KeyboardElementActions {
        Actions actions;

        public KeyboardElementActionTestable(WebDriver driver, WebElement element, Actions actions) {
            super(driver, element);
            this.actions = actions;
        }

        @Override
        protected Actions actions() {
            return actions;
        }
    }
}
