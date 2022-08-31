package org.fluentlenium.configuration;

import io.appium.java_client.gecko.options.GeckoOptions;
import io.appium.java_client.safari.options.SafariOptions;
import org.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.opera.OperaOptions;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link WebDriverFactory} that create {@link WebDriver} instances using reflection.
 */
public class ReflectiveWebDriverFactory implements WebDriverFactory, ReflectiveFactory, FactoryNames {
    protected String name;
    protected Object[] args;
    protected String webDriverClassName;
    protected Class<? extends WebDriver> webDriverClass;
    protected Class<? extends Capabilities> capabilitiesClass;
    protected Capabilities capabilities;
    protected boolean available;

    /**
     * Creates a new reflective web driver factory.
     *
     * @param name               factory name
     * @param webDriverClassName web driver class name
     * @param capabilities       browser capabilities
     */
    public ReflectiveWebDriverFactory(String name, String webDriverClassName, Capabilities capabilities) {
        this.name = name;
        this.webDriverClassName = webDriverClassName;
        this.args = null;
        this.capabilitiesClass = capabilities.getClass();
        this.capabilities = capabilities;

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
     * @param capabilities   browser capabilities
     * @param args           web driver class constructor arguments
     */
    public ReflectiveWebDriverFactory(String name, Class<? extends WebDriver> webDriverClass, Capabilities capabilities, Object... args) {
        this.name = name;
        this.webDriverClass = webDriverClass;
        this.args = args;
        this.capabilitiesClass = capabilities.getClass();
        this.capabilities = capabilities;

        webDriverClassName = webDriverClass.getName();
        available = WebDriver.class.isAssignableFrom(this.webDriverClass);
    }

    /**
     * Creates a new reflective web driver factory.
     *
     * @param name               factory name
     * @param webDriverClassName web driver class name
     * @param capabilities       browser capabilities
     * @param args               web driver class constructor arguments
     */
    public ReflectiveWebDriverFactory(String name, String webDriverClassName, Capabilities capabilities, Object... args) {
        this.name = name;
        this.args = args;
        this.capabilitiesClass = capabilities.getClass();
        this.capabilities = capabilities;

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
    @Deprecated
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
    @Deprecated
    public ReflectiveWebDriverFactory(String name, Class<? extends WebDriver> webDriverClass, Class<? extends Capabilities> capabilitiesClass, Object... args) {
        this.name = name;
        this.webDriverClass = webDriverClass;
        this.args = args;
        webDriverClassName = webDriverClass.getName();
        this.capabilitiesClass = capabilitiesClass;
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

            if (capabilities != null) {
                capabilitiesClass = capabilities.getClass();
            }

            if (capabilities != null && !capabilities.asMap().isEmpty()) {
                try {
                    Capabilities browserCapabilities;

                    if (capabilitiesClass.equals(ChromeOptions.class)) {
                        browserCapabilities = new GenericBrowserCapabilities<ChromeOptions>().getBrowserOptions(ChromeOptions.class, capabilities);
                    } else if (capabilitiesClass.equals(EdgeOptions.class)) {
                        browserCapabilities = new GenericBrowserCapabilities<EdgeOptions>().getBrowserOptions(EdgeOptions.class, capabilities);
                    } else if (capabilitiesClass.equals(SafariOptions.class)) {
                        browserCapabilities = new GenericBrowserCapabilities<SafariOptions>().getBrowserOptions(SafariOptions.class, capabilities);
                    } else if (capabilitiesClass.equals(GeckoOptions.class)) {
                        browserCapabilities = new GenericBrowserCapabilities<GeckoOptions>().getBrowserOptions(GeckoOptions.class, capabilities);
                    } else if (capabilitiesClass.equals(OperaOptions.class)) {
                        browserCapabilities = new GenericBrowserCapabilities<OperaOptions>().getBrowserOptions(OperaOptions.class, capabilities);
                    } else {
                        browserCapabilities = capabilities;
                    }
                    return newInstance(webDriverClass, configuration, browserCapabilities);
                } catch (NoSuchMethodException e) { // NOPMD EmptyCatchBlock
                    // Ignore capabilities.
                }
            } else {
                return newInstance(webDriverClass, configuration, args);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new ConfigurationException("Can't create new WebDriver instance", e);
        }
        return null;
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
