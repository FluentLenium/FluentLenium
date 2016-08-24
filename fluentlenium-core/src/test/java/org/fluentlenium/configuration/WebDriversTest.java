package org.fluentlenium.configuration;

import org.assertj.core.api.ThrowableAssert;
import org.fluentlenium.utils.ReflectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.LinkedHashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class WebDriversTest {
    private WebDrivers.Impl webDrivers;

    public static class CustomWebDriver extends HtmlUnitDriver {

    }

    public static class AnotherFactory implements WebDriverFactory {
        @Override
        public WebDriver newWebDriver(Capabilities capabilities) {
            return new CustomWebDriver();
        }

        @Override
        public String getName() {
            return "another";
        }

        @Override
        public int getPriority() {
            return 2048;
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

    @Test
    public void testDefault() {
        WebDriverFactory webDriverFactory = webDrivers.get(null);
        assertThat(webDriverFactory).isExactlyInstanceOf(AnotherFactory.class);
    }

    @Test
    public void testNoDefault() throws NoSuchFieldException, IllegalAccessException {
        ReflectionUtils.set(WebDrivers.Impl.class.getDeclaredField("factories"), webDrivers, new LinkedHashMap<>());

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                webDrivers.get(null);

            }
        }).isExactlyInstanceOf(ConfigurationException.class)
                .hasMessage("No WebDriverFactory is available. You need add least one supported " +
                        "WebDriver in your classpath.");
    }

    @Test(expected = ConfigurationException.class)
    public void testRegisterFirefox() {
        webDrivers.register(new AnotherFactory());
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
