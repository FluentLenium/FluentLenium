package org.fluentlenium.adapter.kotest.describespec

import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.fluentlenium.adapter.kotest.FluentDescribeSpec
import org.fluentlenium.adapter.kotest.TestConstants.DEFAULT_URL
import org.fluentlenium.core.domain.FluentList
import org.fluentlenium.core.domain.FluentWebElement
import org.openqa.selenium.support.FindBy

class CanInjectElementsSpec : FluentDescribeSpec() {

    @FindBy(id = "name")
    lateinit var names: FluentList<FluentWebElement>

    @FindBy(id = "name")
    lateinit var name: FluentWebElement

    init {
        it("Title should be correct") {
            goTo(DEFAULT_URL)
            names.fill().with("FluentLenium")
            name.value() shouldBe "FluentLenium"
            window().title() shouldContain "Fluent"
        }
    }
}
