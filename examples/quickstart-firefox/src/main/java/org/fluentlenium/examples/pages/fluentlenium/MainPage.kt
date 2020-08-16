package org.fluentlenium.examples.pages.fluentlenium

import org.fluentlenium.assertj.FluentLeniumAssertions
import org.fluentlenium.core.FluentPage
import org.fluentlenium.core.annotation.PageUrl
import org.fluentlenium.core.domain.FluentWebElement
import org.fluentlenium.examples.components.fluentlenium.Header
import org.openqa.selenium.support.FindBy

@PageUrl("https://fluentlenium.com")
class MainPage : FluentPage() {

    @FindBy(className = "whats-fluentlenium")
    lateinit var mainContent: FluentWebElement

    @FindBy(css = "nav")
    lateinit var header: Header

    override fun isAt() {
        FluentLeniumAssertions.assertThat(mainContent).isDisplayed
    }

}