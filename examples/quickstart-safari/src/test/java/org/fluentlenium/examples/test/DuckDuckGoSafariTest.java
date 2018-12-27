package org.fluentlenium.examples.test;

import org.fluentlenium.adapter.junit.FluentTest;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.examples.pages.DuckDuckMainPage;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;

public class DuckDuckGoSafariTest extends FluentTest {

    private static final String PATH_TO_SAFARI_DRIVER = "/home/ubuntu/safaridriver";
    private static final String SAFARI_DRIVER_PROPERTY = "webdriver.safari.driver";

    @Page
    private DuckDuckMainPage duckDuckMainPage;

    @BeforeClass
    public static void setup() {
        if (System.getProperty(SAFARI_DRIVER_PROPERTY) == null) {
            System.setProperty(SAFARI_DRIVER_PROPERTY, PATH_TO_SAFARI_DRIVER);
        }
    }

    @Override
    public WebDriver newWebDriver() {
        return new SafariDriver();
    }

    @Test
    public void titleOfDuckDuckGoShouldContainSearchQueryName() {
        String searchPhrase = "searchPhrase";

        goTo(duckDuckMainPage)
                .typeSearchPhraseIn(searchPhrase)
                .submitSearchForm()
                .assertIsPhrasePresentInTheResults(searchPhrase);
    }

}