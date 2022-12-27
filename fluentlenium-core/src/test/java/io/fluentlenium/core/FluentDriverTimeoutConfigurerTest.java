package io.fluentlenium.core;

import io.fluentlenium.configuration.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

/**
 * Unit test for {@link FluentDriverTimeoutConfigurer}.
 */
@RunWith(MockitoJUnitRunner.class)
public class FluentDriverTimeoutConfigurerTest {

    @Mock
    private WebDriver webDriver;
    @Mock
    private WebDriver.Options manage;
    @Mock
    private WebDriver.Timeouts timeouts;
    @Mock
    private Configuration configuration;
    private FluentDriverTimeoutConfigurer timeoutConfigurer;

    @Before
    public void setup() {
        timeoutConfigurer = new FluentDriverTimeoutConfigurer(configuration, webDriver);
    }

    @Test
    public void shouldConfigurePageLoadTimeout() {
        mockWebDriver();
        mockConfigurationValues(2000L, null, null);

        timeoutConfigurer.configureDriver();

        verify(timeouts).pageLoadTimeout(2000L, TimeUnit.MILLISECONDS);
        verifyNoMoreInteractions(timeouts);
    }

    @Test
    public void shouldConfigureImplicitlyWait() {
        mockWebDriver();
        mockConfigurationValues(null, 2000L, null);

        timeoutConfigurer.configureDriver();

        verify(timeouts).implicitlyWait(2000L, TimeUnit.MILLISECONDS);
        verifyNoMoreInteractions(timeouts);
    }

    @Test
    public void shouldConfigureScriptTimeout() {
        mockWebDriver();
        mockConfigurationValues(null, null, 2000L);

        timeoutConfigurer.configureDriver();

        verify(timeouts).setScriptTimeout(2000L, TimeUnit.MILLISECONDS);
        verifyNoMoreInteractions(timeouts);
    }

    @Test
    public void shouldNotConfigureAnyTimeout() {
        FluentDriverTimeoutConfigurer configurer = new FluentDriverTimeoutConfigurer(configuration, null);

        configurer.configureDriver();

        verify(webDriver, never()).manage();
    }

    private void mockWebDriver() {
        when(webDriver.manage()).thenReturn(manage);
        when(manage.timeouts()).thenReturn(timeouts);
    }

    private void mockConfigurationValues(Long pageLoadTimeout, Long implicitlyWait, Long scriptTimeout) {
        when(configuration.getPageLoadTimeout()).thenReturn(pageLoadTimeout);
        when(configuration.getImplicitlyWait()).thenReturn(implicitlyWait);
        when(configuration.getScriptTimeout()).thenReturn(scriptTimeout);
    }
}
