package io.fluentlenium.adapter.kotest.describespec

import io.kotest.matchers.string.shouldContain
import io.fluentlenium.adapter.kotest.FluentDescribeSpec
import io.fluentlenium.adapter.kotest.TestConstants.DEFAULT_URL

class FluentleniumKotestLifecycleSpec : FluentDescribeSpec() {
    init {
        beforeTest {
            goTo(DEFAULT_URL)
        }

        it("can user browser in afterTest") {
            goTo(DEFAULT_URL)
            window().title() shouldContain "Fluent"
        }

        afterTest {
            goTo(DEFAULT_URL)
        }
    }
}
