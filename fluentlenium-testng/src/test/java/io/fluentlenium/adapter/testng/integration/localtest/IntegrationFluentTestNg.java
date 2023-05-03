package io.fluentlenium.adapter.testng.integration.localtest;

import io.fluentlenium.adapter.testng.FluentTestNg;
import io.fluentlenium.utils.UrlUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.BeforeSuite;

public class IntegrationFluentTestNg extends FluentTestNg {

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
