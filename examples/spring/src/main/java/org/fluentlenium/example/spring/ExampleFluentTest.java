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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = Config.class)
public class ExampleFluentTest extends FluentTest {

    @Autowired
    private SeleniumBrowserConfigProperties config;

    @Before
    public void setUp() {
        if (!config.useHub()) {
            setupDriver();
        }
    }

    @Override
    public WebDriver newWebDriver() {
        if (config.useHub()) {
            if (config.isMobileSimulator()) {
                try {
                    return new AppiumDriver(new URL(getRemoteUrl()), getBrowser().getBrowserCapabilities());
                } catch (MalformedURLException e) {
                    throw new ConfigException(e.getMessage());
                }
            } else {
                return runRemoteWebdriver();
            }
        } else {
            return super.newWebDriver();
        }
    }

    private WebDriver runRemoteWebdriver() {
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

    @Override
    public String getRemoteUrl() {
        return Optional.ofNullable(System.getProperty("gridUrl"))
                .orElse(config.getGridUrl());
    }

    @Override
    public Capabilities getCapabilities() {
        return getBrowser().getBrowserCapabilities();
    }

    @Override
    public String getBaseUrl() {
        return config.getPageUrl();
    }

    private void setupDriver() {
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
