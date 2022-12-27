package io.fluentlenium.adapter.kotest.describespec

import io.kotest.datatest.withData
import io.fluentlenium.adapter.kotest.FluentDescribeSpec
import io.fluentlenium.adapter.kotest.TestConstants

class DataDrivenSpec : FluentDescribeSpec({
    withData(listOf("A", "B")) {
        goTo(TestConstants.DEFAULT_URL)
    }
})
