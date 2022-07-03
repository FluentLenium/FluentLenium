package org.fluentlenium.core.action;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Interactive;
import org.openqa.selenium.interactions.Sequence;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.action.SequenceAssert.assertThatSequence;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class KeyboardActionsTest {

    @Mock
    private InputDevicesDriver driver;

    @Captor
    ArgumentCaptor<Collection<Sequence>> captor;

    @Test
    public void testKeyDown() {
        KeyboardActions actions = new KeyboardActions(driver);
        actions.keyDown(Keys.SHIFT);

        verify(driver).perform(captor.capture());

        assertThat(captor.getValue()).hasSize(1).allSatisfy(sequence -> {
            assertThatSequence(sequence).isKeyDown(Keys.SHIFT);
        });
    }

    @Test
    public void testKeyUp() {
        KeyboardActions actions = new KeyboardActions(driver);
        actions.keyUp(Keys.SHIFT);

        verify(driver).perform(captor.capture());

        assertThat(captor.getValue()).hasSize(1).allSatisfy(sequence -> {
            assertThatSequence(sequence).isKeyUp(Keys.SHIFT);
        });
    }

    @Test
    public void testSendKeys() {
        KeyboardActions actions = new KeyboardActions(driver);
        actions.sendKeys(Keys.ENTER, Keys.SPACE);

        verify(driver).perform(captor.capture());

        assertThat(captor.getValue()).hasSize(1).allSatisfy(sequence -> {
            assertThatSequence(sequence).isKeySequence(Keys.ENTER, Keys.SPACE);
        });
    }

    private abstract static class InputDevicesDriver implements WebDriver, Interactive { // NOPMD AbstractNaming
    }
}

