package io.fluentlenium.assertj.integration;

import io.fluentlenium.adapter.testng.FluentTestNg;
import io.fluentlenium.utils.UrlUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.BeforeSuite;

public abstract class IntegrationTest extends FluentTestNg {

    protected static final String DEFAULT_URL = UrlUtils.getAbsoluteUrlFromFile("index.html");

    @BeforeSuite
    public void setUpChrome() {
        WebDriverManager.chromedriver().setup();
    }

}
