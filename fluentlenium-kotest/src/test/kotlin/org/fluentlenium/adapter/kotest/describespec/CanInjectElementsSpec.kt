package org.fluentlenium.adapter.kotest.describespec

import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.fluentlenium.adapter.kotest.FluentDescribeSpec
import org.fluentlenium.adapter.kotest.TestConstants.DEFAULT_URL
import org.fluentlenium.core.domain.FluentList
import org.fluentlenium.core.domain.FluentWebElement
import org.fluentlenium.core.script.JavascriptControl
import org.openqa.selenium.support.FindBy

class CanInjectElementsSpec : FluentDescribeSpec() {

    @FindBy(id = "name")
    lateinit var names: FluentList<FluentWebElement>

    @FindBy(id = "name")
    lateinit var name: FluentWebElement

    init {
        it("Title should be correct") {
            goTo(DEFAULT_URL)
            highlightElement(names.first())
            names.fill().with("FluentLenium")
            name.value() shouldBe "FluentLenium"
            window().title() shouldContain "Fluent"
        }
    }

    fun JavascriptControl.highlightElement(element: FluentWebElement) {
        executeScript("arguments[0].style.border = '3px solid red';", element.element)
    }
}
