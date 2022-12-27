package io.fluentlenium.kotest.matchers.config.pages

import io.kotest.matchers.should
import io.fluentlenium.core.FluentPage
import io.fluentlenium.core.domain.FluentList
import io.fluentlenium.core.domain.FluentWebElement
import org.fluentlenium.kotest.matchers.page.haveElement
import org.fluentlenium.kotest.matchers.page.haveElementDisplayed
import org.fluentlenium.kotest.matchers.page.haveElements
import org.openqa.selenium.support.FindAll
import org.openqa.selenium.support.FindBy

@FindAll(FindBy(id = "oneline"), FindBy(className = "small"))
class IndexPage : _root_ide_package_.io.fluentlenium.core.FluentPage() {
    @FindBy(id = "oneline")
    lateinit var fluentWebElement: _root_ide_package_.io.fluentlenium.core.domain.FluentWebElement

    @FindBy(className = "small")
    lateinit var fluentList: _root_ide_package_.io.fluentlenium.core.domain.FluentList<_root_ide_package_.io.fluentlenium.core.domain.FluentWebElement>

    fun verifyElement() {
        this should haveElement(fluentWebElement)
    }

    fun verifyElements() {
        this should haveElements(fluentList)
    }

    fun verifyElementDisplayed() {
        this should haveElementDisplayed(fluentWebElement)
    }
}
