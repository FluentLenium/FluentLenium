package org.fluentlenium;

import org.fluentlenium.adapter.testng.FluentTestNgSpringTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.test.context.ContextConfiguration;

import static org.fluentlenium.utils.UrlUtils.getAbsoluteUrlFromFile;

@ContextConfiguration(locations = {"classpath:spring-test-config.xml"})
public class IntegrationFluentTestNg extends FluentTestNgSpringTest {

    public static final String DEFAULT_URL;
    public static final String PAGE_2_URL;

    static {
        DEFAULT_URL = getAbsoluteUrlFromFile("index.html");
        PAGE_2_URL = getAbsoluteUrlFromFile("page2.html");
    }

    @Override
    public WebDriver newWebDriver() {
        return new HtmlUnitDriver(true);
    }
}
