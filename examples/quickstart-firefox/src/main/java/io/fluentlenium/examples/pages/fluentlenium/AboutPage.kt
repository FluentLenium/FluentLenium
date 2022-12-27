package org.fluentlenium.examples.pages.fluentlenium

import org.fluentlenium.assertj.FluentLeniumAssertions.assertThat
import org.fluentlenium.core.FluentPage
import org.fluentlenium.core.domain.FluentList
import org.fluentlenium.core.domain.FluentWebElement
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