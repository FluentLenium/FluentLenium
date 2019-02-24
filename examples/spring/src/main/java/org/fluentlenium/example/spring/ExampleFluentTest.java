package org.fluentlenium.example.spring;

import org.fluentlenium.adapter.junit.FluentTest;
import org.fluentlenium.example.spring.config.BrowserConfig;
import org.fluentlenium.example.spring.config.Config;
import org.fluentlenium.example.spring.config.SeleniumBrowserConfigProperties;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.os.ExecutableFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = Config.class)
public class ExampleFluentTest extends FluentTest {

    private static final Logger logger = LoggerFactory.getLogger(ExampleFluentTest.class);

    @Autowired
    private SeleniumBrowserConfigProperties config;

    @Override
    public WebDriver newWebDriver() {
        setupDriver();
        logger.info("Using {} browser as specified in config.properties",
                getBrowserConfig().getBrowserType());
        BrowserConfig browserConfig = getBrowserConfig();
        return browserConfig.resolveDriver(browserConfig);
    }

    @Override
    public String getBaseUrl() {
        return config.getPageUrl();
    }

    private BrowserConfig getBrowserConfig() {
        return config.getBrowserConfig();
    }

    private void setupDriver() {
        String propertyName = getBrowserConfig().getDriverSystemPropertyName();
        String driverExecutablePath = config.getDriverExecutablePath();
        if (systemPropertyNotSet(propertyName) && executableNotPresentInPath()) {
            setSystemProperty(propertyName, driverExecutablePath);
        }
    }

    private void setSystemProperty(String propertyName, String driverExecutablePath) {
        System.setProperty(propertyName, driverExecutablePath);
    }

    private boolean systemPropertyNotSet(String propertyName) {
        return System.getProperty(propertyName) == null;
    }

    private boolean executableNotPresentInPath() {
        String driverExecutableName = getBrowserConfig().getDriverExecutableName();
        return new ExecutableFinder().find(driverExecutableName) == null;
    }
}
