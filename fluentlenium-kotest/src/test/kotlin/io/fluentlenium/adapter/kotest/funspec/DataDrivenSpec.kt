package io.fluentlenium.adapter.kotest.funspec

import io.kotest.datatest.withData
import org.fluentlenium.adapter.kotest.FluentFunSpec
import org.fluentlenium.adapter.kotest.TestConstants

class DataDrivenSpec : FluentFunSpec({
    withData(listOf("A")) {
        goTo(TestConstants.DEFAULT_URL)
    }
})
