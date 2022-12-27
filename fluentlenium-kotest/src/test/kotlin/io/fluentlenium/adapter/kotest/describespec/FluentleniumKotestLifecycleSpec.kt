package io.fluentlenium.adapter.kotest.describespec

import io.fluentlenium.adapter.kotest.FluentDescribeSpec
import io.fluentlenium.adapter.kotest.TestConstants.DEFAULT_URL
import io.kotest.matchers.string.shouldContain

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
