package org.fluentlenium.configuration;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class WebDriversTest {
    private WebDrivers.Impl webDrivers;

    public static class CustomWebDriver extends HtmlUnitDriver {

    }

    public static class CustomWebDriverFactory implements WebDriverFactory {
        @Override
        public WebDriver newWebDriver(Capabilities capabilities) {
            return new CustomWebDriver();
        }

        @Override
        public String getName() {
            return "custom";
        }
    }

    @Before
    public void before() {
        webDrivers = new WebDrivers.Impl();
    }

    @Test
    public void testFirefox() {
        WebDriverFactory firefox = webDrivers.get("firefox");
        assertThat(firefox).isExactlyInstanceOf(DefaultWebDriverFactories.FirefoxWebDriverFactory.class);

        Class<? extends WebDriver> webDriverClass = ((ReflectiveWebDriverFactory) firefox).getWebDriverClass();
        assertThat(webDriverClass).isSameAs(FirefoxDriver.class);
    }

    @Test(expected = ConfigurationException.class)
    public void testRegisterFirefox() {
        webDrivers.register(new CustomWebDriverFactory());
    }

    @Test
    public void testCustomClassName() {
        WebDriverFactory customWebFactory = webDrivers.get(CustomWebDriver.class.getName());
        WebDriver webDriver = customWebFactory.newWebDriver(null);

        try {
            assertThat(webDriver).isExactlyInstanceOf(CustomWebDriver.class);
        } finally {
            webDriver.quit();
        }

    }

    @Test
    public void testCustomClassNameNewWebDriver() {
        WebDriver webDriver = webDrivers.newWebDriver(CustomWebDriver.class.getName(), null);

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
