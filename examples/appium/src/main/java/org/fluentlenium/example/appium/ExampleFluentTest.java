package org.fluentlenium.example.appium;

import io.appium.java_client.AppiumDriver;
import org.fluentlenium.adapter.junit.FluentTest;
import org.fluentlenium.example.appium.config.SeleniumBrowserConfigProperties;
import org.fluentlenium.example.appium.config.Config;
import org.fluentlenium.example.appium.config.ConfigException;
import org.fluentlenium.example.appium.device.Device;
import org.junit.runner.RunWith;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
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

    @Override
    public WebDriver newWebDriver() {
        log.info("Running test on Appium server {} using {}", getAppiumServerUrl(), getDevice());
        return runTestOnAppiumServer();
    }

    private WebDriver runTestOnAppiumServer() {
        try {
            return new AppiumDriver(
                    new URL(getAppiumServerUrl()), getCapabilities());
        } catch (MalformedURLException e) {
            throw new ConfigException("Invalid hub location: " + getAppiumServerUrl(), e);
        }
    }

    private String getAppiumServerUrl() {
        return config.getAppiumServerUrl();
    }

    @Override
    public Capabilities getCapabilities() {
        return getDevice().getCapabilities();
    }

    private Device getDevice() {
        return Device.getBrowser(config.getDeviceName());
    }

}
