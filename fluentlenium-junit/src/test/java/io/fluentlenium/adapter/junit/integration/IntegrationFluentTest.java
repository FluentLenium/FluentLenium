package io.fluentlenium.adapter.junit.integration;

import static io.fluentlenium.utils.UrlUtils.getAbsoluteUrlFromFile;

import io.fluentlenium.utils.UrlUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.fluentlenium.adapter.junit.FluentTest;
import org.junit.BeforeClass;

public class IntegrationFluentTest extends FluentTest {

    protected static final String DEFAULT_URL = UrlUtils.getAbsoluteUrlFromFile("index.html");

    @BeforeClass
    public static void setUpChrome() {
        WebDriverManager.chromedriver().setup();
    }

}
