package io.fluentlenium.examples.pages.basic

import org.assertj.core.api.Assertions.assertThat
import io.fluentlenium.core.FluentPage
import io.fluentlenium.core.annotation.PageUrl
import io.fluentlenium.core.domain.FluentWebElement
import org.openqa.selenium.support.FindBy
import java.util.concurrent.TimeUnit

@PageUrl("https://duckduckgo.com")
class DuckDuckMainPage : FluentPage() {

    fun perform(fn: DuckDuckMainPage.() -> Unit) = this.apply(fn)

    @FindBy(css = "#search_form_input_homepage")
    private lateinit var searchInput: FluentWebElement

    @FindBy(css = "#search_button_homepage")
    private lateinit var searchButton: FluentWebElement

    fun typeSearchPhraseIn(searchPhrase: String) {
        searchInput.write(searchPhrase)
    }

    fun submitSearchForm() {
        searchButton.submit()
        awaitUntilSearchFormDisappear()
    }

    fun assertIsPhrasePresentInTheResults(searchPhrase: String) {
        assertThat(window().title()).contains(searchPhrase)
    }

    private fun awaitUntilSearchFormDisappear() {
        await().atMost(5, TimeUnit.SECONDS).until(el(SEARCH_FORM_HOMEPAGE)).not().present()
    }

    companion object {
        private const val SEARCH_FORM_HOMEPAGE = "#search_form_homepage"
    }
}