package org.fluentlenium.configuration;

import lombok.experimental.Delegate;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

/**
 * A registry of {@link WebDriverFactory}.
 */
public enum WebDrivers {
    INSTANCE;

    @Delegate
    private final Impl impl = new Impl();

    static class Impl {
        Impl() {
            register("firefox", new ReflectiveWebDriverFactory("org.openqa.selenium.firefox.FirefoxDriver"));
            register("htmlunit", new ReflectiveWebDriverFactory("org.openqa.selenium.htmlunit.HtmlUnitDriver"));
        }

        private Map<String, WebDriverFactory> factories = new HashMap<>();

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

        public synchronized WebDriver newWebDriver(String name) {
            return get(name).newWebDriver();
        }

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
