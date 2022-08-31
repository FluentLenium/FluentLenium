package org.fluentlenium.configuration;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import org.assertj.core.api.Assertions;
import org.fluentlenium.configuration.DefaultWebDriverFactories.RemoteWebDriverFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class RemoteWebDriverTest {
    private RemoteWebDriverFactory factorySpy;
    private ChromeOptions chromeOptions = new ChromeOptions();
    private static final String GRID_SAMPLE_URL = "http://localhost:4444";

    @Mock
    private WebDriver webDriver;

    @Before
    public void before() {
        RemoteWebDriverFactory factory = new RemoteWebDriverFactory(chromeOptions) {
            @Override
            protected WebDriver newRemoteWebDriver(Object... args) {
                return webDriver;
            }
        };
        factorySpy = spy(factory);
    }

    @Test
    public void testDefault()
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        WebDriver newWebDriver = factorySpy.newWebDriver(null, null);
        Assertions.assertThat(newWebDriver).isSameAs(webDriver);

        DesiredCapabilities defaultCapabilities = new DesiredCapabilities();

        verify(factorySpy).newRemoteWebDriver(null, defaultCapabilities);
    }

    @Test
    public void testCustomRemoteUrl()
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException,
            MalformedURLException {

        ProgrammaticConfiguration programmaticConfiguration = new ProgrammaticConfiguration();
        programmaticConfiguration.setRemoteUrl(GRID_SAMPLE_URL);

        MutableCapabilities defaultCapabilities = new ChromeOptions();

        WebDriver newWebDriver = factorySpy.newWebDriver(defaultCapabilities, programmaticConfiguration);
        Assertions.assertThat(newWebDriver).isSameAs(webDriver);


        verify(factorySpy).newRemoteWebDriver(new URL(GRID_SAMPLE_URL), defaultCapabilities);
    }

    @Test(expected = ConfigurationException.class)
    public void testInvalidRemoteUrl() {

        ProgrammaticConfiguration programmaticConfiguration = new ProgrammaticConfiguration();
        programmaticConfiguration.setRemoteUrl("dummy");

        factorySpy.newWebDriver(null, programmaticConfiguration);
    }

    @Test
    public void testCustomRemoteUrlAndCapabilities()
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException,
            MalformedURLException {

        ProgrammaticConfiguration programmaticConfiguration = new ProgrammaticConfiguration();
        programmaticConfiguration.setRemoteUrl(GRID_SAMPLE_URL);

        MutableCapabilities capabilities = new FirefoxOptions();

        WebDriver newWebDriver = factorySpy.newWebDriver(capabilities, programmaticConfiguration);
        Assertions.assertThat(newWebDriver).isSameAs(webDriver);

        verify(factorySpy).newRemoteWebDriver(new URL(GRID_SAMPLE_URL), capabilities);
    }

    @Test
    public void testCustomCapabilities()
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        MutableCapabilities capabilities = new ChromeOptions();

        WebDriver newWebDriver = factorySpy.newWebDriver(capabilities, null);
        Assertions.assertThat(newWebDriver).isSameAs(webDriver);

        verify(factorySpy).newRemoteWebDriver(null, capabilities);
    }
}
