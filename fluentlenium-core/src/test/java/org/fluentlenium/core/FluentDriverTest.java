package org.fluentlenium.core;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.fluentlenium.configuration.Configuration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;

/**
 * Unit test for {@link FluentDriver}.
 */
@RunWith(MockitoJUnitRunner.class)
public class FluentDriverTest {

    @Mock
    private WebDriver webDriver;
    @Mock
    private Configuration configuration;
    @Mock
    private FluentControl adapter;
    private FluentDriver fluentDriver;

    @Test
    public void shouldQuitDriverIfItsPresent() {
        fluentDriver = spy(new FluentDriver(webDriver, configuration, adapter));
        doNothing().when(fluentDriver).releaseFluent();

        fluentDriver.quit();

        verify(webDriver).quit();
        verify(fluentDriver).releaseFluent();
    }

    @Test
    public void shouldOnlyReleaseFluentIfDriverIsNotPresent() {
        fluentDriver = spy(new FluentDriver(null, configuration, adapter));
        doNothing().when(fluentDriver).releaseFluent();

        fluentDriver.quit();

        verify(fluentDriver).getDriver();
        verify(fluentDriver).releaseFluent();
    }
}
