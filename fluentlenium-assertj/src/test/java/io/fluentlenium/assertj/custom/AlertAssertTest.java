package io.fluentlenium.assertj.custom;

import io.fluentlenium.assertj.FluentLeniumAssertions;
import io.fluentlenium.core.alert.AlertImpl;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.NoAlertPresentException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AlertAssertTest {

    @Mock
    private AlertImpl alert;
    private AlertAssert alertAssert;

    @BeforeMethod
    public void before() {
        MockitoAnnotations.initMocks(this);
        alertAssert = FluentLeniumAssertions.assertThat(alert);
    }

    @Test
    public void testHasTextPositive() {
        when(alert.present()).thenReturn(true);
        when(alert.getText()).thenReturn("some text");
        assertThatCode(() -> alertAssert.hasText("some text")).doesNotThrowAnyException();
    }

    @Test
    public void testHasTextNegative() {
        when(alert.present()).thenReturn(true);
        when(alert.getText()).thenReturn("other text");
        assertThatThrownBy(() -> alertAssert.hasText("some text"))
                .isInstanceOf(AssertionError.class)
                .hasMessage("The alert box does not contain the expected text");
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
        when(alert.present()).thenReturn(false);
        assertThatThrownBy(() -> alertAssert.isPresent())
                .isInstanceOf(AssertionError.class)
                .hasMessage("There is no alert box");
    }

}
