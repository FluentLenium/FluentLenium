package org.fluentlenium.configuration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test for {@link WebDriversRegistryImpl}.
 */
@RunWith(Parameterized.class)
public class WebDriversRegistryImplSpecificDriversTest {
    private WebDriversRegistryImpl webDrivers;

    @Parameter
    public String browserName;

    @Parameter(1)
    public Class<ReflectiveWebDriverFactory> driverFactoryClass;

    @Parameter(2)
    public Class<? extends WebDriver> driverClass;

    @Before
    public void before() {
        webDrivers = new WebDriversRegistryImpl();
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"firefox", DefaultWebDriverFactories.FirefoxWebDriverFactory.class, FirefoxDriver.class},
                {"chrome", DefaultWebDriverFactories.ChromeWebDriverFactory.class, ChromeDriver.class},
                {"ie", DefaultWebDriverFactories.InternetExplorerWebDriverFactory.class, InternetExplorerDriver.class},
                {"edge", DefaultWebDriverFactories.EdgeWebDriverFactory.class, EdgeDriver.class},
                {"opera", DefaultWebDriverFactories.OperaWebDriverFactory.class, OperaDriver.class},
                {"safari", DefaultWebDriverFactories.SafariWebDriverFactory.class, SafariDriver.class},
                {"phantomjs", DefaultWebDriverFactories.PhantomJSWebDriverFactory.class, PhantomJSDriver.class}
        });
    }

    @Test
    public void testBrowser() {
        WebDriverFactory browser = webDrivers.get(browserName);
        assertThat(browser).isExactlyInstanceOf(driverFactoryClass);

        Class<? extends WebDriver> webDriverClass = ((ReflectiveWebDriverFactory) browser).getWebDriverClass();
        assertThat(webDriverClass).isSameAs(driverClass);
    }
}
