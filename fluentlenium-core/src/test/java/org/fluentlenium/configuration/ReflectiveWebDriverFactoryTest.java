package org.fluentlenium.configuration;

import org.assertj.core.api.ThrowableAssert;
import org.junit.Test;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReflectiveWebDriverFactoryTest {
    public static class NoConstructorDriver extends HtmlUnitDriver {
        private NoConstructorDriver() {
        }
    }

    public static abstract class AbstractDriver extends HtmlUnitDriver {
        public AbstractDriver() {
        }
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
    public void testInexistantClass() {
        final ReflectiveWebDriverFactory webDriverFactory = new ReflectiveWebDriverFactory("doesnt-exists",
                "org.fluentlenium.ThisClassDoesntExists");
        assertThat(webDriverFactory.isAvailable()).isFalse();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                webDriverFactory.newWebDriver(null, null);
            }
        }).isExactlyInstanceOf(ConfigurationException.class);

        assertThat(webDriverFactory.getWebDriverClass()).isNull();
    }

    @Test
    public void testNonWebDriverClass() {
        final ReflectiveWebDriverFactory webDriverFactory = new ReflectiveWebDriverFactory("test-class", getClass().getName());
        assertThat(webDriverFactory.isAvailable()).isFalse();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                webDriverFactory.newWebDriver(null, null);
            }
        }).isExactlyInstanceOf(ConfigurationException.class);

        assertThat(webDriverFactory.getWebDriverClass()).isSameAs(getClass());
    }

    @Test
    public void testAbstractClass() {
        final ReflectiveWebDriverFactory webDriverFactory = new ReflectiveWebDriverFactory("abstract", AbstractDriver.class);
        assertThat(webDriverFactory.isAvailable()).isTrue();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                webDriverFactory.newWebDriver(null, null);
            }
        }).isExactlyInstanceOf(ConfigurationException.class);
    }

    @Test
    public void testNoConstructorClass() {
        final ReflectiveWebDriverFactory webDriverFactory = new ReflectiveWebDriverFactory("no-constructor",
                NoConstructorDriver.class);
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
        final ReflectiveWebDriverFactory webDriverFactory = new ReflectiveWebDriverFactory("failing", FailingDriver.class);
        assertThat(webDriverFactory.isAvailable()).isTrue();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                webDriverFactory.newWebDriver(null, null);
            }
        }).isExactlyInstanceOf(ConfigurationException.class);
    }

    @Test
    public void testCustomConstructorClassInvalidArguments() {
        final ReflectiveWebDriverFactory webDriverFactory = new ReflectiveWebDriverFactory("custom_constructor",
                CustomConstructorDriver.class);
        assertThat(webDriverFactory.isAvailable()).isTrue();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                webDriverFactory.newWebDriver(null, null);
            }
        }).isExactlyInstanceOf(ConfigurationException.class);
    }

    @Test
    public void testCustomConstructorClass() {
        final ReflectiveWebDriverFactory webDriverFactory = new ReflectiveWebDriverFactory("custom_constructor",
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

        WebDriver webDriver = webDriverFactory.newWebDriver(null, null);
        try {
            assertThat(webDriver).isExactlyInstanceOf(HtmlUnitDriver.class);
            assertThat(((HasCapabilities) webDriver).getCapabilities().isJavascriptEnabled()).isTrue();
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

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setJavascriptEnabled(false);

        WebDriver webDriver = webDriverFactory.newWebDriver(desiredCapabilities, null);
        try {
            assertThat(webDriver).isExactlyInstanceOf(HtmlUnitDriver.class);
            assertThat(((HasCapabilities) webDriver).getCapabilities().isJavascriptEnabled()).isFalse();
        } finally {
            webDriver.quit();
        }
    }
}
