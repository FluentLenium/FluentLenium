package org.fluentlenium.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.LinkedHashMap;

import org.fluentlenium.utils.ReflectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.safari.SafariDriver;

/**
 * Unit test for {@link WebDriversRegistryImpl}.
 */
public class WebDriversRegistryImplTest {
    private WebDriversRegistryImpl webDrivers;

    public static class CustomWebDriver extends HtmlUnitDriver {
    }

    @FactoryPriority(2048)
    @FactoryName("another")
    public static class AnotherFactory implements WebDriverFactory {
        @Override
        public WebDriver newWebDriver(Capabilities capabilities, ConfigurationProperties configuration) {
            return new CustomWebDriver();
        }
    }

    @FactoryName("another-default")
    @DefaultFactory
    public static class AnotherDefaultFactory implements WebDriverFactory {
        @Override
        public WebDriver newWebDriver(Capabilities capabilities, ConfigurationProperties configuration) {
            return new CustomWebDriver();
        }
    }

    @Before
    public void before() {
        webDrivers = new WebDriversRegistryImpl();
    }

    @Test
    public void testDefault() {
        WebDriverFactory webDriverFactory = webDrivers.get(null);
        assertThat(webDriverFactory).isExactlyInstanceOf(AnotherFactory.class);
    }

    @Test
    public void testNoDefault() throws NoSuchFieldException, IllegalAccessException {
        ReflectionUtils.set(AbstractFactoryRegistryImpl.class.getDeclaredField("factories"),
                webDrivers, new LinkedHashMap<>());

        assertThatThrownBy(() -> webDrivers.get(null)).isExactlyInstanceOf(ConfigurationException.class)
                .hasMessage("No WebDriverFactory is available. You need add least one supported "
                        + "WebDriver in your classpath.");
    }

    @Test(expected = ConfigurationException.class)
    public void testRegisterExistingNameShouldFail() {
        webDrivers.register(new AnotherFactory());
    }

    @Test
    public void testRegisterExistingNameShouldNotFailWhenDefault() {
        webDrivers.register(new AnotherDefaultFactory());
    }

    @Test
    public void testCustomClassName() {
        WebDriverFactory customWebFactory = webDrivers.get(CustomWebDriver.class.getName());
        WebDriver webDriver = customWebFactory.newWebDriver(null, null);

        try {
            assertThat(webDriver).isExactlyInstanceOf(CustomWebDriver.class);
        } finally {
            webDriver.quit();
        }

    }

    @Test
    public void testCustomClassNameNewWebDriver() {
        WebDriver webDriver = webDrivers.newWebDriver(CustomWebDriver.class.getName(), null, null);

        try {
            assertThat(webDriver).isExactlyInstanceOf(CustomWebDriver.class);
        } finally {
            webDriver.quit();
        }
    }

    @Test(expected = ConfigurationException.class)
    public void testInvalidName() {
        webDrivers.get("dummy");
    }

    @Test
    public void testSingleton() {
        assertThat(WebDrivers.INSTANCE.get("firefox")).isNotNull();
        assertThat(WebDrivers.INSTANCE.get("htmlunit")).isNotNull();
    }

}
