package org.fluentlenium.core.alert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AlertTest {
    @Mock
    private WebDriver driver;

    @Mock
    private WebDriver.TargetLocator targetLocator;

    @Mock
    private org.openqa.selenium.Alert seleniumAlert;

    private Alert alert;

    @Before
    public void before() {
        when(driver.switchTo()).thenReturn(targetLocator);
        when(targetLocator.alert()).thenReturn(seleniumAlert);
        alert = new Alert(driver);
    }

    @Test
    public void testAlert() {
        alert.accept();
        verify(seleniumAlert).accept();
    }

    @Test
    public void testSwitchTo() {
        alert.switchTo();
        verify(driver).switchTo();
        verify(targetLocator).alert();
    }

    @Test
    public void testPrompt() {
        alert.prompt("abc");
        verify(seleniumAlert).sendKeys("abc");
        verify(seleniumAlert).accept();
    }
}
