package org.fluentlenium.configuration;

import org.openqa.selenium.remote.DesiredCapabilities;

public class DefaultWebDriverFactories {
    public static class FirefoxWebDriverFactory extends ReflectiveWebDriverFactory {
        public FirefoxWebDriverFactory() {
            super("firefox", "org.openqa.selenium.firefox.FirefoxDriver");
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

}
