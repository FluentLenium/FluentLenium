package io.fluentlenium.examples.test;

import io.fluentlenium.adapter.testng.FluentTestNg;
import io.fluentlenium.core.annotation.Page;
import io.fluentlenium.examples.pages.DuckDuckMainPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class DuckDuckGoIETest extends FluentTestNg {

    private static final String PATH_TO_IE_DRIVER = "C:\\drivers\\IEDriverServer.exe";
    private static final String IE_DRIVER_PROPERTY = "webdriver.ie.driver";

    @Page
    private DuckDuckMainPage duckDuckMainPage;

    @BeforeClass
    public static void setup() {
        if (System.getProperty(IE_DRIVER_PROPERTY) == null) {
            System.setProperty(IE_DRIVER_PROPERTY, PATH_TO_IE_DRIVER);
        }
    }

    @Override
    public WebDriver newWebDriver() {
        return new InternetExplorerDriver();
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