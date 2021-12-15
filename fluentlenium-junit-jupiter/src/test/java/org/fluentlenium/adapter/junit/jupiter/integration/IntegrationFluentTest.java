package org.fluentlenium.adapter.junit.jupiter.integration;

import static org.fluentlenium.utils.UrlUtils.getAbsoluteUrlFromFile;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.fluentlenium.adapter.junit.jupiter.FluentTest;
import org.junit.jupiter.api.BeforeAll;

public class IntegrationFluentTest extends FluentTest {

    protected static final String DEFAULT_URL = getAbsoluteUrlFromFile("index.html");

    @BeforeAll
    public static void setUpChrome() {
        WebDriverManager.chromedriver().setup();
    }

}
