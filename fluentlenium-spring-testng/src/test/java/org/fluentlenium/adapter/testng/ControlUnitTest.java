package org.fluentlenium.adapter.testng;

import org.fluentlenium.adapter.FluentControlContainer;
import org.fluentlenium.configuration.Configuration;
import org.fluentlenium.configuration.DefaultConfigurationFactory;
import org.fluentlenium.core.FluentControl;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.Capabilities;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle.DEFAULT;
import static org.fluentlenium.configuration.ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(locations = {"classpath:spring-test-config.xml"})
public class ControlUnitTest {

    @Mock
    private FluentControlContainer fluentControlContainer;

    @Mock
    private FluentControl fluentControl;

    @Mock
    private Configuration configuration;

    private SpringTestNGControl control;

    @BeforeMethod
    public void init() {
        MockitoAnnotations.openMocks(this);
        when(fluentControlContainer.getFluentControl()).thenReturn(fluentControl);
        control = new SpringTestNGControl(fluentControlContainer, configuration);
    }

    @AfterMethod
    public void cleanup() {
        Mockito.reset();
    }

    @Test
    public void shouldCallGetDriver() {
        control.getDriver();
        verify(fluentControl, times(1)).getDriver();
    }

    @Test
    public void shouldCallGetAppiumDriver() {
        control.getAppiumDriver();
        verify(fluentControl, times(1)).getAppiumDriver();
    }

    @Test
    public void shouldCallGetConfigurationDefaults() {
        control.getConfigurationDefaults();
        verify(configuration, times(1)).getConfigurationDefaults();
    }

    @Test
    public void shouldCallGetSetAwaitPollingEvery() {
        control.getAwaitPollingEvery();
        control.setAwaitPollingEvery(1L);
        verify(configuration, times(1)).getAwaitPollingEvery();
        verify(configuration, times(1)).setAwaitPollingEvery(1L);
    }

    @Test
    public void shouldCallGetSetAwaitAtMost() {
        control.getAwaitAtMost();
        control.setAwaitAtMost(1L);
        verify(configuration, times(1)).getAwaitAtMost();
        verify(configuration, times(1)).setAwaitAtMost(1L);
    }

    @Test
    public void shouldCallGetSetCustomProperty() {
        control.setCustomProperty("key", "value");
        control.getCustomProperty("key");
        verify(configuration, times(1)).setCustomProperty("key", "value");
        verify(configuration, times(1)).getCustomProperty("key");
    }

    @Test
    public void shouldCallGetSetBrowserTimeoutRetries() {
        control.setBrowserTimeoutRetries(1);
        control.getBrowserTimeoutRetries();
        verify(configuration, times(1)).setBrowserTimeoutRetries(1);
        verify(configuration, times(1)).getBrowserTimeoutRetries();
    }

    @Test
    public void shouldCallGetSetBrowserTimeout() {
        control.setBrowserTimeout(1L);
        control.getBrowserTimeout();
        verify(configuration, times(1)).setBrowserTimeout(1L);
        verify(configuration, times(1)).getBrowserTimeout();
    }

    @Test
    public void shouldCallGetSetWebDriver() {
        control.setWebDriver("chrome");
        control.getWebDriver();
        verify(configuration, times(1)).setWebDriver("chrome");
        verify(configuration, times(1)).getWebDriver();
    }

    @Test
    public void shouldCallGetSetDeleteCookies() {
        control.setDeleteCookies(true);
        control.getDeleteCookies();
        verify(configuration, times(1)).setDeleteCookies(true);
        verify(configuration, times(1)).getDeleteCookies();
    }

    @Test
    public void shouldCallGetSetScreenshotPath() {
        control.setScreenshotPath("/path");
        control.getScreenshotPath();
        verify(configuration, times(1)).setScreenshotPath("/path");
        verify(configuration, times(1)).getScreenshotPath();
    }

    @Test
    public void shouldCallGetSetScreenshotMode() {
        control.setScreenshotMode(AUTOMATIC_ON_FAIL);
        control.getScreenshotMode();
        verify(configuration, times(1)).setScreenshotMode(AUTOMATIC_ON_FAIL);
        verify(configuration, times(1)).getScreenshotMode();
    }

    @Test
    public void shouldCallGetSetHtmlDumpPath() {
        control.setHtmlDumpPath("/path");
        control.getHtmlDumpPath();
        verify(configuration, times(1)).setHtmlDumpPath("/path");
        verify(configuration, times(1)).getHtmlDumpPath();;
    }

    @Test
    public void shouldCallGetSetHtmlDumpMode() {
        control.setHtmlDumpMode(AUTOMATIC_ON_FAIL);
        control.getHtmlDumpMode();
        verify(configuration, times(1)).setHtmlDumpMode(AUTOMATIC_ON_FAIL);
        verify(configuration, times(1)).getHtmlDumpMode();
    }

    @Test
    public void shouldCallGetSetBaseUrl() {
        control.setBaseUrl("url");
        control.getBaseUrl();
        verify(configuration, times(1)).setBaseUrl("url");
        verify(configuration, times(1)).getBaseUrl();
    }

    @Test
    public void shouldCallGetSetPageLoadTimeout() {
        control.setPageLoadTimeout(1L);
        control.getPageLoadTimeout();
        verify(configuration, times(1)).setPageLoadTimeout(1L);
        verify(configuration, times(1)).getPageLoadTimeout();
    }

    @Test
    public void shouldCallGetSetConfigurationFactory() {
        control.setConfigurationFactory(DefaultConfigurationFactory.class);
        control.getConfigurationFactory();
        verify(configuration, times(1)).setConfigurationFactory(DefaultConfigurationFactory.class);
        verify(configuration, times(1)).getConfigurationFactory();
    }

    @Test
    public void shouldCallGetSetDriverLifecycle() {
        control.setDriverLifecycle(DEFAULT);
        control.getDriverLifecycle();
        verify(configuration, times(1)).setDriverLifecycle(DEFAULT);
        verify(configuration, times(1)).getDriverLifecycle();
    }

    @Test
    public void shouldCallGetSetRemote() {
        control.setRemoteUrl("http://");
        control.getRemoteUrl();
        verify(configuration, times(1)).setRemoteUrl("http://");
        verify(configuration, times(1)).getRemoteUrl();
    }

    @Test
    public void shouldCallGetSetEventsEnabled() {
        control.setEventsEnabled(true);
        control.getEventsEnabled();
        verify(configuration, times(1)).setEventsEnabled(true);
        verify(configuration, times(1)).getEventsEnabled();
    }

    @Test
    public void shouldCallGetSetScriptTimeout() {
        control.setScriptTimeout(1L);
        control.getScriptTimeout();
        verify(configuration, times(1)).setScriptTimeout(1L);
        verify(configuration, times(1)).getScriptTimeout();
    }

    @Test
    public void shouldCallGetSetImplicitlyWait() {
        control.setImplicitlyWait(1L);
        control.getImplicitlyWait();
        verify(configuration, times(1)).setImplicitlyWait(1L);
        verify(configuration, times(1)).getImplicitlyWait();
    }

    @Test
    public void shouldCallGetSetCapabilities() {
        Capabilities capabilities = Mockito.mock(Capabilities.class);
        control.setCapabilities(capabilities);
        control.getCapabilities();
        verify(configuration, times(1)).setCapabilities(capabilities);
        verify(configuration, times(1)).getCapabilities();
    }

}
