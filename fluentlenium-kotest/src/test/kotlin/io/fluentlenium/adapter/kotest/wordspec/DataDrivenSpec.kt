package io.fluentlenium.adapter.kotest.wordspec

import io.kotest.datatest.withData
import org.fluentlenium.adapter.kotest.FluentWordSpec
import org.fluentlenium.adapter.kotest.TestConstants

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
