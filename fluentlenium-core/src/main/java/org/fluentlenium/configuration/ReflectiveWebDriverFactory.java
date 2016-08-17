package org.fluentlenium.configuration;


import org.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.InvocationTargetException;

public class ReflectiveWebDriverFactory implements WebDriverFactory {
    private Object[] args;
    private String webDriverClassName;
    private Class<? extends WebDriver> webDriverClass;
    private boolean available;

    public ReflectiveWebDriverFactory(String webDriverClassName, Object... args) {
        this.webDriverClassName = webDriverClassName;
        this.args = args;
        try {
            this.webDriverClass = (Class<? extends WebDriver>) Class.forName(webDriverClassName);
            this.available = WebDriver.class.isAssignableFrom(this.webDriverClass);
        } catch (ClassNotFoundException e) {
            this.available = false;
        }
    }

    public ReflectiveWebDriverFactory(Class<? extends WebDriver> webDriverClass, Object... args) {
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
    public String[] getNames() {
        if (webDriverClass != null) {
            return new String[]{webDriverClass.getName(), webDriverClass.getSimpleName()};
        }
        return new String[0];
    }
}
