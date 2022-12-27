package io.fluentlenium.adapter.kotest.describespec

import io.kotest.matchers.string.shouldContain
import io.fluentlenium.adapter.kotest.FluentDescribeSpec
import io.fluentlenium.adapter.kotest.TestConstants

class AwaitUntilAssertedSpec : FluentDescribeSpec({

    it("can await until asserted") {
        goTo(TestConstants.DEFAULT_URL)

        await().untilAsserted {
            window().title() shouldContain "Fluent"
        }
    }
})
