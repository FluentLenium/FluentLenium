package io.fluentlenium.adapter.kotest.pages

import io.kotest.matchers.shouldBe
import io.fluentlenium.core.FluentPage
import io.fluentlenium.core.domain.FluentWebElement
import org.openqa.selenium.support.FindBy

class Page2 : _root_ide_package_.io.fluentlenium.core.FluentPage() {

    @FindBy(id = "link")
    private lateinit var link: _root_ide_package_.io.fluentlenium.core.domain.FluentWebElement

    fun verifyLinkPresent() {
        link.present() shouldBe true
    }
}
