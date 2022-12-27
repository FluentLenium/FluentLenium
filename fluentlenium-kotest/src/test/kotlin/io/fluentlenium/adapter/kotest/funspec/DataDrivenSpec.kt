package io.fluentlenium.adapter.kotest.funspec

import io.fluentlenium.adapter.kotest.FluentFunSpec
import io.fluentlenium.adapter.kotest.TestConstants
import io.kotest.datatest.withData

class DataDrivenSpec : FluentFunSpec({
    withData(listOf("A")) {
        goTo(TestConstants.DEFAULT_URL)
    }
})
