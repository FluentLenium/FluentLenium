package org.fluentlenium.adapter.kotest.shouldspec

import io.kotest.datatest.withData
import org.fluentlenium.adapter.kotest.FluentShouldSpec
import org.fluentlenium.adapter.kotest.TestConstants

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
