package org.fluentlenium.adapter.testng.integration.localtest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.fluentlenium.adapter.testng.FluentTestNg;
import org.testng.annotations.BeforeSuite;

import static org.fluentlenium.utils.UrlUtils.getAbsoluteUrlFromFile;

public class IntegrationFluentTestNg extends FluentTestNg {

    public static final String DEFAULT_URL;
    public static final String PAGE_2_URL;

    static {
        DEFAULT_URL = getAbsoluteUrlFromFile("index.html");
        PAGE_2_URL = getAbsoluteUrlFromFile("page2.html");
    }

    @BeforeSuite
    public static void setUpChrome() {
        WebDriverManager.chromedriver().setup();
    }
}
