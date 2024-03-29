package io.fluentlenium.adapter.kotest.behaviorspec

import io.fluentlenium.adapter.kotest.FluentBehaviorSpec
import io.fluentlenium.adapter.kotest.TestConstants
import io.kotest.datatest.withData

class DataDrivenSpec : FluentBehaviorSpec({
    given("given") {
        `when`("when") {
            withData(listOf("A", "B")) {
                goTo(TestConstants.DEFAULT_URL)
            }
        }

        withData(listOf("C")) {
            goTo(TestConstants.DEFAULT_URL)
        }
    }

    withData(listOf("D")) {
        goTo(TestConstants.DEFAULT_URL)
    }
})
