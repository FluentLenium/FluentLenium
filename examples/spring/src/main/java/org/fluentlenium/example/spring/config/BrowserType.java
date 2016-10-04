package org.fluentlenium.example.spring.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static org.openqa.selenium.remote.DesiredCapabilities.chrome;

public enum BrowserType {

    CHROME {
        @Override
        public WebDriver getWebDriver() {
            return new ChromeDriver();
        }

        @Override
        protected DesiredCapabilities getBrowserCapabilities() {
            return chrome();
        }
    };

    protected abstract WebDriver getWebDriver();

    protected abstract DesiredCapabilities getBrowserCapabilities();

    public WebDriver getRemoteWebDriver(final String hubLocation) {
        try {
            return new Augmenter().augment(new RemoteWebDriver(new URL(hubLocation), getBrowserCapabilities()));
        } catch (final MalformedURLException e) {
            throw new ConfigException("Invalid hub location: " + hubLocation, e);
        }
    }

}
