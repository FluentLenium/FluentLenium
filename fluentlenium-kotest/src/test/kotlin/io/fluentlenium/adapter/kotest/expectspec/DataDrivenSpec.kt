package io.fluentlenium.adapter.kotest.expectspec

import io.kotest.datatest.withData
import org.fluentlenium.adapter.kotest.FluentExpectSpec
import org.fluentlenium.adapter.kotest.TestConstants

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
