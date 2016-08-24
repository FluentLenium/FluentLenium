package org.fluentlenium.configuration;

import lombok.experimental.UtilityClass;
import org.openqa.selenium.remote.DesiredCapabilities;

@UtilityClass
public class DefaultWebDriverFactories {
    public class FirefoxWebDriverFactory extends ReflectiveWebDriverFactory {
        public FirefoxWebDriverFactory() {
            super("firefox", "org.openqa.selenium.firefox.FirefoxDriver");
        }

        @Override
        public int getPriority() {
            return 128;
        }
    }

    public class MarionetteWebDriverFactory extends ReflectiveWebDriverFactory {
        public MarionetteWebDriverFactory() {
            super("marionette", "org.openqa.selenium.firefox.MarionetteDriver");
        }

        @Override
        public int getPriority() {
            return 127;
        }
    }

    public class ChromeWebDriverFactory extends ReflectiveWebDriverFactory {
        public ChromeWebDriverFactory() {
            super("chrome", "org.openqa.selenium.chrome.ChromeDriver");
        }

        @Override
        public int getPriority() {
            return 64;
        }
    }

    public class InternetExplorerWebDriverFactory extends ReflectiveWebDriverFactory {
        public InternetExplorerWebDriverFactory() {
            super("ie", "org.openqa.selenium.ie.InternetExplorerDriver");
        }

        @Override
        public int getPriority() {
            return 32;
        }
    }

    public class EdgeWebDriverFactory extends ReflectiveWebDriverFactory {
        public EdgeWebDriverFactory() {
            super("edge", "org.openqa.selenium.edge.EdgeDriver");
        }

        @Override
        public int getPriority() {
            return 31;
        }
    }

    public class SafaryWebDriverFactory extends ReflectiveWebDriverFactory {
        public SafaryWebDriverFactory() {
            super("safari", "org.openqa.selenium.safari.SafariDriver");
        }

        @Override
        public int getPriority() {
            return 16;
        }
    }

    public class PhantomJSWebDriverFactory extends ReflectiveWebDriverFactory {
        public PhantomJSWebDriverFactory() {
            super("phantomjs", "org.openqa.selenium.phantomjs.PhantomJSDriver");
        }

        @Override
        public int getPriority() {
            return 8;
        }
    }

    public class HtmlUnitWebDriverFactory extends ReflectiveWebDriverFactory {
        public HtmlUnitWebDriverFactory() {
            super("htmlunit", "org.openqa.selenium.htmlunit.HtmlUnitDriver");
        }

        @Override
        protected DesiredCapabilities newDefaultCapabilities() {
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setJavascriptEnabled(true);
            return desiredCapabilities;
        }

        @Override
        public int getPriority() {
            return -128;
        }
    }

}
