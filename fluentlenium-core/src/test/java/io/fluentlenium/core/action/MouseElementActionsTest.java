package io.fluentlenium.core.action;

import io.fluentlenium.core.domain.FluentWebElement;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Interactive;
import org.openqa.selenium.interactions.Locatable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MouseElementActionsTest {
    @Spy
    @InjectMocks
    private Actions actions;

    @Mock
    private InputDevicesDriver driver;

    @Mock
    private LocatableElement element;

    @Mock
    private FluentWebElement fluentWebElement;

    @After
    public void after() {
        reset(driver, actions);
    }

    @Test
    public void testClickAndHold() {
        MouseElementActionsTestable actions = new MouseElementActionsTestable(driver, element, this.actions);
        actions.clickAndHold();

        verify(this.actions).clickAndHold(element);
    }

    @Test
    public void testClickWebElement() {
        MouseElementActionsTestable actions = new MouseElementActionsTestable(driver, element, this.actions);
        actions.click();

        verify(this.actions).click(element);
    }

    @Test
    public void testClickFluentWebElement() {
        MouseElementActionsTestable actions = new MouseElementActionsTestable(driver, element, this.actions);
        actions.click();

        verify(this.actions).click(element);
    }


    @Test
    public void testContextClick() {
        MouseElementActionsTestable actions = new MouseElementActionsTestable(driver, element, this.actions);
        actions.contextClick();

        verify(this.actions).contextClick(element);
    }

    @Test
    public void testDoubleClick() {
        MouseElementActionsTestable actions = new MouseElementActionsTestable(driver, element, this.actions);
        actions.doubleClick();

        verify(this.actions).doubleClick(element);
    }

    @Test
    public void testRelease() {
        MouseElementActionsTestable actions = new MouseElementActionsTestable(driver, element, this.actions);
        actions.release();

        verify(this.actions).release(element);
    }

    @Test
    public void moveToElement() {
        MouseElementActionsTestable actions = new MouseElementActionsTestable(driver, element, this.actions);
        actions.moveToElement();

        verify(this.actions).moveToElement(element);
    }

    @Test
    public void moveToTargetElement() {
        LocatableElement target = mock(LocatableElement.class);

        MouseElementActionsTestable actions = new MouseElementActionsTestable(driver, element, this.actions);
        actions.moveToElement(target);

        verify(this.actions).moveToElement(target);
    }

    @Test
    public void moveToElementOffset() {
        MouseElementActionsTestable actions = new MouseElementActionsTestable(driver, element, this.actions);
        actions.moveToElement(10, 20);

        verify(this.actions).moveToElement(element, 10, 20);
    }

    @Test
    public void moveToTargetElementOffset() {
        LocatableElement target = mock(LocatableElement.class);
        MouseElementActionsTestable actions = new MouseElementActionsTestable(driver, element, this.actions);
        actions.moveToElement(target, 10, 20);

        verify(this.actions).moveToElement(target, 10, 20);
    }

    @Test
    public void dragAndDropFrom() {
        MouseElementActionsTestable actions = new MouseElementActionsTestable(driver, element, this.actions);

        LocatableElement source = mock(LocatableElement.class);
        actions.dragAndDropFrom(source);

        verify(this.actions).dragAndDrop(source, element);
    }

    @Test
    public void dragAndDropTo() {
        MouseElementActionsTestable actions = new MouseElementActionsTestable(driver, element, this.actions);

        LocatableElement target = mock(LocatableElement.class);
        actions.dragAndDropTo(target);

        verify(this.actions).dragAndDrop(element, target);
    }

    @Test
    public void dragAndDropBy() {
        MouseElementActionsTestable actions = new MouseElementActionsTestable(driver, element, this.actions);
        actions.dragAndDropBy(10, 20);

        verify(this.actions).dragAndDropBy(element, 10, 20);
    }

    @Test
    public void dragAndDropByWithTargetOffset() {
        MouseElementActionsTestable actions = new MouseElementActionsTestable(driver, element, this.actions);

        LocatableElement target = mock(LocatableElement.class);
        actions.dragAndDropByWithTargetOffset(target, 10, 20);

        verify(this.actions).clickAndHold(element);
        verify(this.actions).moveToElement(target, 10, 20);
        verify(this.actions).release();
    }

    private abstract static class InputDevicesDriver implements WebDriver, Interactive { // NOPMD AbstractNaming
    }

    private abstract static class LocatableElement implements WebElement, Locatable { // NOPMD AbstractNaming
    }

    static class MouseElementActionsTestable extends MouseElementActions {
        Actions actions;

        public MouseElementActionsTestable(WebDriver driver, WebElement element, Actions actions) {
            super(driver, element);
            this.actions = actions;
        }

        @Override
        protected Actions actions() {
            return actions;
        }
    }
}
