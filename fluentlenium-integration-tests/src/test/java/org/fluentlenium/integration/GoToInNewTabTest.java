package org.fluentlenium.integration;

import org.fluentlenium.adapter.junit.jupiter.MockitoExtension;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;
import java.util.HashSet;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GoToInNewTabTest extends IntegrationFluentTest {
    @Mock
    private JavascriptWebDriver webDriver;

    @Mock
    private WebDriver.TargetLocator locator;

    @Test
    void checkGoToInNewTab() {
        when(webDriver.getWindowHandles()).thenReturn(new HashSet<>(singletonList("a")), new HashSet<>(Arrays.asList("a", "b")));
        when(webDriver.switchTo()).thenReturn(locator);
        goToInNewTab(DEFAULT_URL);
        verify(locator).window("b");
    }

    @Override
    public WebDriver newWebDriver() {
        return webDriver;
    }

    interface JavascriptWebDriver extends WebDriver, JavascriptExecutor {

    }

}
