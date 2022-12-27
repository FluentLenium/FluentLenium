package io.fluentlenium.adapter.kotest.describespec

import io.fluentlenium.adapter.kotest.FluentDescribeSpec
import io.fluentlenium.adapter.kotest.TestConstants
import io.kotest.datatest.withData

class DataDrivenSpec : FluentDescribeSpec({
    withData(listOf("A", "B")) {
        goTo(TestConstants.DEFAULT_URL)
    }
})
