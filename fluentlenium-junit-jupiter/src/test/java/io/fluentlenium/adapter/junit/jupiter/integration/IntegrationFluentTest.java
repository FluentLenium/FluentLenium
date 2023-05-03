package io.fluentlenium.adapter.junit.jupiter.integration;

import io.fluentlenium.adapter.junit.jupiter.FluentTest;
import io.fluentlenium.utils.UrlUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;

public class IntegrationFluentTest extends FluentTest {

    protected static final String DEFAULT_URL = UrlUtils.getAbsoluteUrlFromFile("index.html");

    @BeforeAll
    public static void setUpChrome() {
        WebDriverManager.chromedriver().setup();
    }

}
