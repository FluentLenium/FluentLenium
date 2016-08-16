package org.fluentlenium.configuration;

import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

/**
 * A registry of {@link WebDriverFactory}.
 */
public abstract class WebDrivers {
    private static Map<String, WebDriverFactory> factories = new HashMap<>();

    static {
        register("firefox", new ReflectiveWebDriverFactory("org.openqa.selenium.firefox.FirefoxDriver"));
    }

    private WebDrivers() {
    }

    public synchronized static WebDriverFactory get(String name) {
        WebDriverFactory factory = factories.get(name);
        if (factory == null) {
            throw new ConfigurationException("No factory is available with this name: " + name + " (" + factories.get(name) + ")");
        }
        return factory;
    }

    public synchronized static WebDriver newWebDriver(String name) {
        return get(name).newWebDriver();
    }

    public synchronized static void register(String name, WebDriverFactory factory) {
        if (factories.containsKey(name)) {
            throw new ConfigurationException("A factory is already registered with this name: " + name + " (" + factories.get(name) + ")");
        }
        factories.put(name, factory);
        factories.put(factory.getClass().getSimpleName(), factory);
        factories.put(factory.getClass().getName(), factory);
    }
}
