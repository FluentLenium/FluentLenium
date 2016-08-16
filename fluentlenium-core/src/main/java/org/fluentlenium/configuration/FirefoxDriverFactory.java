package org.fluentlenium.configuration;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class FirefoxDriverFactory implements WebDriverFactory {
    @Override
    public WebDriver newWebDriver() {
        return new FirefoxDriver();
    }
}
