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

/**
 * Utility class with default WebDriver factories.
 */
public class DefaultWebDriverFactories {
    /**
     * Firefox WebDriver factory.
     */
    @FactoryPriority(128)
    @DefaultFactory
    public static class FirefoxWebDriverFactory extends ReflectiveWebDriverFactory {
        /**
         * Creates a new firefox WebDriver factory.
         */
        public FirefoxWebDriverFactory() {
            super("firefox", "org.openqa.selenium.firefox.FirefoxDriver");
        }
    }

    /**
     * Chrome WebDriver factory.
     */
    @FactoryPriority(64)
    @DefaultFactory
    public static class ChromeWebDriverFactory extends ReflectiveWebDriverFactory {
        /**
         * Creates a new chrome WebDriver factory.
         */
        public ChromeWebDriverFactory() {
            super("chrome", "org.openqa.selenium.chrome.ChromeDriver");
        }
    }

    /**
     * Internet Explorer WebDriver factory.
     */
    @FactoryPriority(32)
    @DefaultFactory
    public static class InternetExplorerWebDriverFactory extends ReflectiveWebDriverFactory {
        /**
         * Creates a new internet explorer WebDriver factory.
         */
        public InternetExplorerWebDriverFactory() {
            super("ie", "org.openqa.selenium.ie.InternetExplorerDriver");
        }
    }

    /**
     * Edge WebDriver factory.
     */
    @FactoryPriority(31)
    @DefaultFactory
    public static class EdgeWebDriverFactory extends ReflectiveWebDriverFactory {
        /**
         * Creates a new edge WebDriver factory.
         */
        public EdgeWebDriverFactory() {
            super("edge", "org.openqa.selenium.edge.EdgeDriver");
        }
    }

    /**
     * Safary WebDriver factory.
     */
    @FactoryPriority(16)
    @DefaultFactory
    public static class SafariWebDriverFactory extends ReflectiveWebDriverFactory {
        /**
         * Creates a new safary WebDriver factory.
         */
        public SafariWebDriverFactory() {
            super("safari", "org.openqa.selenium.safari.SafariDriver");
        }
    }

    /**
     * PhantomJS WebDriver factory.
     */
    @FactoryPriority(8)
    @DefaultFactory
    public static class PhantomJSWebDriverFactory extends ReflectiveWebDriverFactory {
        /**
         * Creates a new phantomJs WebDriver factory.
         */
        public PhantomJSWebDriverFactory() {
            super("phantomjs", "org.openqa.selenium.phantomjs.PhantomJSDriver");
        }
    }

    /**
     * Remote WebDriver factory.
     */
    @DefaultFactory
    public static class RemoteWebDriverFactory extends ReflectiveWebDriverFactory {
        /**
         * Creates a new remote WebDriver factory.
         */
        public RemoteWebDriverFactory() {
            super("remote", RemoteWebDriver.class);
        }

        @Override
        protected WebDriver newInstance(final Class<? extends WebDriver> webDriverClass,
                final ConfigurationProperties configuration, final Object... args)
                throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
            URL url = null;
            if (configuration != null) {
                final String remoteUrl = configuration.getRemoteUrl();

                if (remoteUrl != null) {
                    try {
                        url = new URL(remoteUrl);
                    } catch (final MalformedURLException e) {
                        throw new ConfigurationException("remoteUrl configuration property is not a valid URL.", e);
                    }
                }
            }

            return newRemoteWebDriver(url, args.length > 0 ? args[0] : new DesiredCapabilities());
        }

        /**
         * Creates a new remote WebDriver instance
         *
         * @param args WebDriver constructor arguments
         * @return new remote WebDriver instance
         * @throws NoSuchMethodException     if a matching method is not found.
         * @throws IllegalAccessException    if this {@code Constructor} object
         *                                   is enforcing Java language access control and the underlying
         *                                   constructor is inaccessible.
         * @throws InstantiationException    if the class that declares the
         *                                   underlying constructor represents an abstract class.
         * @throws InvocationTargetException if the underlying constructor
         *                                   throws an exception.
         */
        protected WebDriver newRemoteWebDriver(final Object... args)
                throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
            final WebDriver webDriver = ReflectionUtils.getConstructor(webDriverClass, URL.class, Capabilities.class)
                    .newInstance(args);
            return new Augmenter().augment(webDriver);
        }
    }

    /**
     * HtmlUnit WebDriver factory.
     */
    @DefaultFactory
    public static class HtmlUnitWebDriverFactory extends ReflectiveWebDriverFactory {
        /**
         * Creates a new htmlUnit WebDriver factory.
         */
        public HtmlUnitWebDriverFactory() {
            super("htmlunit", "org.openqa.selenium.htmlunit.HtmlUnitDriver");
        }

        @Override
        protected DesiredCapabilities newDefaultCapabilities() {
            final DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setJavascriptEnabled(true);
            return desiredCapabilities;
        }
    }

}
