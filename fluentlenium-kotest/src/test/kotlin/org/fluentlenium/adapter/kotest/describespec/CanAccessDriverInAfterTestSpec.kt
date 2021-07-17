package org.fluentlenium.adapter.kotest.describespec

import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.string.shouldContain
import org.fluentlenium.adapter.kotest.FluentDescribeSpec
import org.fluentlenium.adapter.kotest.TestConstants.DEFAULT_URL

class CanAccessDriverInAfterTestSpec : FluentDescribeSpec() {
    init {
        it("can user browser in afterTest") {
            goTo(DEFAULT_URL)
            window().title() shouldContain "Fluent"
        }

        afterTest {
            goTo(DEFAULT_URL)
        }
    }

    override fun doAfterTest(testCase: TestCase, result: TestResult) {
        goTo(DEFAULT_URL)
    }
}
