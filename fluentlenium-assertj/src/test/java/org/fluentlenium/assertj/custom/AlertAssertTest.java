package org.fluentlenium.assertj.custom;

import org.fluentlenium.assertj.FluentLeniumAssertions;
import org.fluentlenium.core.alert.AlertImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.NoAlertPresentException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AlertAssertTest {

    @Mock
    private AlertImpl alert;
    private AlertAssert alertAssert;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        alertAssert = FluentLeniumAssertions.assertThat(alert);
    }

    @Test
    public void testHasTextPositive() {
        when(alert.getText()).thenReturn("some text");
        alertAssert.hasText("some text");
    }

    @Test
    public void testHasTextNegative() {
        when(alert.getText()).thenReturn("other text");
        assertThatThrownBy(() -> alertAssert.hasText("some text"))
                .isInstanceOf(AssertionError.class)
                .hasMessage("The alert box does not contain the text: some text. Actual text found : other text");
    }

    @Test
    public void testHasTextNotPresent() {
        doThrow(new NoAlertPresentException()).when(alert).getText();
        assertThatThrownBy(() -> alertAssert.hasText("some text"))
                .isInstanceOf(AssertionError.class)
                .hasMessage("There is no alert box");
    }

    @Test
    public void testIsPresentPositive() {
        when(alert.present()).thenReturn(true);
        alertAssert.isPresent();
        verify(alert).present();
    }

    @Test
    public void testIsPresentNegative() {
        assertThatThrownBy(() -> alertAssert.isPresent())
                .isInstanceOf(AssertionError.class)
                .hasMessage("There is no alert box");
    }

}
