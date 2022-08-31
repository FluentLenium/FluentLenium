package org.fluentlenium.core.action;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Interactive;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MouseActionsTest {

    @Mock
    private InputDevicesDriver driver;
    private MouseActions actions;
    private Actions actionsSpy;

    @Before
    public void before() {

        actionsSpy = Mockito.spy(new Actions(driver));

        actions = new MouseActions(driver) {
            @Override
            protected Actions actions() {
                return actionsSpy;
            }
        };

    }

    @After
    public void after() {
        reset(driver);
    }

    @Test
    public void testClickAndHold() {
        actions.clickAndHold();

        verify(actionsSpy).clickAndHold();
    }

    @Test
    public void testClick() {
        actions.click();

        verify(actionsSpy).click();
    }

    @Test
    public void testContextClick() {
        actions.contextClick();

        verify(actionsSpy).contextClick();
    }

    @Test
    public void testDoubleClick() {
        actions.doubleClick();

        verify(actionsSpy).doubleClick();
    }

    @Test
    public void testRelease() {
        actions.release();

        verify(actionsSpy).release();
    }

    @Test
    public void moveByOffset() {
        actions.moveByOffset(1, 1);

        verify(actionsSpy).moveByOffset(1, 1);
    }

    private abstract static class InputDevicesDriver implements Interactive, WebDriver { // NOPMD AbstractNaming
    }
}
