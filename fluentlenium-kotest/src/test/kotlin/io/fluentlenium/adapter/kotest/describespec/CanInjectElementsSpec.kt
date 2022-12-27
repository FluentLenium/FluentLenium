package io.fluentlenium.adapter.kotest.describespec

import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.fluentlenium.adapter.kotest.FluentDescribeSpec
import io.fluentlenium.adapter.kotest.TestConstants.DEFAULT_URL
import io.fluentlenium.core.domain.FluentList
import io.fluentlenium.core.domain.FluentWebElement
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
