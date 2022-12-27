package io.fluentlenium.adapter.kotest.stringspec

import io.kotest.datatest.withData
import org.fluentlenium.adapter.kotest.FluentStringSpec
import org.fluentlenium.adapter.kotest.TestConstants

class DataDrivenSpec : FluentStringSpec({
    withData(listOf("A", "B")) {
        goTo(TestConstants.DEFAULT_URL)
    }
})
