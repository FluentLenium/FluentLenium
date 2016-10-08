package org.fluentlenium.configuration;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

import java.util.List;

/**
 * WebDrivers registry implementation.
 */
public class WebDriversRegistryImpl extends AbstractFactoryRegistryImpl<WebDriverFactory, ReflectiveWebDriverFactory> {
    /**
     * Creates a new registry.
     */
    public WebDriversRegistryImpl() {
        super(WebDriverFactory.class, ReflectiveWebDriverFactory.class);
    }

    @Override
    protected ReflectiveWebDriverFactory newReflectiveInstance(final String name) {
        return new ReflectiveWebDriverFactory(name, name);
    }

    @Override
    protected WebDriverFactory getDefault(final List<WebDriverFactory> filteredFactories) {
        if (filteredFactories.isEmpty()) {
            throw new ConfigurationException(
                    "No WebDriverFactory is available. You need add least one supported " + "WebDriver in your classpath.");
        }
        return filteredFactories.get(0);
    }

    @Override
    protected void handleNoFactoryAvailable(final String name) {
        throw new ConfigurationException("No factory is available with this name: " + name);
    }

    /**
     * Creates a new {@link WebDriver} instance from factory of the given name
     *
     * @param name          name of the factory used to create new WebDriver instance
     * @param capabilities  Desired capabilities for the WebDriver
     * @param configuration Configuration for the WebDriver
     * @return a new WebDriver instance
     */
    public WebDriver newWebDriver(final String name, final Capabilities capabilities,
            final ConfigurationProperties configuration) {
        synchronized (this) {
            return get(name).newWebDriver(capabilities, configuration);
        }
    }
}
