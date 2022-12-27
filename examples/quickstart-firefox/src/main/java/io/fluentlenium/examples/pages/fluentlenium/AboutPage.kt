package io.fluentlenium.examples.pages.fluentlenium

import io.fluentlenium.assertj.FluentLeniumAssertions.assertThat
import io.fluentlenium.core.FluentPage
import io.fluentlenium.core.domain.FluentList
import io.fluentlenium.core.domain.FluentWebElement
import org.openqa.selenium.support.FindBy

class AboutPage : FluentPage() {

    fun perform(fn: AboutPage.() -> Unit) = this.apply(fn)

    @FindBy(className = "username")
    private lateinit var contributors: FluentList<FluentWebElement>

    override fun isAt() {
        assertThat(contributors).hasSize().greaterThan(0)
    }

    fun verifySlawomirPresence() {
        assertThat(contributors.texts()).contains("Sławomir Radzymiński")
    }
}