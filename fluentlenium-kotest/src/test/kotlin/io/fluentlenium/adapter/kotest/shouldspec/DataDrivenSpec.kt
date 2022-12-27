package io.fluentlenium.adapter.kotest.shouldspec

import io.fluentlenium.adapter.kotest.FluentShouldSpec
import io.fluentlenium.adapter.kotest.TestConstants
import io.kotest.datatest.withData

class DataDrivenSpec : FluentShouldSpec({
    context("context") {
        withData(listOf("A")) {
            goTo(TestConstants.DEFAULT_URL)
        }
    }

    withData(listOf("B")) {
        goTo(TestConstants.DEFAULT_URL)
    }
})
