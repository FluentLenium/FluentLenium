package org.fluentlenium.configuration;


import org.openqa.selenium.WebDriver;

public class ReflectiveWebDriverFactory implements WebDriverFactory {
    private String webDriverClassName;
    private Class<? extends WebDriver> webDriverClass;
    private boolean available;

    public ReflectiveWebDriverFactory(String webDriverClassName) {
        this.webDriverClassName = webDriverClassName;
        try {
            this.webDriverClass = (Class<? extends WebDriver>) Class.forName(webDriverClassName);
            this.available = WebDriver.class.isAssignableFrom(this.webDriverClass);
        } catch (ClassNotFoundException e) {
            this.available = false;
        }
    }

    @Override
    public WebDriver newWebDriver() {
        if (!available) {
            throw new ConfigurationException("WebDriver " + webDriverClassName + " is not available.");
        }

        try {
            return webDriverClass.newInstance();
        } catch (InstantiationException e) {
            throw new ConfigurationException("Can't create new WebDriver instance", e);
        } catch (IllegalAccessException e) {
            throw new ConfigurationException("Can't create new WebDriver instance", e);
        }
    }
}
