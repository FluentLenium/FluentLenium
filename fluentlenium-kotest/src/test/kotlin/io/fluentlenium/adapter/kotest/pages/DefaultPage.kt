package io.fluentlenium.adapter.kotest.pages

import io.fluentlenium.core.FluentPage
import io.fluentlenium.core.annotation.PageUrl
import io.fluentlenium.core.domain.FluentWebElement
import org.openqa.selenium.support.FindBy

@_root_ide_package_.io.fluentlenium.core.annotation.PageUrl("https://fake.com")
class DefaultPage : _root_ide_package_.io.fluentlenium.core.FluentPage() {
    @FindBy(id = "linkToPage2")
    private lateinit var linkToPage2: _root_ide_package_.io.fluentlenium.core.domain.FluentWebElement

    fun clickLinkToPage2() {
        linkToPage2.click()
    }
}
