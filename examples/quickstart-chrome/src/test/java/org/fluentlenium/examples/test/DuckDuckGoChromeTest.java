package org.fluentlenium.examples.test;

import org.fluentlenium.adapter.junit.FluentTest;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.examples.pages.DuckDuckMainPage;
import org.junit.BeforeClass;
import org.junit.Test;

public class DuckDuckGoChromeTest extends FluentTest {

    private static final String PATH_TO_CHROME_DRIVER = "C:\\drivers\\chromedriver.exe";
    private static final String CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";

    @Page
    private DuckDuckMainPage duckDuckMainPage;

    @BeforeClass
    public static void setup() {
        if (System.getProperty(CHROME_DRIVER_PROPERTY) == null) {
            System.setProperty(CHROME_DRIVER_PROPERTY, PATH_TO_CHROME_DRIVER);
        }
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