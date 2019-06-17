package org.fluentlenium.example.spring;

import io.appium.java_client.AppiumDriver;
import org.fluentlenium.adapter.junit.FluentTest;
import org.fluentlenium.example.spring.config.Config;
import org.fluentlenium.example.spring.config.ConfigException;
import org.fluentlenium.example.spring.config.SeleniumBrowserConfigProperties;
import org.fluentlenium.example.spring.config.browser.IBrowser;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.os.ExecutableFinder;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.net.MalformedURLException;
import java.net.URL;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = Config.class)
public class ExampleFluentTest extends FluentTest {

    private static final Logger log = LoggerFactory.getLogger(ExampleFluentTest.class);

    @Autowired
    private SeleniumBrowserConfigProperties config;

    @Before
    public void setUp() {
        if (!config.useHub() && !config.isMobileSimulator()) {
            setupDriverExecutables();
        }
    }

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
            return new AppiumDriver(
                    new URL(getAppiumServerUrl()), getBrowser().getBrowserCapabilities());
        } catch (MalformedURLException e) {
            throw new ConfigException("Invalid hub location: " + getAppiumServerUrl(), e);
        }
    }

    private WebDriver runRemoteWebDriver() {
        try {
            return new Augmenter().augment(
                    new RemoteWebDriver(new URL(getRemoteUrl()), getBrowser().getBrowserCapabilities()));
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
        return getBrowser().getBrowserCapabilities();
    }

    @Override
    public String getBaseUrl() {
        return config.getPageUrl();
    }

    private void setupDriverExecutables() {
        String propertyName = getBrowser().getDriverSystemPropertyName();
        String driverExecutablePath = config.getDriverExecutablePath();
        if (systemPropertyNotSet(propertyName) && executableNotPresentInPath(getBrowser())) {
            System.setProperty(propertyName, driverExecutablePath);
        }
    }

    private IBrowser getBrowser() {
        return IBrowser.getBrowser(config.getBrowserName());
    }

    private boolean systemPropertyNotSet(String propertyName) {
        return System.getProperty(propertyName) == null;
    }

    private boolean executableNotPresentInPath(IBrowser browser) {
        String driverExecutableName = browser.getDriverExecutableName();
        return new ExecutableFinder().find(driverExecutableName) == null;
    }
}
