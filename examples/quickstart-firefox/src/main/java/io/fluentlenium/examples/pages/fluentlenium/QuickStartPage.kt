package io.fluentlenium.examples.pages.fluentlenium

import io.fluentlenium.assertj.FluentLeniumAssertions.assertThat
import io.fluentlenium.core.FluentPage
import io.fluentlenium.core.annotation.PageUrl
import io.fluentlenium.core.domain.FluentWebElement
import io.fluentlenium.examples.components.fluentlenium.Header
import org.openqa.selenium.support.FindBy

@PageUrl("https://fluentlenium.io/quickstart/")
class QuickStartPage : FluentPage() {

    fun perform(fn: QuickStartPage.() -> Unit) = this.apply(fn)

    @FindBy(id = "table-of-contents")
    private lateinit var tableOfContents: FluentWebElement

    @FindBy(css = "nav")
    lateinit var header: Header

    override fun isAt() {
        assertThat(tableOfContents).isDisplayed
    }

}