package org.fluentlenium.configuration;


import org.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.InvocationTargetException;

/**
 * A simple {@link WebDriverFactory} that create {@link WebDriver} instances using reflection.
 */
public class ReflectiveWebDriverFactory implements WebDriverFactory, AlternativeNames {
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

    @Override
    public WebDriver newWebDriver() {
        if (!available) {
            throw new ConfigurationException("WebDriver " + webDriverClassName + " is not available.");
        }

        try {
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
}
