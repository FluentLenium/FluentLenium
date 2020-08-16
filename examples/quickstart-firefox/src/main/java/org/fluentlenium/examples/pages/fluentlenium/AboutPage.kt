package org.fluentlenium.examples.pages.fluentlenium

import org.assertj.core.api.Assertions
import org.fluentlenium.assertj.FluentLeniumAssertions
import org.fluentlenium.core.FluentPage
import org.fluentlenium.core.domain.FluentList
import org.fluentlenium.core.domain.FluentWebElement
import org.openqa.selenium.support.FindBy

class AboutPage : FluentPage() {

    @FindBy(className = "username")
    private lateinit var contributors: FluentList<FluentWebElement>

    override fun isAt() {
        FluentLeniumAssertions.assertThat(contributors).hasSize().greaterThan(0)
    }

    fun verifySlawomirPresence() {
        Assertions.assertThat(contributors.texts()).contains("Sławomir Radzymiński")
    }
}