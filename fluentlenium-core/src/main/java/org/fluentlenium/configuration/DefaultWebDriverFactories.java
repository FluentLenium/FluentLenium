package org.fluentlenium.configuration;

import org.openqa.selenium.remote.DesiredCapabilities;

public class DefaultWebDriverFactories {
    public static class FirefoxWebDriverFactory extends ReflectiveWebDriverFactory {
        public FirefoxWebDriverFactory() {
            super("firefox", "org.openqa.selenium.firefox.FirefoxDriver");
        }
    }

    public static class MarionetteWebDriverFactory extends ReflectiveWebDriverFactory {
        public MarionetteWebDriverFactory() {
            super("marionette", "org.openqa.selenium.firefox.MarionetteDriver");
        }
    }

    public static class ChromeWebDriverFactory extends ReflectiveWebDriverFactory {
        public ChromeWebDriverFactory() {
            super("chrome", "org.openqa.selenium.chrome.ChromeDriver");
        }
    }

    public static class InternetExplorerWebDriverFactory extends ReflectiveWebDriverFactory {
        public InternetExplorerWebDriverFactory() {
            super("ie", "org.openqa.selenium.ie.InternetExplorerDriver");
        }
    }

    public static class HtmlUnitWebDriverFactory extends ReflectiveWebDriverFactory {
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

    public static class SafaryWebDriverFactory extends ReflectiveWebDriverFactory {
        public SafaryWebDriverFactory() {
            super("safari", "org.openqa.selenium.safari.SafariDriver");
        }
    }

    public static class PhantomJSWebDriverFactory extends ReflectiveWebDriverFactory {
        public PhantomJSWebDriverFactory() {
            super("phantomjs", "org.openqa.selenium.phantomjs.PhantomJSDriver");
        }
    }

}
