package org.fluentlenium.adapter.kotest.pages

import org.fluentlenium.core.FluentPage
import org.fluentlenium.core.annotation.PageUrl
import org.fluentlenium.core.domain.FluentWebElement
import org.openqa.selenium.support.FindBy

@PageUrl("https://duckduckgo.com")
class DuckDuckGoPage : FluentPage() {
    @FindBy(css = "#search_form_input_homepage")
    private lateinit var searchForm: FluentWebElement

     @FindBy(css = "#search_button_homepage")
    private lateinit var searchButton: FluentWebElement

    fun fillAndSubmitForm(text: String) {
        searchForm.fill().with("FluentLenium")
        searchButton.submit()
    }
}