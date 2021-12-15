package org.fluentlenium.assertj.integration;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.fluentlenium.adapter.testng.FluentTestNg;
import org.testng.annotations.BeforeSuite;

import static org.fluentlenium.utils.UrlUtils.getAbsoluteUrlFromFile;

public abstract class IntegrationTest extends FluentTestNg {

    protected static final String DEFAULT_URL = getAbsoluteUrlFromFile("index.html");

    @BeforeSuite
    public void setUpChrome() {
        WebDriverManager.chromedriver().setup();
    }

}
