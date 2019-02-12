package org.fluentlenium.examples.test;

import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.examples.pages.DuckDuckMainPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DuckDuckGoFirefoxTest extends AbstractFirefoxTest {

    @Page
    private DuckDuckMainPage duckDuckMainPage;

    @Override
    public WebDriver newWebDriver() {
        return new FirefoxDriver();
    }

    @Test
    void titleOfDuckDuckGoShouldContainSearchQueryName() {
        String searchPhrase = "searchPhrase";

        goTo(duckDuckMainPage)
                .typeSearchPhraseIn(searchPhrase)
                .submitSearchForm()
                .assertIsPhrasePresentInTheResults(searchPhrase);
    }

}