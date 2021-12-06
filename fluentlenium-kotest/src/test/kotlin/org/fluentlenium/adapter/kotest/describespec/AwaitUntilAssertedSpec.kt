package org.fluentlenium.adapter.kotest.describespec

import io.kotest.matchers.string.shouldContain
import org.fluentlenium.adapter.kotest.FluentDescribeSpec
import org.fluentlenium.adapter.kotest.TestConstants

class AwaitUntilAssertedSpec : FluentDescribeSpec({

    it("can await until asserted") {
        goTo(TestConstants.DEFAULT_URL)

        await().untilAsserted {
            window().title() shouldContain "Fluent"
        }
    }
})
