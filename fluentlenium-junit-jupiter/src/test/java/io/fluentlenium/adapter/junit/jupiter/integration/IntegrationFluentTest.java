package io.fluentlenium.adapter.junit.jupiter.integration;

import static io.fluentlenium.utils.UrlUtils.getAbsoluteUrlFromFile;

import io.fluentlenium.utils.UrlUtils;import io.github.bonigarcia.wdm.WebDriverManager;
import io.fluentlenium.adapter.junit.jupiter.FluentTest;
import org.junit.jupiter.api.BeforeAll;

public class IntegrationFluentTest extends FluentTest {

    protected static final String DEFAULT_URL = UrlUtils.getAbsoluteUrlFromFile("index.html");

    @BeforeAll
    public static void setUpChrome() {
        WebDriverManager.chromedriver().setup();
    }

}
