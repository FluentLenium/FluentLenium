package io.fluentlenium.kotest.matchers.config.pages

import io.fluentlenium.core.FluentPage
import io.fluentlenium.core.domain.FluentList
import io.fluentlenium.core.domain.FluentWebElement
import io.fluentlenium.kotest.matchers.page.haveElement
import io.fluentlenium.kotest.matchers.page.haveElementDisplayed
import io.fluentlenium.kotest.matchers.page.haveElements
import io.kotest.matchers.should
import org.openqa.selenium.support.FindAll
import org.openqa.selenium.support.FindBy

@FindAll(FindBy(id = "oneline"), FindBy(className = "small"))
class IndexPage : FluentPage() {
    @FindBy(id = "oneline")
    lateinit var fluentWebElement: FluentWebElement

    @FindBy(className = "small")
    lateinit var fluentList: FluentList<FluentWebElement>

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
