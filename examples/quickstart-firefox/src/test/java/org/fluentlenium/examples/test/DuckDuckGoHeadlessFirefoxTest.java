package org.fluentlenium.examples.test;


import org.fluentlenium.adapter.junit.jupiter.FluentTest;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.examples.pages.DuckDuckMainPage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DuckDuckGoHeadlessFirefoxTest extends FluentTest {

    private static final String PATH_TO_GECKO_DRIVER = "C:\\drivers\\geckodriver.exe";
    private static final String GECKO_DRIVER_PROPERTY = "webdriver.gecko.driver";

    @Page
    private DuckDuckMainPage duckDuckMainPage;

    @BeforeAll
    static void setup() {
        if (System.getProperty(GECKO_DRIVER_PROPERTY) == null) {
            System.setProperty(GECKO_DRIVER_PROPERTY, PATH_TO_GECKO_DRIVER);
        }
    }

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