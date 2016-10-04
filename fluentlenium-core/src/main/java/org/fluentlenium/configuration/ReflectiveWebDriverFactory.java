package org.fluentlenium.configuration;

import org.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

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
    protected boolean available;

    public ReflectiveWebDriverFactory(final String name, final String webDriverClassName, final Object... args) {
        this.name = name;
        this.webDriverClassName = webDriverClassName;
        this.args = args;
        try {
            this.webDriverClass = (Class<? extends WebDriver>) Class.forName(webDriverClassName);
            this.available = WebDriver.class.isAssignableFrom(this.webDriverClass);
        } catch (final ClassNotFoundException e) {
            this.available = false;
        }
    }

    public ReflectiveWebDriverFactory(final String name, final Class<? extends WebDriver> webDriverClass, final Object... args) {
        this.name = name;
        this.webDriverClass = webDriverClass;
        this.args = args;
        this.webDriverClassName = webDriverClass.getName();
        this.available = WebDriver.class.isAssignableFrom(this.webDriverClass);
    }

    public Class<? extends WebDriver> getWebDriverClass() {
        return webDriverClass;
    }

    public boolean isAvailable() {
        return available;
    }

    protected DesiredCapabilities newDefaultCapabilities() {
        return null;
    }

    @Override
    public WebDriver newWebDriver(Capabilities capabilities, final ConfigurationProperties configuration) {
        if (!available) {
            throw new ConfigurationException("WebDriver " + webDriverClassName + " is not available.");
        }

        try {
            final DesiredCapabilities defaultCapabilities = newDefaultCapabilities();
            if (defaultCapabilities != null) {
                defaultCapabilities.merge(capabilities);
                capabilities = defaultCapabilities;
            }

            if (capabilities != null && !capabilities.asMap().isEmpty()) {
                final ArrayList<Object> argsList = new ArrayList<>(Arrays.asList(args));
                argsList.add(0, capabilities);
                try {
                    return newInstance(webDriverClass, configuration, argsList.toArray());
                } catch (final NoSuchMethodException e) { // NOPMD EmptyCatchBlock
                    // Ignore capabilities.
                }
            }
            return newInstance(webDriverClass, configuration, args);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new ConfigurationException("Can't create new WebDriver instance", e);
        }
    }

    protected WebDriver newInstance(final Class<? extends WebDriver> webDriverClass, final ConfigurationProperties configuration,
            final Object... args)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return ReflectionUtils.newInstance(webDriverClass, args);
    }

    @Override
    public String[] getNames() {
        final List<String> names = new ArrayList<>(Arrays.asList(name));
        if (webDriverClass != null) {
            names.add(webDriverClass.getName());
            names.add(webDriverClass.getSimpleName());
        }
        return names.toArray(new String[names.size()]);
    }
}
