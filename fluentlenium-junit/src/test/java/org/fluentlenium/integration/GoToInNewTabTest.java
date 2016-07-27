package org.fluentlenium.integration;

import org.fluentlenium.core.domain.FluentJavascript;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;
import java.util.HashSet;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GoToInNewTabTest extends LocalFluentCase {
    WebDriver webDriver = Mockito.mock(WebDriver.class);
    WebDriver.TargetLocator locator = Mockito.mock(WebDriver.TargetLocator.class);

    @Test
    public void checkGoToInNewTab() {
        when(webDriver.getWindowHandles()).thenReturn(new HashSet<String>(Arrays.asList("a")),
                new HashSet<String>(Arrays.asList("a", "b")));
        when(webDriver.switchTo()).thenReturn(locator);
        goToInNewTab(DEFAULT_URL);
        verify(locator).window("b");
    }

    @Override
    public FluentJavascript executeScript(String script, Object... args) {
        return null; // do nothing, it's a unit test
    }

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }

}
