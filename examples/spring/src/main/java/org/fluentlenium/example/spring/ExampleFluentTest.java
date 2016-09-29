package org.fluentlenium.example.spring;

import org.fluentlenium.adapter.junit.FluentTest;
import org.fluentlenium.example.spring.config.BrowserConfig;
import org.fluentlenium.example.spring.config.Config;
import org.fluentlenium.example.spring.config.SeleniumBrowserConfigProperties;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = Config.class)
public class ExampleFluentTest extends FluentTest {
    @Autowired
    private SeleniumBrowserConfigProperties config;

    @Override
    public WebDriver newWebDriver() {
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
}
