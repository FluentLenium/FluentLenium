package io.fluentlenium.examples.pages.fluentlenium

import io.fluentlenium.assertj.FluentLeniumAssertions.assertThat
import io.fluentlenium.core.FluentPage
import io.fluentlenium.core.annotation.PageUrl
import io.fluentlenium.core.domain.FluentWebElement
import io.fluentlenium.examples.components.fluentlenium.Header
import org.openqa.selenium.support.FindBy

@PageUrl("https://fluentlenium.io")
class MainPage : FluentPage() {

    fun perform(fn: MainPage.() -> Unit) = this.apply(fn)

    @FindBy(className = "whats-fluentlenium")
    lateinit var mainContent: FluentWebElement

    @FindBy(css = "nav")
    lateinit var header: Header

    override fun isAt() {
        assertThat(mainContent).isDisplayed
    }

}