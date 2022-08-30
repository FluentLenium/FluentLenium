package org.fluentlenium.configuration;

import io.appium.java_client.gecko.options.GeckoOptions;
import io.appium.java_client.safari.options.SafariOptions;
import org.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.opera.OperaOptions;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * A simple {@link WebDriverFactory} that create {@link WebDriver} instances using reflection.
 */
public class ReflectiveWebDriverFactory implements WebDriverFactory, ReflectiveFactory, FactoryNames {
    protected String name;
    protected Object[] args;
    protected String webDriverClassName;
    protected Class<? extends WebDriver> webDriverClass;
    protected Class<? extends Capabilities> capabilitiesClass;
    protected boolean available;

    public ReflectiveWebDriverFactory(String name, String webDriverClassName, Object... args) {
        this.name = name;
        this.webDriverClassName = webDriverClassName;
        this.args = args;

        try {
            webDriverClass = (Class<? extends WebDriver>) Class.forName(webDriverClassName);
            available = WebDriver.class.isAssignableFrom(webDriverClass);
        } catch (ClassNotFoundException e) {
            available = false;
        }
    }

    /**
     * Creates a new reflective web driver factory.
     *
     * @param name               factory name
     * @param webDriverClassName web driver class name
     * @param args               web driver class constructor arguments
     */
    public ReflectiveWebDriverFactory(String name, String webDriverClassName, Class<? extends Capabilities> capabilitiesClass, Object... args) {
        this.name = name;
        this.webDriverClassName = webDriverClassName;
        this.args = args;
        this.capabilitiesClass = capabilitiesClass;

        try {
            webDriverClass = (Class<? extends WebDriver>) Class.forName(webDriverClassName);
            available = WebDriver.class.isAssignableFrom(webDriverClass);
        } catch (ClassNotFoundException e) {
            available = false;
        }
    }

    /**
     * Creates a new reflective web driver factory.
     *
     * @param name           factory name
     * @param webDriverClass web driver class
     * @param args           web driver class constructor arguments
     */
    public ReflectiveWebDriverFactory(String name, Class<? extends WebDriver> webDriverClass, Object... args) {
        this.name = name;
        this.webDriverClass = webDriverClass;
        this.args = args;
        webDriverClassName = webDriverClass.getName();
        available = WebDriver.class.isAssignableFrom(this.webDriverClass);
    }

    /**
     * Get web driver class.
     *
     * @return web driver class
     */
    public Class<? extends WebDriver> getWebDriverClass() {
        return webDriverClass;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    /**
     * Creates new default capabilities.
     *
     * @return default capabilities
     */
    protected Capabilities newDefaultCapabilities() {
        return null;
    }


    @Override
    public WebDriver newWebDriver(Capabilities capabilities, ConfigurationProperties configuration) {
        if (!available) {
            throw new ConfigurationException("WebDriver " + webDriverClassName + " is not available.");
        }

        try {
            Capabilities defaultCapabilities = newDefaultCapabilities();
            if (defaultCapabilities != null) {
                defaultCapabilities.merge(capabilities);
                capabilities = defaultCapabilities;
            }

            if (capabilities != null && !capabilities.asMap().isEmpty()) {
                try {
                    Capabilities browserCapabilities;

                    if (capabilitiesClass == ChromeOptions.class) {
                        browserCapabilities = new GenericBrowserCapabilities<ChromeOptions>().getBrowserOptions(ChromeOptions.class, capabilities);;
                    }  else if (capabilitiesClass == EdgeOptions.class) {
                        browserCapabilities = new GenericBrowserCapabilities<EdgeOptions>().getBrowserOptions(EdgeOptions.class, capabilities);
                    } else if (capabilitiesClass == SafariOptions.class) {
                        browserCapabilities = new GenericBrowserCapabilities<SafariOptions>().getBrowserOptions(SafariOptions.class, capabilities);
                    } else if (capabilitiesClass == GeckoOptions.class) {
                        browserCapabilities = new GenericBrowserCapabilities<GeckoOptions>().getBrowserOptions(GeckoOptions.class, capabilities);
                    } else if (capabilitiesClass == OperaOptions.class) {
                        browserCapabilities = new GenericBrowserCapabilities<OperaOptions>().getBrowserOptions(OperaOptions.class, capabilities);
                    } else {
                        browserCapabilities = capabilities;
                    }
                    return newInstance(webDriverClass, configuration, browserCapabilities);
                } catch (NoSuchMethodException e) { // NOPMD EmptyCatchBlock
                    // Ignore capabilities.
                }
            }

            return newInstance(webDriverClass, configuration, capabilities);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new ConfigurationException("Can't create new WebDriver instance", e);
        }
    }

    /**
     * Creates a new instance of web driver.
     *
     * @param webDriverClass web driver class
     * @param configuration  configuration
     * @param args           web driver class constructor arguments
     * @return new web driver instance
     * @throws NoSuchMethodException     if a matching method is not found.
     * @throws IllegalAccessException    if this {@code Constructor} object
     *                                   is enforcing Java language access control and the underlying
     *                                   constructor is inaccessible.
     * @throws InstantiationException    if the class that declares the
     *                                   underlying constructor represents an abstract class.
     * @throws InvocationTargetException if the underlying constructor
     *                                   throws an exception.
     */
    protected WebDriver newInstance(Class<? extends WebDriver> webDriverClass, ConfigurationProperties configuration,
                                    Object... args)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return ReflectionUtils.newInstance(webDriverClass, args);
    }

    @Override
    public String[] getNames() {
        List<String> names = new ArrayList<>(Arrays.asList(name));
        if (webDriverClass != null) {
            names.add(webDriverClass.getName());
            names.add(webDriverClass.getSimpleName());
        }
        return names.toArray(new String[names.size()]);
    }
}
