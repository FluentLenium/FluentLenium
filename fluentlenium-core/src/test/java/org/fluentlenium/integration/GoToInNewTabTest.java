package org.fluentlenium.integration;

import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;
import java.util.HashSet;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GoToInNewTabTest extends IntegrationFluentTest {
    @Mock
    private JavascriptWebDriver webDriver;

    @Mock
    private WebDriver.TargetLocator locator;

    @Test
    public void checkGoToInNewTab() {
        when(webDriver.getWindowHandles()).thenReturn(new HashSet<>(Arrays.asList("a")), new HashSet<>(Arrays.asList("a", "b")));
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
