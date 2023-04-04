package io.fluentlenium.examples.pages.basic

import io.fluentlenium.core.FluentPage
import io.fluentlenium.core.annotation.PageUrl
import io.fluentlenium.core.domain.FluentWebElement
import java.util.concurrent.TimeUnit
import org.assertj.core.api.Assertions.assertThat
import org.openqa.selenium.support.FindBy

@PageUrl("https://duckduckgo.com")
class DuckDuckMainPage : FluentPage() {

    fun perform(fn: DuckDuckMainPage.() -> Unit) = this.apply(fn)

    @FindBy(css = "#searchbox_input")
    private lateinit var searchInput: FluentWebElement

    @FindBy(css = "button[type=submit]")
    private lateinit var searchButton: FluentWebElement

    fun typeSearchPhraseIn(searchPhrase: String) {
        searchInput.write(searchPhrase)
    }

    fun submitSearchForm() {
        searchButton.submit()
        awaitUntilSearchFormDisappear()
    }

    fun assertIsPhrasePresentInTheResults(searchPhrase: String) {
        await().atMost(5, TimeUnit.SECONDS).untilAsserted {
            assertThat(window().title()).contains(searchPhrase)
        }
    }

    private fun awaitUntilSearchFormDisappear() {
        await().atMost(5, TimeUnit.SECONDS).until(el(SEARCH_FORM_HOMEPAGE)).not().present()
    }

    companion object {
        private const val SEARCH_FORM_HOMEPAGE = "#search_form_homepage"
    }
}