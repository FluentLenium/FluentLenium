package org.fluentlenium.adapter.kotest.describespec

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldContain
import org.fluentlenium.adapter.kotest.FluentDescribeSpec
import org.fluentlenium.adapter.kotest.pages.DuckDuckGoPage
import org.fluentlenium.core.annotation.Page

class PageSpec : FluentDescribeSpec() {

    @Page
    lateinit var duckDuckGoPage: DuckDuckGoPage

    init {
        it("Page is injected") {
            duckDuckGoPage shouldNotBe null
        }

        it("is not at page") {
            shouldThrow<AssertionError> {
                duckDuckGoPage.isAt()
            }
        }

        it("Title of duck duck go") {
            duckDuckGoPage.go<DuckDuckGoPage>()
            duckDuckGoPage.isAt()

            duckDuckGoPage.fillAndSubmitForm("FluentLenium")

            window().title() shouldContain "FluentLenium"
        }
    }
}
