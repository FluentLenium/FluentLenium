package org.fluentlenium.configuration;

import lombok.experimental.Delegate;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

/**
 * A registry of {@link WebDriverFactory}.
 * <p>
 * Supported drivers are "firefox", "htmlunit".
 */
public enum WebDrivers {
    INSTANCE;

    @Delegate
    private final Impl impl = new Impl();

    static class Impl {
        Impl() {
            register("firefox", new ReflectiveWebDriverFactory("org.openqa.selenium.firefox.FirefoxDriver"));
            register("chrome", new ReflectiveWebDriverFactory("org.openqa.selenium.chrome.ChromeDriver"));
            register("ie", new ReflectiveWebDriverFactory("org.openqa.selenium.ie.InternetExplorerDriver"));
            register("htmlunit", new ReflectiveWebDriverFactory("org.openqa.selenium.htmlunit.HtmlUnitDriver"));
        }

        private Map<String, WebDriverFactory> factories = new HashMap<>();

        /**
         * Get the {@link WebDriver} factory registered under the given name.
         *
         * @param name name of the webdriver
         * @return factory of {@link WebDriver}
         */
        public synchronized WebDriverFactory get(String name) {
            WebDriverFactory factory = factories.get(name);
            if (factory == null) {
                ReflectiveWebDriverFactory reflectiveFactory = new ReflectiveWebDriverFactory(name);
                if (reflectiveFactory.isAvailable()) {
                    factories.put(name, reflectiveFactory);
                    factory = reflectiveFactory;
                } else {
                    throw new ConfigurationException("No factory is available with this name: " + name);
                }
            }
            return factory;
        }

        /**
         * Creates a new {@link WebDriver} instance from factory of the given name
         *
         * @param name name of the factory used to create new WebDriver instance
         * @return a new WebDriver instance
         */
        public synchronized WebDriver newWebDriver(String name) {
            return get(name).newWebDriver();
        }

        /**
         * Register a new {@link WebDriver} factory with the given name.
         *
         * It will also register the factory under names returned by {@link WebDriverFactory#getNames()}
         *
         * @param name name to register
         * @param factory {@link WebDriver} factory to register
         */
        public synchronized void register(String name, WebDriverFactory factory) {
            if (factories.containsKey(name)) {
                throw new ConfigurationException("A factory is already registered with this name: " + name + " (" + factories.get(name) + ")");
            }
            factories.put(name, factory);
            for (String alternativeName : factory.getNames()) {
                factories.put(alternativeName, factory);
            }
        }
    }


}
