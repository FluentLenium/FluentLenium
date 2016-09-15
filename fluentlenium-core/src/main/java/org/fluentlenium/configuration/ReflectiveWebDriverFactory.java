package org.fluentlenium.configuration;


import org.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link WebDriverFactory} that create {@link WebDriver} instances using reflection.
 */
public class ReflectiveWebDriverFactory implements WebDriverFactory, ReflectiveFactory, AlternativeNames {
    private String name;
    private Object[] args;
    private String webDriverClassName;
    private Class<? extends WebDriver> webDriverClass;
    private boolean available;

    public ReflectiveWebDriverFactory(String name, String webDriverClassName, Object... args) {
        this.name = name;
        this.webDriverClassName = webDriverClassName;
        this.args = args;
        try {
            this.webDriverClass = (Class<? extends WebDriver>) Class.forName(webDriverClassName);
            this.available = WebDriver.class.isAssignableFrom(this.webDriverClass);
        } catch (ClassNotFoundException e) {
            this.available = false;
        }
    }

    public ReflectiveWebDriverFactory(String name, Class<? extends WebDriver> webDriverClass, Object... args) {
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
    public WebDriver newWebDriver(Capabilities capabilities) {
        if (!available) {
            throw new ConfigurationException("WebDriver " + webDriverClassName + " is not available.");
        }

        try {
            DesiredCapabilities defaultCapabilities = newDefaultCapabilities();
            if (defaultCapabilities != null) {
                defaultCapabilities.merge(capabilities);
                capabilities = defaultCapabilities;
            }

            if (capabilities != null && !capabilities.asMap().isEmpty()) {
                ArrayList<Object> argsList = new ArrayList<>(Arrays.asList(args));
                argsList.add(0, capabilities);
                try {
                    return ReflectionUtils.newInstance(webDriverClass, argsList.toArray());
                } catch (NoSuchMethodException e) {
                    // Ignore capabilities.
                }
            }
            return ReflectionUtils.newInstance(webDriverClass, args);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new ConfigurationException("Can't create new WebDriver instance", e);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String[] getAlternativeNames() {
        if (webDriverClass != null) {
            return new String[]{webDriverClass.getName(), webDriverClass.getSimpleName()};
        }
        return new String[0];
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
