package org.fluentlenium.adapter.kotest.describespec

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.fluentlenium.adapter.kotest.FluentDescribeSpec
import org.fluentlenium.adapter.kotest.TestConstants.DEFAULT_URL
import org.fluentlenium.adapter.kotest.pages.DefaultPage
import org.fluentlenium.adapter.kotest.pages.Page2
import org.fluentlenium.core.annotation.Page

class PageSpec : FluentDescribeSpec() {

    @Page
    lateinit var defaultPage: DefaultPage

    @Page
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
