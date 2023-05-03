package io.fluentlenium.adapter.kotest.describespec

import io.fluentlenium.adapter.kotest.FluentDescribeSpec
import io.fluentlenium.adapter.kotest.TestConstants.DEFAULT_URL
import io.kotest.matchers.shouldBe
import org.openqa.selenium.WebDriver

class CanProvideCustomWebDriverSpec : FluentDescribeSpec() {

    var createCount = 0

    override fun newWebDriver(): WebDriver {
        createCount++
        return super.newWebDriver()
    }

    init {
        it("we can provide a custom WebDriver by overriding a method") {
            goTo(DEFAULT_URL)

            createCount shouldBe 1
        }
    }
}
