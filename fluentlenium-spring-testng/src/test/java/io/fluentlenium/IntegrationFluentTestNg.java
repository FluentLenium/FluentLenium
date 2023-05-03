package io.fluentlenium;

import io.fluentlenium.adapter.testng.FluentTestNgSpringTest;
import io.fluentlenium.utils.UrlUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeSuite;

@ContextConfiguration(locations = {"classpath:spring-test-config.xml"})
public class IntegrationFluentTestNg extends FluentTestNgSpringTest {

    public static final String DEFAULT_URL;
    public static final String PAGE_2_URL;

    static {
        DEFAULT_URL = UrlUtils.getAbsoluteUrlFromFile("index.html");
        PAGE_2_URL = UrlUtils.getAbsoluteUrlFromFile("page2.html");
    }

    @BeforeSuite
    public void setUpChrome() {
        WebDriverManager.chromedriver().setup();
    }

}
