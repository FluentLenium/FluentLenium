package org.fluentlenium.configuration;

import org.junit.Test;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReflectiveWebDriverFactoryTest {
    public static final class NoConstructorDriver extends HtmlUnitDriver {
    }

    public abstract static class AbstractDriver extends HtmlUnitDriver {
    }

    public static class CustomConstructorDriver extends HtmlUnitDriver {
        public CustomConstructorDriver(boolean javascript) {
            super(javascript);
        }
    }

    public static class FailingDriver extends HtmlUnitDriver {
        public FailingDriver() {
            throw new IllegalStateException();
        }
    }

    @Test
    public void testNonexstingClass() {
        ReflectiveWebDriverFactory webDriverFactory = new ReflectiveWebDriverFactory("doesnt-exists",
                "org.fluentlenium.ThisClassDoesntExists");
        assertThat(webDriverFactory.isAvailable()).isFalse();

        assertThatThrownBy(() -> webDriverFactory.newWebDriver(null, null))
                .isExactlyInstanceOf(ConfigurationException.class);

        assertThat(webDriverFactory.getWebDriverClass()).isNull();
    }

    @Test
    public void testNonWebDriverClass() {
        ReflectiveWebDriverFactory webDriverFactory = new ReflectiveWebDriverFactory("test-class", getClass().getName());
        assertThat(webDriverFactory.isAvailable()).isFalse();

        assertThatThrownBy(() -> webDriverFactory.newWebDriver(null, null))
                .isExactlyInstanceOf(ConfigurationException.class);

        assertThat(webDriverFactory.getWebDriverClass()).isSameAs(getClass());
    }

    @Test
    public void testAbstractClass() {
        ReflectiveWebDriverFactory webDriverFactory = new ReflectiveWebDriverFactory("abstract", AbstractDriver.class);
        assertThat(webDriverFactory.isAvailable()).isTrue();

        assertThatThrownBy(() -> webDriverFactory.newWebDriver(null, null))
                .isExactlyInstanceOf(ConfigurationException.class);
    }

    @Test
    public void testNoConstructorClass() {
        ReflectiveWebDriverFactory webDriverFactory = new ReflectiveWebDriverFactory("no-constructor", NoConstructorDriver.class);
        assertThat(webDriverFactory.isAvailable()).isTrue();

        WebDriver webDriver = webDriverFactory.newWebDriver(null, null);
        try {
            assertThat(webDriver).isExactlyInstanceOf(NoConstructorDriver.class);
        } finally {
            webDriver.quit();
        }
    }

    @Test
    public void testFailingDriverClass() {
        ReflectiveWebDriverFactory webDriverFactory = new ReflectiveWebDriverFactory("failing", FailingDriver.class);
        assertThat(webDriverFactory.isAvailable()).isTrue();

        assertThatThrownBy(() -> webDriverFactory.newWebDriver(null, null))
                .isExactlyInstanceOf(ConfigurationException.class);
    }

    @Test
    public void testCustomConstructorClassInvalidArguments() {
        ReflectiveWebDriverFactory webDriverFactory = new ReflectiveWebDriverFactory("custom_constructor",
                CustomConstructorDriver.class);
        assertThat(webDriverFactory.isAvailable()).isTrue();

        assertThatThrownBy(() -> webDriverFactory.newWebDriver(null, null))
                .isExactlyInstanceOf(ConfigurationException.class);
    }

    @Test
    public void testCustomConstructorClass() {
        ReflectiveWebDriverFactory webDriverFactory = new ReflectiveWebDriverFactory("custom_constructor",
                CustomConstructorDriver.class, true);
        assertThat(webDriverFactory.isAvailable()).isTrue();

        WebDriver webDriver = webDriverFactory.newWebDriver(null, null);
        try {
            assertThat(webDriver).isExactlyInstanceOf(CustomConstructorDriver.class);
        } finally {
            webDriver.quit();
        }
    }

    @Test
    public void testHtmlUnitWebDriver() {
        ReflectiveWebDriverFactory webDriverFactory = new DefaultWebDriverFactories.HtmlUnitWebDriverFactory();
        assertThat(webDriverFactory.isAvailable()).isTrue();
        assertThat(webDriverFactory.getWebDriverClass()).isSameAs(HtmlUnitDriver.class);

        assertThat(webDriverFactory.getNames())
                .containsExactly("htmlunit", HtmlUnitDriver.class.getName(), HtmlUnitDriver.class.getSimpleName());

        WebDriver webDriver = webDriverFactory.newWebDriver(DesiredCapabilities.htmlUnit(), null);
        try {
            assertThat(webDriver).isExactlyInstanceOf(HtmlUnitDriver.class);
            assertThat(((HasCapabilities) webDriver).getCapabilities().is("javascriptEnabled")).isTrue();
        } finally {
            webDriver.quit();
        }
    }

    @Test
    public void testHtmlUnitWebDriverCapabilities() {
        ReflectiveWebDriverFactory webDriverFactory = new DefaultWebDriverFactories.HtmlUnitWebDriverFactory();
        assertThat(webDriverFactory.isAvailable()).isTrue();
        assertThat(webDriverFactory.getWebDriverClass()).isSameAs(HtmlUnitDriver.class);

        assertThat(webDriverFactory.getNames())
                .containsExactly("htmlunit", HtmlUnitDriver.class.getName(), HtmlUnitDriver.class.getSimpleName());

        DesiredCapabilities desiredCapabilities = DesiredCapabilities.htmlUnit();
        desiredCapabilities.setJavascriptEnabled(false);
        desiredCapabilities.setBrowserName(BrowserType.HTMLUNIT);

        WebDriver webDriver = webDriverFactory.newWebDriver(desiredCapabilities, null);
        try {
            assertThat(webDriver).isExactlyInstanceOf(HtmlUnitDriver.class);
            assertThat(((HasCapabilities) webDriver).getCapabilities().is("javascriptEnabled")).isFalse();
        } finally {
            webDriver.quit();
        }
    }
}
