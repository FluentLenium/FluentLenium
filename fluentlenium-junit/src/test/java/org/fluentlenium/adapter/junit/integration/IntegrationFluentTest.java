package org.fluentlenium.adapter.junit.integration;

import static org.fluentlenium.utils.UrlUtils.getAbsoluteUrlFromFile;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.fluentlenium.adapter.junit.FluentTest;
import org.junit.BeforeClass;

public class IntegrationFluentTest extends FluentTest {

    protected static final String DEFAULT_URL = getAbsoluteUrlFromFile("index.html");

    @BeforeClass
    public static void setUpChrome() {
        WebDriverManager.chromedriver().setup();
    }

}
