package org.fluentlenium.examples.test.basic

import org.fluentlenium.core.annotation.Page
import org.fluentlenium.examples.pages.basic.DuckDuckMainPage
import org.fluentlenium.examples.test.AbstractFirefoxTest
import org.junit.jupiter.api.Test
import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions

class DuckDuckGoHeadlessFirefoxTest : AbstractFirefoxTest() {

    @Page
    private lateinit var duckDuckMainPage: DuckDuckMainPage

    override fun newWebDriver(): WebDriver {
        val firefoxOptions = FirefoxOptions()
        firefoxOptions.addArguments("--headless")
        return FirefoxDriver(firefoxOptions)
    }

    @Test
    fun titleOfDuckDuckGoShouldContainSearchQueryName() {
        val searchPhrase = "searchPhrase"
        goTo<DuckDuckMainPage>(duckDuckMainPage)
                .typeSearchPhraseIn(searchPhrase)
                .submitSearchForm()
                .assertIsPhrasePresentInTheResults(searchPhrase)
    }
}