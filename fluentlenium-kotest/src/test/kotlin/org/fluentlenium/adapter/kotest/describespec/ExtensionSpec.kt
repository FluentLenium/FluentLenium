package org.fluentlenium.adapter.kotest.describespec

import io.kotest.core.extensions.Extension
import io.kotest.core.extensions.TestCaseExtension
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.shouldBe
import org.fluentlenium.adapter.kotest.FluentDescribeSpec

class ExtensionSpec : FluentDescribeSpec() {

    var extensionWasUsed = 0

    override fun extensions(): List<Extension> =
        listOf(MyExtension)

    object MyExtension : TestCaseExtension {
        override suspend fun intercept(testCase: TestCase, execute: suspend (TestCase) -> TestResult): TestResult {
            (testCase.spec as ExtensionSpec).extensionWasUsed++

            return execute(testCase)
        }
    }

    init {
        it("can use custom extensions") {
            extensionWasUsed shouldBe 1
        }
    }
}