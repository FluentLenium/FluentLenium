package io.fluentlenium.adapter.kotest.describespec

import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.fluentlenium.adapter.kotest.FluentDescribeSpec
import org.fluentlenium.adapter.kotest.TestConstants.DEFAULT_URL
import io.fluentlenium.core.domain.FluentList
import io.fluentlenium.core.domain.FluentWebElement
import org.openqa.selenium.support.FindBy

class CanInjectElementsSpec : FluentDescribeSpec() {

    @FindBy(id = "name")
    lateinit var names: _root_ide_package_.io.fluentlenium.core.domain.FluentList<_root_ide_package_.io.fluentlenium.core.domain.FluentWebElement>

    @FindBy(id = "name")
    lateinit var name: _root_ide_package_.io.fluentlenium.core.domain.FluentWebElement

    init {
        it("Title should be correct") {
            goTo(DEFAULT_URL)
            names.fill().with("FluentLenium")
            name.value() shouldBe "FluentLenium"
            window().title() shouldContain "Fluent"
        }
    }
}
