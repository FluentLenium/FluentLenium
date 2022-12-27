package io.fluentlenium.adapter.kotest.pages

import io.fluentlenium.core.FluentPage
import io.fluentlenium.core.annotation.PageUrl
import io.fluentlenium.core.domain.FluentWebElement
import org.openqa.selenium.support.FindBy

@PageUrl("https://fake.com")
class DefaultPage : FluentPage() {
    @FindBy(id = "linkToPage2")
    private lateinit var linkToPage2: FluentWebElement

    fun clickLinkToPage2() {
        linkToPage2.click()
    }
}
