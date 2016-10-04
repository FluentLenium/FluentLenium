package org.fluentlenium.configuration;

import org.assertj.core.api.Assertions;
import org.fluentlenium.configuration.DefaultWebDriverFactories.RemoteWebDriverFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class RemoteWebDriverTest {
    private RemoteWebDriverFactory factorySpy;

    @Mock
    private WebDriver webDriver;

    @Before
    public void before() {
        RemoteWebDriverFactory factory = new RemoteWebDriverFactory() {
            @Override
            protected WebDriver newRemoteWebDriver(Object... args)
                    throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
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

        verify(factorySpy).newRemoteWebDriver(new Object[]{null, defaultCapabilities});
    }

    @Test
    public void testCustomRemoteUrl()
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException,
            MalformedURLException {

        ProgrammaticConfiguration programmaticConfiguration = new ProgrammaticConfiguration();
        programmaticConfiguration.setRemoteUrl("http://localhost:4444");

        WebDriver newWebDriver = factorySpy.newWebDriver(null, programmaticConfiguration);
        Assertions.assertThat(newWebDriver).isSameAs(webDriver);

        DesiredCapabilities defaultCapabilities = new DesiredCapabilities();

        verify(factorySpy).newRemoteWebDriver(new Object[]{new URL("http://localhost:4444"), defaultCapabilities});
    }

    @Test(expected = ConfigurationException.class)
    public void testInvalidRemoteUrl()
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException,
            MalformedURLException {

        ProgrammaticConfiguration programmaticConfiguration = new ProgrammaticConfiguration();
        programmaticConfiguration.setRemoteUrl("dummy");

        factorySpy.newWebDriver(null, programmaticConfiguration);
    }

    @Test
    public void testCustomRemoteUrlAndCapabilities()
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException,
            MalformedURLException {

        ProgrammaticConfiguration programmaticConfiguration = new ProgrammaticConfiguration();
        programmaticConfiguration.setRemoteUrl("http://localhost:4444");

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();

        WebDriver newWebDriver = factorySpy.newWebDriver(capabilities, programmaticConfiguration);
        Assertions.assertThat(newWebDriver).isSameAs(webDriver);

        verify(factorySpy).newRemoteWebDriver(new Object[]{new URL("http://localhost:4444"), capabilities});
    }

    @Test
    public void testCustomCapabilities()
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException,
            MalformedURLException {

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();

        WebDriver newWebDriver = factorySpy.newWebDriver(capabilities, null);
        Assertions.assertThat(newWebDriver).isSameAs(webDriver);

        verify(factorySpy).newRemoteWebDriver(new Object[]{null, capabilities});
    }
}
