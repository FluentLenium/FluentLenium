package org.fluentlenium.configuration;

import org.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;

public class DefaultWebDriverFactories {
    @FactoryPriority(128)
    public static class FirefoxWebDriverFactory extends ReflectiveWebDriverFactory {
        public FirefoxWebDriverFactory() {
            super("firefox", "org.openqa.selenium.firefox.FirefoxDriver");
        }
    }

    @FactoryPriority(127)
    public static class MarionetteWebDriverFactory extends ReflectiveWebDriverFactory {
        public MarionetteWebDriverFactory() {
            super("marionette", "org.openqa.selenium.firefox.MarionetteDriver");
        }
    }

    @FactoryPriority(64)
    public static class ChromeWebDriverFactory extends ReflectiveWebDriverFactory {
        public ChromeWebDriverFactory() {
            super("chrome", "org.openqa.selenium.chrome.ChromeDriver");
        }
    }

    @FactoryPriority(32)
    public static class InternetExplorerWebDriverFactory extends ReflectiveWebDriverFactory {
        public InternetExplorerWebDriverFactory() {
            super("ie", "org.openqa.selenium.ie.InternetExplorerDriver");
        }
    }

    @FactoryPriority(31)
    public static class EdgeWebDriverFactory extends ReflectiveWebDriverFactory {
        public EdgeWebDriverFactory() {
            super("edge", "org.openqa.selenium.edge.EdgeDriver");
        }
    }

    @FactoryPriority(16)
    public static class SafaryWebDriverFactory extends ReflectiveWebDriverFactory {
        public SafaryWebDriverFactory() {
            super("safari", "org.openqa.selenium.safari.SafariDriver");
        }
    }

    @FactoryPriority(8)
    public static class PhantomJSWebDriverFactory extends ReflectiveWebDriverFactory {
        public PhantomJSWebDriverFactory() {
            super("phantomjs", "org.openqa.selenium.phantomjs.PhantomJSDriver");
        }
    }

    public static class RemoteWebDriverFactory extends ReflectiveWebDriverFactory {
        public RemoteWebDriverFactory() {
            super("remote", RemoteWebDriver.class);
        }

        @Override
        protected WebDriver newInstance(Class<? extends WebDriver> webDriverClass, ConfigurationProperties configuration,
                Object[] args)
                throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
            URL url = null;
            if (configuration != null) {
                String remoteUrl = configuration.getRemoteUrl();

                if (remoteUrl != null) {
                    try {
                        url = new URL(remoteUrl);
                    } catch (MalformedURLException e) {
                        throw new ConfigurationException("remoteUrl configuration property is not a valid URL.", e);
                    }
                }
            }

            Object[] urlArgs = new Object[2];
            urlArgs[0] = url;
            urlArgs[1] = args.length > 0 ? args[0] : new DesiredCapabilities();

            return newRemoteWebDriver(urlArgs);
        }

        protected WebDriver newRemoteWebDriver(Object[] args)
                throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
            WebDriver webDriver = ReflectionUtils.getConstructor(webDriverClass, URL.class, Capabilities.class).newInstance(args);
            return new Augmenter().augment(webDriver);
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
