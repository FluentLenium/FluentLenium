package org.fluentlenium.configuration;

import org.junit.Before;
import org.junit.Test;
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
        public WebDriver newWebDriver() {
            return new CustomWebDriver();
        }

        @Override
        public String[] getNames() {
            return new String[]{"custom-factory"};
        }
    }

    @Before
    public void before() {
        webDrivers = new WebDrivers.Impl();
    }

    @Test
    public void testFirefox() {
        WebDriverFactory firefox = webDrivers.get("firefox");
        assertThat(firefox).isExactlyInstanceOf(ReflectiveWebDriverFactory.class);

        Class<? extends WebDriver> webDriverClass = ((ReflectiveWebDriverFactory) firefox).getWebDriverClass();
        assertThat(webDriverClass).isSameAs(FirefoxDriver.class);
    }

    @Test(expected = ConfigurationException.class)
    public void testRegisterFirefox() {
        webDrivers.register("firefox", new CustomWebDriverFactory());
    }

    @Test
    public void testCustomClassName() {
        WebDriverFactory customWebFactory = webDrivers.get(CustomWebDriver.class.getName());
        WebDriver webDriver = customWebFactory.newWebDriver();

        try {
            assertThat(webDriver).isExactlyInstanceOf(CustomWebDriver.class);
        } finally {
            webDriver.quit();
        }

    }

    @Test
    public void testCustomClassNameNewWebDriver() {
        WebDriver webDriver = webDrivers.newWebDriver(CustomWebDriver.class.getName());

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
