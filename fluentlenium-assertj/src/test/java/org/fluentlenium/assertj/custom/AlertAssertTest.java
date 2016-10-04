package org.fluentlenium.assertj.custom;

import org.fluentlenium.assertj.FluentLeniumAssertions;
import org.fluentlenium.core.alert.Alert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.NoAlertPresentException;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AlertAssertTest {

    @Mock
    private Alert alert;
    private AlertAssert alertAssert;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        alertAssert = FluentLeniumAssertions.assertThat(alert);
    }

    @Test
    public void testHasTextOk() {
        when(alert.getText()).thenReturn("some text");
        alertAssert.hasText("some text");
    }

    @Test(expected = AssertionError.class)
    public void testHasTextKo() throws Exception {
        when(alert.getText()).thenReturn("other text");
        alertAssert.hasText("some text");
    }

    @Test(expected = AssertionError.class)
    public void testHasTextKoNoAlert() throws Exception {
        doThrow(new NoAlertPresentException()).when(alert).getText();
        alertAssert.hasText("some text");
    }

    @Test
    public void testIsPresentOk() {
        alertAssert.isPresent();
        verify(alert).switchTo();
    }

    @Test(expected = AssertionError.class)
    public void testIsPresentKo() {
        doThrow(new NoAlertPresentException()).when(alert).switchTo();
        alertAssert.isPresent();
    }

}
