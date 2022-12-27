package org.fluentlenium.examples.pages.fluentlenium

import org.fluentlenium.assertj.FluentLeniumAssertions.assertThat
import org.fluentlenium.core.FluentPage
import org.fluentlenium.core.annotation.PageUrl
import org.fluentlenium.core.domain.FluentWebElement
import org.fluentlenium.examples.components.fluentlenium.Header
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