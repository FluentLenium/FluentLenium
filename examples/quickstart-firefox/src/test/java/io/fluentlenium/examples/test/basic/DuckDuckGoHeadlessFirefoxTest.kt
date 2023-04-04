package io.fluentlenium.examples.test.basic

import io.fluentlenium.configuration.ConfigurationProperties
import io.fluentlenium.core.annotation.Page
import io.fluentlenium.examples.pages.basic.DuckDuckMainPage
import io.fluentlenium.examples.test.AbstractFirefoxTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.Dimension
import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions

class DuckDuckGoHeadlessFirefoxTest : AbstractFirefoxTest() {

    @Page
    private lateinit var onDuckDuckGoMainPage: DuckDuckMainPage

    override fun newWebDriver(): WebDriver =
        FirefoxDriver(FirefoxOptions().apply {
            addArguments("-headless")
        }).also {
            it.manage().window().size = Dimension(1400, 1200)
        }

    @BeforeEach
    fun setUp() {
        screenshotMode = ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL
        screenshotPath = "build/screenshots"
    }

    @Test
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