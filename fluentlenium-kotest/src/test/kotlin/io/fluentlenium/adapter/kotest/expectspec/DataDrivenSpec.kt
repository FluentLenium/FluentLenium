package io.fluentlenium.adapter.kotest.expectspec

import io.kotest.datatest.withData
import io.fluentlenium.adapter.kotest.FluentExpectSpec
import io.fluentlenium.adapter.kotest.TestConstants

class DataDrivenSpec : FluentExpectSpec({
    context("context") {
        withData(listOf("A", "B")) {
            goTo(TestConstants.DEFAULT_URL)
        }
    }

    withData(listOf("C")) {
        goTo(TestConstants.DEFAULT_URL)
    }
})
