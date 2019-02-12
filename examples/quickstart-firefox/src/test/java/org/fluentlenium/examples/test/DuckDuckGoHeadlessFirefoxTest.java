package org.fluentlenium.examples.test;

import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.examples.pages.DuckDuckMainPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DuckDuckGoHeadlessFirefoxTest extends AbstractFirefoxTest {

    @Page
    private DuckDuckMainPage duckDuckMainPage;

    @Override
    public WebDriver newWebDriver() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("--headless");
        return new FirefoxDriver(firefoxOptions);
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