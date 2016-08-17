package org.fluentlenium.integration;

import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;
import java.util.HashSet;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GoToInNewTabTest extends LocalFluentCase {
    @Mock
    private JavascriptWebDriver webDriver;

    @Mock
    private WebDriver.TargetLocator locator;

    public GoToInNewTabTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void checkGoToInNewTab() {
        when(webDriver.getWindowHandles()).thenReturn(new HashSet<String>(Arrays.asList("a")),
                new HashSet<String>(Arrays.asList("a", "b")));
        when(webDriver.switchTo()).thenReturn(locator);
        goToInNewTab(DEFAULT_URL);
        verify(locator).window("b");
    }

    @Override
    public WebDriver newWebDriver() {
        return webDriver;
    }

    public interface JavascriptWebDriver extends WebDriver, JavascriptExecutor {

    }

}
