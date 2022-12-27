package io.fluentlenium.adapter.kotest.describespec

import io.kotest.datatest.withData
import org.fluentlenium.adapter.kotest.FluentDescribeSpec
import org.fluentlenium.adapter.kotest.TestConstants

class DataDrivenSpec : FluentDescribeSpec({
    withData(listOf("A", "B")) {
        goTo(TestConstants.DEFAULT_URL)
    }
})
