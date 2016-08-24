package org.fluentlenium.configuration;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import lombok.experimental.Delegate;
import org.atteo.classindex.ClassIndex;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
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

        private Map<String, WebDriverFactory> factories = new LinkedHashMap<>();

        public synchronized WebDriverFactory getDefault() {
            List<WebDriverFactory> factories = new ArrayList<>(this.factories.values());
            Collections.sort(factories, new Comparator<WebDriverFactory>() {
                @Override
                public int compare(WebDriverFactory o1, WebDriverFactory o2) {
                    return -Integer.compare(o1.getPriority(), o2.getPriority());
                }
            });
            List<WebDriverFactory> filteredFactories = new ArrayList<>();
            for (WebDriverFactory factory : factories) {
                if (factory instanceof ReflectiveWebDriverFactory) {
                    if (((ReflectiveWebDriverFactory) factory).isAvailable()) {
                        filteredFactories.add(factory);
                    }
                } else {
                    filteredFactories.add(factory);
                }
            }
            if (filteredFactories.size() == 0) {
                throw new ConfigurationException("No WebDriverFactory is available. You need add least one supported " +
                        "WebDriver in your classpath.");
            }
            return filteredFactories.get(0);
        }

        /**
         * Get the {@link WebDriver} factory registered under the given name.
         *
         * @param name name of the webdriver
         * @return factory of {@link WebDriver}
         */
        public synchronized WebDriverFactory get(String name) {
            if (name == null) return getDefault();
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
         * @param capabilities Desired capabilities for the WebDriver
         * @return a new WebDriver instance
         */
        public synchronized WebDriver newWebDriver(String name, Capabilities capabilities) {
            return get(name).newWebDriver(capabilities);
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
