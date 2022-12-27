package io.fluentlenium.adapter.kotest.funspec

import io.kotest.datatest.withData
import io.fluentlenium.adapter.kotest.FluentFunSpec
import io.fluentlenium.adapter.kotest.TestConstants

class DataDrivenSpec : FluentFunSpec({
    withData(listOf("A")) {
        goTo(TestConstants.DEFAULT_URL)
    }
})
