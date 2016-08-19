package org.fluentlenium.configuration;

import lombok.experimental.Delegate;
import org.atteo.classindex.ClassIndex;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * A registry of {@link WebDriverFactory}.
 * <p>
 * Supported drivers are "firefox", "chrome", "ie", "htmlunit", or any class name implementing {@link WebDriver}.
 */
public enum WebDrivers {
    INSTANCE;

    @Delegate
    private final Impl impl = new Impl();

    static class Impl {
        Impl() {
            Iterable<Class<? extends WebDriverFactory>> factoryClasses = ClassIndex.getSubclasses(WebDriverFactory.class);
            for (Class<? extends WebDriverFactory> factoryClass : factoryClasses) {
                if (factoryClass == ReflectiveWebDriverFactory.class) continue;
                if (Modifier.isAbstract(factoryClass.getModifiers())) continue;
                if (!Modifier.isPublic(factoryClass.getModifiers())) continue;
                WebDriverFactory factory;
                try {
                    factory = factoryClass.getConstructor().newInstance();
                } catch (NoSuchMethodException e) {
                    throw new ConfigurationException(factoryClass + " should have a public default constructor.", e);
                } catch (Exception e) {
                    throw new ConfigurationException(factoryClass + " can't be instantiated.", e);
                }
                register(factory);
            }
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
                ReflectiveWebDriverFactory reflectiveFactory = new ReflectiveWebDriverFactory(name, name);
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
         * Register a new {@link WebDriver} factory.
         *
         * It will also register the factory under names returned by {@link AlternativeNames#getAlternativeNames()} if
         * it implements {@link AlternativeNames}.
         *
         * @param factory {@link WebDriver} factory to register
         */
        public synchronized void register(WebDriverFactory factory) {
            if (factories.containsKey(factory.getName())) {
                throw new ConfigurationException("A factory is already registered with this name: " +
                        factory.getName() + " (" + factories.get(factory.getName()) + ")");
            }
            factories.put(factory.getName(), factory);
            if (factory instanceof AlternativeNames) {
                for (String alternativeName : ((AlternativeNames) factory).getAlternativeNames()) {
                    if (factories.containsKey(alternativeName)) {
                        throw new ConfigurationException("A factory is already registered with this name: " +
                                alternativeName + " (" + factories.get(alternativeName) + ")");
                    }
                    factories.put(alternativeName, factory);
                }
            }

        }
    }


}
