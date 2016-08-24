package org.fluentlenium.configuration;

import lombok.experimental.UtilityClass;
import org.openqa.selenium.remote.DesiredCapabilities;

@UtilityClass
public class DefaultWebDriverFactories {
    public class FirefoxWebDriverFactory extends ReflectiveWebDriverFactory {
        public FirefoxWebDriverFactory() {
            super("firefox", "org.openqa.selenium.firefox.FirefoxDriver");
        }
    }

    public class MarionetteWebDriverFactory extends ReflectiveWebDriverFactory {
        public MarionetteWebDriverFactory() {
            super("marionette", "org.openqa.selenium.firefox.MarionetteDriver");
        }
    }

    public class ChromeWebDriverFactory extends ReflectiveWebDriverFactory {
        public ChromeWebDriverFactory() {
            super("chrome", "org.openqa.selenium.chrome.ChromeDriver");
        }
    }

    public class InternetExplorerWebDriverFactory extends ReflectiveWebDriverFactory {
        public InternetExplorerWebDriverFactory() {
            super("ie", "org.openqa.selenium.ie.InternetExplorerDriver");
        }
    }

    public class EdgeWebDriverFactory extends ReflectiveWebDriverFactory {
        public EdgeWebDriverFactory() {
            super("edge", "org.openqa.selenium.edge.EdgeDriver");
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
    }

    public class SafaryWebDriverFactory extends ReflectiveWebDriverFactory {
        public SafaryWebDriverFactory() {
            super("safari", "org.openqa.selenium.safari.SafariDriver");
        }
    }

    public class PhantomJSWebDriverFactory extends ReflectiveWebDriverFactory {
        public PhantomJSWebDriverFactory() {
            super("phantomjs", "org.openqa.selenium.phantomjs.PhantomJSDriver");
        }
    }

}
