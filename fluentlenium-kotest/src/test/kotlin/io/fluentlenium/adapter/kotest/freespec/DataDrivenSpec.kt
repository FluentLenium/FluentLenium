package io.fluentlenium.adapter.kotest.freespec

import io.fluentlenium.adapter.kotest.FluentFreeSpec
import io.fluentlenium.adapter.kotest.TestConstants
import io.kotest.datatest.withData

class DataDrivenSpec : FluentFreeSpec({
    "free" - {
        withData(listOf("A", "B")) {
            goTo(TestConstants.DEFAULT_URL)
        }
    }

    "nested" - {
        "inner" - {
            withData(listOf("C", "D")) {
                goTo(TestConstants.DEFAULT_URL)
            }
        }
    }

    withData(listOf("E")) {
        goTo(TestConstants.DEFAULT_URL)
    }
})
