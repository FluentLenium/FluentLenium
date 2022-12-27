package io.fluentlenium.adapter.kotest.stringspec

import io.kotest.datatest.withData
import io.fluentlenium.adapter.kotest.FluentStringSpec
import io.fluentlenium.adapter.kotest.TestConstants

class DataDrivenSpec : FluentStringSpec({
    withData(listOf("A", "B")) {
        goTo(TestConstants.DEFAULT_URL)
    }
})
