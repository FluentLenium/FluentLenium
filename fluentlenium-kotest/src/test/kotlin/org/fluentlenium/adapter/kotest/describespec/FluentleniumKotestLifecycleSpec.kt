package org.fluentlenium.adapter.kotest.describespec

import io.kotest.matchers.string.shouldContain
import org.fluentlenium.adapter.kotest.FluentDescribeSpec
import org.fluentlenium.adapter.kotest.TestConstants.DEFAULT_URL

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
