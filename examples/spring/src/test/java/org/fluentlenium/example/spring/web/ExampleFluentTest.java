package org.fluentlenium.example.spring.web;

import io.appium.java_client.AppiumDriver;
import org.fluentlenium.adapter.testng.FluentTestNgSpringContextTests;
import org.fluentlenium.example.spring.config.Config;
import org.fluentlenium.example.spring.config.ConfigException;
import org.fluentlenium.example.spring.config.SeleniumBrowserConfigProperties;
import org.fluentlenium.example.spring.config.browser.IBrowser;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.net.MalformedURLException;
import java.net.URL;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = Config.class)
public class ExampleFluentTest extends FluentTestNgSpringContextTests {

    private static final Logger log = LoggerFactory.getLogger(ExampleFluentTest.class);

    @Autowired
    private SeleniumBrowserConfigProperties config;

    @Override
    public WebDriver newWebDriver() {
        if (config.useHub()) {
            // Don't log your Grid url because you may expose your SauceLabs/BrowserStack API key :)
            log.info("Running test on Grid using {}", getBrowser());
            return runRemoteWebDriver();
        } else if (config.isMobileSimulator()) {
            log.info("Running test on Appium server {} using {}", getAppiumServerUrl(), getBrowser());
            return runTestOnAppiumServer();
        } else {
            log.info("Running test locally using {}", getBrowser());
            return super.newWebDriver();
        }
    }

    private WebDriver runTestOnAppiumServer() {
        try {
            return new AppiumDriver<>(
                    new URL(getAppiumServerUrl()), getBrowser().getCapabilities());
        } catch (MalformedURLException e) {
            throw new ConfigException("Invalid hub location: " + getAppiumServerUrl(), e);
        }
    }

    private WebDriver runRemoteWebDriver() {
        try {
            return new Augmenter().augment(
                    new RemoteWebDriver(new URL(getRemoteUrl()), getBrowser().getCapabilities()));
        } catch (MalformedURLException e) {
            throw new ConfigException("Invalid hub location: " + getRemoteUrl(), e);
        }
    }

    @Override
    public String getWebDriver() {
        return config.getBrowserName();
    }

    private String getAppiumServerUrl() {
        return config.getAppiumServerUrl();
    }

    @Override
    public String getRemoteUrl() {
        return config.getGridUrl();
    }

    @Override
    public Capabilities getCapabilities() {
        return getBrowser().getCapabilities();
    }

    @Override
    public String getBaseUrl() {
        return config.getPageUrl();
    }

    private IBrowser getBrowser() {
        return IBrowser.getBrowser(config.getBrowserName());
    }

}
