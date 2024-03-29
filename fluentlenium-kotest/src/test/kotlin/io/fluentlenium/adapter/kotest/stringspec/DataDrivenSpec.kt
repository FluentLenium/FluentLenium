package io.fluentlenium.adapter.kotest.stringspec

import io.fluentlenium.adapter.kotest.FluentStringSpec
import io.fluentlenium.adapter.kotest.TestConstants
import io.kotest.datatest.withData

class DataDrivenSpec : FluentStringSpec({
    withData(listOf("A", "B")) {
        goTo(TestConstants.DEFAULT_URL)
    }
})
