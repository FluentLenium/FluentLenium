package org.fluentlenium.examples.test;

import org.fluentlenium.adapter.junit.FluentTest;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.examples.pages.DuckDuckMainPage;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class DuckDuckGoEdgeTest extends FluentTest {

    private static final String PATH_TO_EDGE_DRIVER = "C:\\drivers\\MicrosoftWebDriver.exe";
    private static final String EDGE_DRIVER_PROPERTY = "webdriver.edge.driver";

    @Page
    private DuckDuckMainPage duckDuckMainPage;

    @BeforeClass
    public static void setup() {
        if (System.getProperty(EDGE_DRIVER_PROPERTY) == null) {
            System.setProperty(EDGE_DRIVER_PROPERTY, PATH_TO_EDGE_DRIVER);
        }
    }

    @Override
    public WebDriver newWebDriver() {
        return new EdgeDriver();
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