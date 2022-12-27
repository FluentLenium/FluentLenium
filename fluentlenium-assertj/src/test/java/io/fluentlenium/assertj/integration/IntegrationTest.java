package io.fluentlenium.assertj.integration;

import io.fluentlenium.utils.UrlUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.fluentlenium.adapter.testng.FluentTestNg;
import org.testng.annotations.BeforeSuite;

import static io.fluentlenium.utils.UrlUtils.getAbsoluteUrlFromFile;

public abstract class IntegrationTest extends FluentTestNg {

    protected static final String DEFAULT_URL = UrlUtils.getAbsoluteUrlFromFile("index.html");

    @BeforeSuite
    public void setUpChrome() {
        WebDriverManager.chromedriver().setup();
    }

}
