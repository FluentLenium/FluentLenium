package org.fluentlenium.example.spring.config;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

public enum BrowserType {

    CHROME {
        @Override
        public WebDriver getWebDriver() {
            return new ChromeDriver();
        }

        @Override
        protected MutableCapabilities getBrowserCapabilities() {
            return DesiredCapabilities.chrome();
        }
    },
    SAFARI {
        @Override
        public WebDriver getWebDriver() {
            return new SafariDriver();
        }

        @Override
        protected MutableCapabilities getBrowserCapabilities() {
            return DesiredCapabilities.safari();
        }
    },
    FIREFOX {
        @Override
        public WebDriver getWebDriver() {
            return new FirefoxDriver();
        }

        @Override
        protected MutableCapabilities getBrowserCapabilities() {
            return DesiredCapabilities.firefox();
        }
    };

    protected abstract WebDriver getWebDriver();

    protected abstract MutableCapabilities getBrowserCapabilities();

    public WebDriver getRemoteWebDriver(String hubLocation) {
        try {
            return new Augmenter().augment(new RemoteWebDriver(new URL(hubLocation), getBrowserCapabilities()));
        } catch (MalformedURLException e) {
            throw new ConfigException("Invalid hub location: " + hubLocation, e);
        }
    }

}
