package io.fluentlenium.adapter.junit.integration;

import io.fluentlenium.adapter.junit.FluentTest;
import io.fluentlenium.utils.UrlUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.BeforeClass;

public class IntegrationFluentTest extends FluentTest {

    protected static final String DEFAULT_URL = UrlUtils.getAbsoluteUrlFromFile("index.html");

    @BeforeClass
    public static void setUpChrome() {
        WebDriverManager.chromedriver().setup();
    }

}
