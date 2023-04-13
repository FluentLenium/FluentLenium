package io.fluentlenium.examples.test;

import io.fluentlenium.adapter.junit.FluentTest;
import io.fluentlenium.core.annotation.Page;
import io.fluentlenium.examples.pages.DuckDuckMainPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

public class DuckDuckGoSafariTest extends FluentTest {

    @Page
    private DuckDuckMainPage duckDuckMainPage;

    @Override
    public WebDriver newWebDriver() {

        WebDriverManager.safaridriver().setup();

        SafariOptions options = new SafariOptions();

        return new SafariDriver(options);
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