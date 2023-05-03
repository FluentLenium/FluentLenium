package io.fluentlenium.adapter.kotest.wordspec

import io.fluentlenium.adapter.kotest.FluentWordSpec
import io.fluentlenium.adapter.kotest.TestConstants
import io.kotest.datatest.withData

class DataDrivenSpec : FluentWordSpec({
    "word spec" should {
        withData(listOf("A")) {
            goTo(TestConstants.DEFAULT_URL)
        }
    }

    withData(listOf("B")) {
        goTo(TestConstants.DEFAULT_URL)
    }
})
