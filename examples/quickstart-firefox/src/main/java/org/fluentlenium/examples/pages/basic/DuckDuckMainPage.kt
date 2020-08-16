package org.fluentlenium.examples.pages.basic

import org.assertj.core.api.Assertions.assertThat
import org.fluentlenium.core.FluentPage
import org.fluentlenium.core.annotation.PageUrl
import org.fluentlenium.core.domain.FluentWebElement
import org.openqa.selenium.support.FindBy
import java.util.concurrent.TimeUnit

@PageUrl("https://duckduckgo.com")
class DuckDuckMainPage : FluentPage() {

    @FindBy(css = "#search_form_input_homepage")
    private lateinit var searchInput: FluentWebElement

    @FindBy(css = "#search_button_homepage")
    private lateinit var searchButton: FluentWebElement

    fun typeSearchPhraseIn(searchPhrase: String?): DuckDuckMainPage {
        searchInput.write(searchPhrase)
        return this
    }

    fun submitSearchForm(): DuckDuckMainPage {
        searchButton.submit()
        awaitUntilSearchFormDisappear()
        return this
    }

    fun assertIsPhrasePresentInTheResults(searchPhrase: String?) {
        assertThat(window().title()).contains(searchPhrase)
    }

    private fun awaitUntilSearchFormDisappear(): DuckDuckMainPage {
        await().atMost(5, TimeUnit.SECONDS).until(el(SEARCH_FORM_HOMEPAGE)).not().present()
        return this
    }

    companion object {
        private const val SEARCH_FORM_HOMEPAGE = "#search_form_homepage"
    }
}