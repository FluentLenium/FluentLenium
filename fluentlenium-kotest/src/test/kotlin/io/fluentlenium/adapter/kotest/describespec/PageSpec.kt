package io.fluentlenium.adapter.kotest.describespec

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.fluentlenium.adapter.kotest.FluentDescribeSpec
import org.fluentlenium.adapter.kotest.TestConstants.DEFAULT_URL
import org.fluentlenium.adapter.kotest.pages.DefaultPage
import org.fluentlenium.adapter.kotest.pages.Page2
import io.fluentlenium.core.annotation.Page

class PageSpec : FluentDescribeSpec() {

    @_root_ide_package_.io.fluentlenium.core.annotation.Page
    lateinit var defaultPage: DefaultPage

    @_root_ide_package_.io.fluentlenium.core.annotation.Page
    lateinit var page2: Page2

    init {
        it("Page is injected") {
            defaultPage shouldNotBe null
        }

        it("is not at page") {
            shouldThrow<AssertionError> {
                defaultPage.isAt()
            }
        }

        it("should handle multiple pages") {
            goTo(DEFAULT_URL)
            defaultPage.clickLinkToPage2()
            page2.verifyLinkPresent()
            window().title() shouldBe "Page 2"
        }
    }
}
