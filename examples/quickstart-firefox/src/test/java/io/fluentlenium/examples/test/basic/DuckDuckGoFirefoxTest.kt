package io.fluentlenium.examples.test.basic

import io.fluentlenium.core.annotation.Page
import io.fluentlenium.examples.pages.basic.DuckDuckMainPage
import io.fluentlenium.examples.test.AbstractFirefoxTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable
import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver

class DuckDuckGoFirefoxTest : AbstractFirefoxTest() {

    @Page
    private lateinit var onDuckDuckGoMainPage: DuckDuckMainPage

    override fun newWebDriver(): WebDriver {
        return FirefoxDriver()
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "CI", matches = "true")
    fun titleOfDuckDuckGoShouldContainSearchQueryName() {
        val searchPhrase = "searchPhrase"
        goTo(onDuckDuckGoMainPage)

        onDuckDuckGoMainPage.perform {
            typeSearchPhraseIn(searchPhrase)
            submitSearchForm()
            assertIsPhrasePresentInTheResults(searchPhrase)
        }
    }
}