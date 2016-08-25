package org.fluentlenium.integration;

import com.google.common.collect.ImmutableSet;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;
import java.util.HashSet;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WindowActionsTest extends IntegrationFluentTest {
    @Mock
    private JavascriptWebDriver webDriver;

    @Mock
    private WebDriver.TargetLocator locator;

    public WindowActionsTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void checkGoToInNewTab() {
        when(webDriver.getWindowHandles()).thenReturn(ImmutableSet.of("s"),
                new HashSet<String>(Arrays.asList("a", "b"), new HashSet<String>(Arrays.asList("a", "b")));
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
