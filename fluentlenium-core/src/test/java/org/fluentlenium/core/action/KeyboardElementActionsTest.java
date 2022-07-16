package org.fluentlenium.core.action;

import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.*;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.action.SequenceAssert.assertThatSequence;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@Ignore
public class KeyboardElementActionsTest {
    @Mock
    private Keyboard keyboard;

    @Mock
    private Mouse mouse;

    @Mock
    private InputDevicesDriver driver;

    @Mock
    private LocatableElement element;

    @Mock
    private FluentWebElement fluentWebElement;

    @Mock
    private Coordinates coordinates;

    @Captor
    ArgumentCaptor<Collection<Sequence>> captor;

    @Before
    public void before() {
        when(element.getCoordinates()).thenReturn(coordinates);
    }

    @Test
    public void testKeyDownWebElement() {
        KeyboardElementActions actions = new KeyboardElementActions(driver, element);
        actions.keyDown(Keys.SHIFT);

        verify(driver).perform(captor.capture());

        assertThat(captor.getValue()).satisfiesExactlyInAnyOrder(
                sequence -> assertThatSequence(sequence).isKey(),
                sequence -> assertThatSequence(sequence).isPointer()
        );
    }

    @Test
    public void testKeyDownFluentWebElement() {
        when(fluentWebElement.getElement()).thenReturn(element);

        KeyboardElementActions actions = new KeyboardElementActions(driver, fluentWebElement);
        actions.keyDown(Keys.SHIFT);


        verify(driver).perform(captor.capture());

        captor.getValue().forEach(v -> System.out.println(v.toJson()));

        assertThat(captor.getValue()).satisfiesExactlyInAnyOrder(
                sequence -> assertThatSequence(sequence).isKey().hasKeyDown(Keys.SHIFT),
                sequence -> assertThatSequence(sequence).isPointer()
        );
        verify(mouse).click(coordinates);
        verify(keyboard).pressKey(Keys.SHIFT);
    }

    @Test
    public void testKeyUp() {
        KeyboardElementActions actions = new KeyboardElementActions(driver, element);
        actions.keyUp(Keys.SHIFT);

        verify(driver).perform(captor.capture());

        captor.getValue().forEach(v -> System.out.println(v.toJson()));

        assertThat(captor.getValue()).satisfiesExactlyInAnyOrder(
                sequence -> assertThatSequence(sequence).isKey().hasKeyUp(Keys.SHIFT)
        );

        verify(mouse).click(coordinates);
        verify(keyboard).releaseKey(Keys.SHIFT);
    }

    @Test
    public void testSendKeys() {
        KeyboardElementActions actions = new KeyboardElementActions(driver, element);
        actions.sendKeys(Keys.ENTER, Keys.SPACE);

        verify(mouse).click(coordinates);
        verify(keyboard).sendKeys(Keys.ENTER, Keys.SPACE);
    }

    private abstract static class InputDevicesDriver implements WebDriver, Interactive { // NOPMD AbstractNaming
    }

    private abstract static class LocatableElement implements WebElement, Locatable { // NOPMD AbstractNaming
    }
}
